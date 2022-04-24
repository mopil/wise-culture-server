package mjucapstone.wiseculture.member.config;

import lombok.extern.slf4j.Slf4j;
import mjucapstone.wiseculture.member.domain.Member;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 로그인 어노테이션이 붙어 있는가를 검사
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        // Member 타입의 객체가 로그인 어노테이션 뒤에 붙어 있는지 검사
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasMemberType;
    }

    // supportsParameter 가 true 면 이 메서드가 실행 됨
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 리퀘스트 뽑아 오기
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        HttpSession session = request.getSession(false);
        if (session == null) {
            // 세션이 없으면 그냥 Member 에 null 이 들어감
            throw new LoginException("인증 거부 : 로그인 안 됨");
        }
        // 세션 정보가 있으면 로그인된 회원을 날린다
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
