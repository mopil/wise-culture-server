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
        log.info("회원가입 성공 : {}", member);
        return success(memberService.signUp(member));
    }

    // 회원가입 : 닉네임 중복 체크
    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        log.info("닉네임 중복 체크 호출 됨 : {}", nickname);
        return success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }

    // 회원가입 : 유저 아이디 중복 체크
    @GetMapping("/id-check/{userId}")
    public ResponseEntity<?> userIdCheck(@PathVariable String userId) {
        log.info("아이디 중복 체크 호출 됨 : {}", userId);
        return success(new BoolResponse(memberService.userIdCheck(userId)));
    }

    /**
     * 회원 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Member findMember = memberService.findById(id);
        log.info("디비 PK로 조회된 회원 = {}", findMember);
        return success(findMember);
    }
    
    /**
     * 회원 수정
     */
    // 닉네임 수정
    @PutMapping("/nickname/{newNickname}")
    public ResponseEntity<?> changeNickname(@Login Member loginMember, @PathVariable String newNickname) {
        log.info("현재 로그인된 사용자 = {}", loginMember);
        log.info("변경하고자 하는 새로운 닉네임 = {}", newNickname);
        Member updatedMember = memberService.changeNickname(loginMember, newNickname);
        log.info("변경된 사용자 정보 = {}", updatedMember);
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
        log.info("현재 로그인된 사용자 = {}", loginMember);
        log.info("변경하고자 하는 비밀번호 = {}", form.getNewPassword());
        Member updatedMember = memberService.changePassword(loginMember, form);
        log.info("변경된 사용자 정보 = {}", updatedMember);
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
        log.info("현재 로그인된 사용자 = {}", loginMember);
    	memberService.delete(loginMember, form, request);
        log.info("회원 탈퇴 성공");
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
        Member findMember = memberService.findUserId(form.getEmail(), form.getName());
        log.info("아이디 찾기로 찾아진 멤버 = {}", findMember);
        return success(findMember);
    }

    /**
     * 비밀번호 찾기(강제 재설정)
     */
    @PostMapping("/password")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordResetForm form) {
        Member updatedMember = memberService.passwordReset(form);
        log.info("비밀번호 찾기(재설정) 완료 = {}", updatedMember);
        return success(updatedMember);
    }

}
