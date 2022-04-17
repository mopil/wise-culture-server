package mjucapstone.wiseculture.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mjucapstone.wiseculture.member.MemberRepository;
import mjucapstone.wiseculture.member.dto.LoginForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.common.EncryptManager;
import mjucapstone.wiseculture.common.SessionManager;
import mjucapstone.wiseculture.member.domain.Member;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
	
	private final SessionManager sessionManager;
	private final MemberRepository memberRepository;
	
	// 로그인
	public Member login(LoginForm loginForm, HttpServletResponse httpServletResponse) throws MemberException {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(loginForm.getUserId())
				.orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));

		// ID, PW가 잘못된 경우 예외 발생
		if(!EncryptManager.check(loginForm.getPassword(), member.getPassword())) throw new MemberException("올바르지 않은 비밀번호");
		
		// 세션 생성
		sessionManager.createSession(member, httpServletResponse);
		
		return member;
	}
	
	// 사용자의 password 확인 
	public boolean checkPassword(String userId, String password) {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(userId)
				.orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));
		
		// 암호가 올바른지 확인
		return EncryptManager.check(password, member.getPassword());
	}
	
	// 로그인 확인
	public boolean checkLogin(HttpServletRequest request, String userId) {
		return getMember(request).getUserId().equals(userId);
	}
	
	public Member checkLogin(HttpServletRequest request) {
		return getMember(request);
	}

	private Member getMember(HttpServletRequest request) {
		// 현재 로그인된 사용자 확인
		Member member = (Member)sessionManager.getSession(request);

		// 로그인된 사용자를 찾을 수 없는 경우
		if(member == null) throw new MemberException("로그인 되어있지 않음");
		return member;
	}

	// 로그아웃
	public void logout(HttpServletRequest httpServletRequest) {
		sessionManager.expire(httpServletRequest);
	}
	
}
