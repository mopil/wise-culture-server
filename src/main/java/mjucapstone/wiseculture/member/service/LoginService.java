package mjucapstone.wiseculture.member.service;

import lombok.RequiredArgsConstructor;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.config.SessionConst;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LogInForm;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;

	/**
	 * 로그인
	 */
	public Member login(LogInForm loginForm, HttpServletRequest request) throws MemberException {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(loginForm.getUserId())
				.orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));

		// ID, PW가 잘못된 경우 예외 발생
		if(!EncryptManager.check(loginForm.getPassword(), member.getPassword())) throw new MemberException("올바르지 않은 비밀번호");
		
		// 세션 생성, true 로 넘기면 새로운 세션을 생성함
		HttpSession session = request.getSession(true);

		// 세션에 로그인 회원 정보 보관
		session.setAttribute(SessionConst.LOGIN_MEMBER, member);
		return member;
	}

	/**
	 * 로그아웃
	 */
	public void logout(HttpServletRequest request) {
		// false 로 넘기면 기존 세션을 가져옴
		HttpSession session = request.getSession(false);
		if (session != null) {
			// 세션 강제 만료시키기
			session.invalidate();
		}
	}
	
}
