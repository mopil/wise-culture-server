package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.config.Login;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.*;
import mjucapstone.wiseculture.member.service.MemberService;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

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
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Member member = form.toMember(EncryptManager.hash(form.getPassword()));
        return success(memberService.signUp(member));
    }

    // 회원가입 : 닉네임 중복 체크
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }

    // 회원가입 : 유저 아이디 중복 체크
    @GetMapping("/id-check/{userId}")
    public ResponseEntity<?> userIdCheck(@PathVariable String userId) {
        return success(new BoolResponse(memberService.userIdCheck(userId)));
    }

    /**
     * 회원 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return success(memberService.findById(id));
    }
    
    /**
     * 회원 수정
     */
    // 닉네임 수정
    @PutMapping("/nickname/{newNickname}")
    public ResponseEntity<?> changeNickname(@Login Member loginMember, @PathVariable String newNickname) {
        Member updatedMember = memberService.changeNickname(loginMember, newNickname);
        return success(updatedMember);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordForm form,
                                            BindingResult bindingResult,
                                            @Login Member loginMember) {
    	if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        Member updatedMember = memberService.changePassword(loginMember, form);
        return success(updatedMember);
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
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
    	memberService.delete(loginMember, form, request);
    	return success(new BoolResponse(true));
    }

    /**
     * 아이디 찾기
     */
    @GetMapping("/user-id")
    public ResponseEntity<?> findUserID(@Valid @RequestBody FindIdForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
        return success(memberService.findUserId(form.getEmail(), form.getName()));
    }

    /**
     * 비밀번호 찾기(강제 재설정)
     */
    @PostMapping("/password")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetForm form) {
        return success(memberService.passwordReset(form));
    }

}
