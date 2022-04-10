package mjucapstone.wiseculture.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.common.ApiResponse;
import mjucapstone.wiseculture.common.dto.ErrorDto;
import mjucapstone.wiseculture.member.SessionManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LoginForm;
import mjucapstone.wiseculture.member.service.LoginService;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;
	private final SessionManager sessionManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginForm loginForm,
			BindingResult bindingResult, HttpServletResponse response) {
		
		// 유효성 검사에 실패한 요청
		if(bindingResult.hasErrors()) {
			log.info("Errors = {}", bindingResult.getFieldErrors());
            return ApiResponse.badRequest(ErrorDto.convertJson(bindingResult.getFieldErrors()));
        }
		
		Member member = loginService.login(loginForm.getUserId(), loginForm.getPassword());
		log.info("login? {}", member);
		
		if(member == null) {
			return ApiResponse.forbidden(new ErrorDto("login", "잘못된 아이디 또는 비밀번호"));
		}
		
		// 로그인 성공 처리
		sessionManager.createSession(member, response);
		
		return ApiResponse.success(member);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		sessionManager.expire(request);
		return ApiResponse.success(null);
	}
	
}
