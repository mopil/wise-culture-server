package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.common.dto.BoolResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.FindIDForm;
import mjucapstone.wiseculture.member.dto.ModifyMemberForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import mjucapstone.wiseculture.member.service.MemberService;
import mjucapstone.wiseculture.member.dto.SignUpForm;
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

    // 회원 정보
    @RequestMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
    	return ApiResponse.success(memberService.findMember(request));
    }

    // ==========<회원 정보 수정>==========
    // 닉네임 수정
    @PostMapping("/change/nickname")
    public ResponseEntity<?> changeNickname(@Valid @RequestBody ModifyMemberForm form, BindingResult bindingResult, HttpServletRequest request) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
    	if(form.getNickname() == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.VALIDATION_ERROR, "닉네임이 입력되지 않음"));

    	memberService.changeNickname(form, request);

        return ApiResponse.success(true);
    }

    // 비밀번호 수정
    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ModifyMemberForm form, BindingResult bindingResult, HttpServletRequest request) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
    	if(form.getNewPassword() == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.VALIDATION_ERROR, "새 비밀번호가 입력되지 않음"));
    	if(form.getCurPassword() == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.VALIDATION_ERROR, "현재 비밀번호가 입력되지 않음"));

    	memberService.changePassword(form, request);

        return ApiResponse.success(true);
    }
    // 아이디 찾기
    @PostMapping("/user-id")
    public ResponseEntity<?> findUserID(@Valid @RequestBody FindIDForm form, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }

    	return ApiResponse.success(memberService.findUserId(form.getEmail(), form.getName()));
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody ModifyMemberForm form, BindingResult bindingResult, HttpServletRequest request) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
    	if(form.getCurPassword() == null) return ApiResponse.badRequest(new ErrorDto(ErrorCode.VALIDATION_ERROR, "현재 비밀번호가 입력되지 않음"));

    	memberService.delete(form, request);

    	return ApiResponse.success(true);
    }

    /**
     * 비밀번호 재설정
     */
    @PostMapping("/{id}/password")
    public ResponseEntity<?> passwordReset(@PathVariable("id") Long memberId) {
        return ApiResponse.success(memberService.passwordReset(memberId));
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
    public ResponseEntity<?> otherMemberExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.MEMBER_CRUD_ERROR, e.getMessage()));
    }



}
