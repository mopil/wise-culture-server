package mjucapstone.wiseculture.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

	private static final String SESSION_COOKIE_NAME = "wisecultureSessoinId";
	private static Map<String, Object> sessionStore = new ConcurrentHashMap<>();
	
	// 세션 생성
	public void createSession(Object value, HttpServletResponse response) {
		
		// 세션 ID 생성 및 저장
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, value);
		
		// 쿠키 생성
		Cookie sessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(sessionCookie);
	}
	
	// 세션 조회
	public Object getSession(HttpServletRequest request) {
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if(sessionCookie == null) return null;
		return sessionStore.get(sessionCookie.getValue());
	}
	
	// 세션 만료
	public void expire(HttpServletRequest request) {
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if(sessionCookie != null) sessionStore.remove(sessionCookie.getValue());
	}
	
	private Cookie findCookie(HttpServletRequest request, String cookieName) {
		if(request.getCookies() == null) return null;
		return Arrays.stream(request.getCookies())
				.filter(c -> c.getName().equals(cookieName))
				.findAny()
				.orElse(null);
	}
	
}
