package mjucapstone.wiseculture.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.dto.BoolResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.SignUpForm;
import mjucapstone.wiseculture.member.exception.LoginException;
import mjucapstone.wiseculture.member.exception.SignUpException;
import mjucapstone.wiseculture.member.service.LoginService;
import mjucapstone.wiseculture.member.service.MemberService;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping("/new")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpForm form, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
        Member member = form.toMember(EncryptManager.hash(form.getPassword()));
        memberService.signUp(member);
        return ApiResponse.success(member);
    }

    @GetMapping("/nickname-check/{nickname}")
    public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
        return ApiResponse.success(new BoolResponse(memberService.nicknameCheck(nickname)));
    }
    
    
    
    // 회원 목록(직접 테스트시 확인 용)
    @RequestMapping("/users")
    public ResponseEntity<?> getAllUser() {
    	return ApiResponse.success(memberService.getAllMember());
    }
    
    // 회원 정보
    @RequestMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
    	
    	// 현재 로그인된 사용자 확인
    	Member member = loginService.checkLogin(request);
    	
    	// 현재 로그인된 사용자 정보 가져오기
    	member = memberService.findMember(member.getUserId());
    	if(member == null) return ApiResponse.forbidden(new ErrorDto(ErrorCode.LOGIN_FAILED, "존재하지 않는 계정으로 로그인 됨"));
    	
    	return ApiResponse.success(member);
    }
    
    // ==========<회원 정보 수정>==========
    // 닉네임 수정
    @PostMapping("/change/nickname")
    public ResponseEntity<?> changeNickname() {
    	return null;
    }
    // 비밀번호 수정
    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword() {
    	return null;
    }
    // =================================
    
    // 아이디 찾기
    @PostMapping("/find/userID")
    public ResponseEntity<?> findUserID() {
    	return null;
    }
    
    // 회원 탈퇴
    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser() {
    	return null;
    }

    /**
     *  예외 처리
     *  스프링에서 예외를 처리하는 방법입니다. 예외가 던져지면 WAS까지 올라가는데 컨트롤러에서 잡아줘야 합니다.
     *  자세한건 스프링 REST API 예외처리 검색하면 나옵니다.
     */
    // 중복 회원
    @ExceptionHandler(SignUpException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> signUpExHandle(SignUpException e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.SIGN_UP_ERROR, e.getMessage()));
    }
    
	// 로그인 안 된 경우 예외 처리
 	@ExceptionHandler(LoginException.class)
 	@ResponseStatus(code = HttpStatus.FORBIDDEN)
 	public ResponseEntity<?> loginExHandle(LoginException exception) {
 		log.error("[exceptionHandle] loginEx", exception);
 		return ApiResponse.forbidden(new ErrorDto(ErrorCode.LOGIN_FAILED, "잘못된 아이디 또는 비밀번호"));
 	}

    // 기타 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.COMMON_ERROR, e.getMessage()));
    }


}
