package mjucapstone.wiseculture.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LogInForm;
import mjucapstone.wiseculture.member.service.LoginService;
import mjucapstone.wiseculture.util.dto.BoolResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static mjucapstone.wiseculture.util.dto.ErrorResponse.convertJson;
import static mjucapstone.wiseculture.util.dto.RestResponse.badRequest;
import static mjucapstone.wiseculture.util.dto.RestResponse.success;

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
	public ResponseEntity<?> login(@Valid @RequestBody LogInForm loginForm,
								   BindingResult bindingResult,
								   HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			log.info("Errors = {}", bindingResult.getFieldErrors());
            return badRequest(convertJson(bindingResult.getFieldErrors()));
        }
		Member loginMember = loginService.login(loginForm, request);
		return success(loginMember);
	}

	/**
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		loginService.logout(request);
		return success(new BoolResponse(true));
	}


	
}
