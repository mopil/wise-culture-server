package mjucapstone.wiseculture.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.config.EncryptManager;
import mjucapstone.wiseculture.member.config.SessionManager;
import mjucapstone.wiseculture.member.domain.Member;
import mjucapstone.wiseculture.member.dto.LogInForm;
import mjucapstone.wiseculture.member.dto.MemberResponse;
import mjucapstone.wiseculture.member.exception.MemberException;
import mjucapstone.wiseculture.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;

	/**
	 * 로그인
	 */
	// 로그인 확인
	public Member getLoginMember(HttpServletRequest request) throws LoginException {
		String sessionId = request.getHeader(SessionManager.SESSION_ID);
		log.info("클라이언트로 부터 요청받은 쿠키(세션값) = {}", sessionId);
		Long memberId = sessionManager.getLoginMember(sessionId);
		return memberRepository.findById(memberId)
				.orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));
	}

	public MemberResponse login(LogInForm loginForm, HttpServletRequest request) throws MemberException {
		
		// 사용자 ID로 사용자 찾기
		Member member = memberRepository.findByUserId(loginForm.getUserId())
				.orElseThrow(() -> new MemberException("해당 회원을 찾을 수 없음"));

		// ID, PW가 잘못된 경우 예외 발생
		if(!EncryptManager.check(loginForm.getPassword(), member.getPassword())) throw new MemberException("올바르지 않은 비밀번호");
		
		// 세션 생성 (여기서는 그냥 랜덤값을 제작하기 위해서만 사용)
		HttpSession session = request.getSession();

		// 세션매니져에 세션 저장
		sessionManager.save(SessionManager.PREFIX + session.getId(), member);

		MemberResponse memberResponse = member.toResponse();
		memberResponse.setSessionId(SessionManager.PREFIX + session.getId());
		return memberResponse;
	}

	/**
	 * 로그아웃
	 */
	public void logout(HttpServletRequest request) {
		sessionManager.expire(request.getHeader(SessionManager.SESSION_ID));
	}
	
}
