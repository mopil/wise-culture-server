package mjucapstone.wiseculture.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.common.SessionManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LoginForm;
import mjucapstone.wiseculture.member.exception.LoginException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
	
	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;
	
	public Member login(LoginForm loginForm, HttpServletResponse httpServletResponse) throws LoginException {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(loginForm.getUserId());
		
		// ID, PW가 잘못된 경우 예외 발생
		if(member == null) throw new LoginException("올바르지 않은 사용자 ID");
		if(!EncryptManager.check(loginForm.getPassword(), member.getPassword())) throw new LoginException("올바르지 않은 비밀번호");
		
		// 세션 생성
		sessionManager.createSession(member, httpServletResponse);
		
		return member;
	}
	
	public void logout(HttpServletRequest httpServletRequest) {
		sessionManager.expire(httpServletRequest);
	}
	
}
