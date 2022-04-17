package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.login.EncryptManager;
import mjucapstone.wiseculture.common.dto.BoolResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.login.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.*;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import mjucapstone.wiseculture.member.service.MemberService;
import mjucapstone.wiseculture.common.error.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     * 주의! {id} = 디비 PK, {userId} = 회원가입에 사용되는 진짜 유저 ID
     */
    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm form, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        Member member = form.toMember(EncryptManager.hash(form.getPassword()));
        return ApiResponse.success(memberService.signUp(member));
    }

    // 회원가입 : 닉네임 중복 체크
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ApiResponse.success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }

    // 회원가입 : 유저 아이디 중복 체크
    @GetMapping("/id-check/{userId}")
    public ResponseEntity<?> userIdCheck(@PathVariable String userId) {
        return ApiResponse.success(new BoolResponse(memberService.userIdCheck(userId)));
    }

    /**
     * 회원 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return ApiResponse.success(memberService.findById(id));
    }
    
    /**
     * 회원 수정
     */
    // 닉네임 수정
    @PutMapping("/nickname/{newNickname}")
    public ResponseEntity<?> changeNickname(@Login Member loginMember, @PathVariable String newNickname) {
        if (loginMember == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.MEMBER_CRUD_ERROR, "로그인이 안됨"));
        Member updatedMember = memberService.changeNickname(loginMember, newNickname);
        return ApiResponse.success(updatedMember);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordForm form,
                                            BindingResult bindingResult,
                                            @Login Member loginMember) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        if (loginMember == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.MEMBER_CRUD_ERROR, "로그인이 안됨"));
        Member updatedMember = memberService.changePassword(loginMember, form);
        return ApiResponse.success(updatedMember);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("")
    public ResponseEntity<?> deleteMember(@Valid @RequestBody DeleteMemberForm form,
                                        BindingResult bindingResult,
                                        @Login Member loginMember, HttpServletRequest request) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        if (loginMember == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.MEMBER_CRUD_ERROR, "로그인이 안됨"));
    	memberService.delete(loginMember, form, request);
    	return ApiResponse.success(new BoolResponse(true));
    }

    /**
     * 아이디 찾기
     */
    @GetMapping("/user-id")
    public ResponseEntity<?> findUserID(@Valid @RequestBody FindIdForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        return ApiResponse.success(memberService.findUserId(form.getEmail(), form.getName()));
    }

    /**
     * 비밀번호 찾기(강제 재설정)
     */
    @PostMapping("/password")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetForm form) {
        return ApiResponse.success(memberService.passwordReset(form));
    }

    /**
     *  예외 처리
     */
    // 회원가입 : 중복 회원
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }

    // 기타 회원 CRUD 오류
    @ExceptionHandler(MemberException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherMemberExHandle(MemberException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.MEMBER_CRUD_ERROR, e.getMessage()));
    }



}
