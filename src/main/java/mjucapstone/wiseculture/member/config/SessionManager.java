package mjucapstone.wiseculture.member.config;

import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class SessionManager {
    public static final String SESSION_ID = "cookie";
    public static final String PREFIX = "JSESSIONID=";

    // concurrentHashMap 으로 해야 동시 접속 처리 가능(멀티쓰레드)
    private final Map<String, Member> sessionStore = new ConcurrentHashMap<>();

    // 세션 값 저장
    public void save(String sessionId, Member loginMember) {
        sessionStore.put(sessionId, loginMember);
    }

    // 세션 조회
    public Member getLoginMember(String sessionId) throws LoginException {
        log.info("세션저장소 = {}", sessionStore);
        if (!sessionStore.containsKey(sessionId)) throw new LoginException("로그인 되어 있지 않음.");
        return sessionStore.get(sessionId);
    }

    // 세션 삭제
    public void expire(String sessionId) {
        sessionStore.remove(sessionId);
    }


}
