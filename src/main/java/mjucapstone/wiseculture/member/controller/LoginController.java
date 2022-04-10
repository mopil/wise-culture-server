package mjucapstone.wiseculture.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import mjucapstone.wiseculture.common.ApiResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.common.error.ErrorCode;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LoginForm;
import mjucapstone.wiseculture.member.exception.LoginException;
import mjucapstone.wiseculture.member.service.LoginService;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
			BindingResult bindingResult, HttpServletResponse response) {
		
		// 유효성 검사에 실패한 요청
		if(bindingResult.hasErrors()) {
			log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
		
		Member member = loginService.login(loginForm, response);
		
		return ApiResponse.success(member);
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		loginService.logout(request);
		return ApiResponse.success(null);
	}
	
	
	// 올바르지 않은 ID, PW 예외 처리
	@ExceptionHandler(LoginException.class)
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public ResponseEntity<?> loginExceptionHandle(LoginException exception) {
		log.error("[exceptionHandle] loginEx", exception);
		return ApiResponse.forbidden(new ErrorDto(ErrorCode.LOGIN_FAILED, "잘못된 아이디 또는 비밀번호"));
	}
	
	// 기타 예외 처리 : 서버에서 발생하는 다른 모든 예외는 얘가 잡아줍니다.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> otherExHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return ApiResponse.badRequest(new ErrorDto(ErrorCode.COMMON_ERROR, e.getMessage()));
    }
	
}
