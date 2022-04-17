package mjucapstone.wiseculture.member.controller;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import mjucapstone.wiseculture.common.dto.BoolResponse;
import mjucapstone.wiseculture.member.dto.LoginForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.dto.ApiResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.domain.Member;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	/**
	 * 로그인
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
								   BindingResult bindingResult,
								   HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
		Member loginMember = loginService.login(loginForm, request);
		return ApiResponse.success(loginMember);
	}

	/**
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		loginService.logout(request);
		return ApiResponse.success(new BoolResponse(true));
	}
	
	
	// 올바르지 않은 ID, PW 예외 처리
	@ExceptionHandler(MemberException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> loginExHandle(MemberException exception) {
		log.error("[exceptionHandle] loginEx", exception);
		return ApiResponse.forbidden(new ErrorDto(ErrorCode.LOGIN_FAILED, "잘못된 아이디 또는 비밀번호"));
	}

	
}
