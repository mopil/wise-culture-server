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
	
	// 로그인
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
	
	// 사용자의 password 확인 
	public boolean checkPassword(String userID, String password) {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(userID);
		
		// 해당 ID에 사용자가 없으면 false
		if(member == null) return false;
		
		// 암호가 올바른지 확인
		return EncryptManager.check(password, member.getPassword());
	}
	
	// 로그인 확인
	public boolean checkLogin(HttpServletRequest httpServletRequest, String userID) {
		
		// 현재 로그인된 사용자 확인
		Member member = (Member)sessionManager.getSession(httpServletRequest);
		
		// 로그인된 사용자를 찾을 수 없는 경우
		if(member == null) throw new LoginException("로그인 되어있지 않음");
		
		return member.getUserId().equals(userID);
	}
	
	public Member checkLogin(HttpServletRequest httpServletRequest) {
		
		// 현재 로그인된 사용자 확인
		Member member = (Member)sessionManager.getSession(httpServletRequest);
		
		// 로그인된 사용자를 찾을 수 없는 경우
		if(member == null) throw new LoginException("로그인 되어있지 않음");
				
		return member;		
	}
	
	// 로그아웃
	public void logout(HttpServletRequest httpServletRequest) {
		sessionManager.expire(httpServletRequest);
	}
	
}
