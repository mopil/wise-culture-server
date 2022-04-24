package mjucapstone.wiseculture.util.config;

import mjucapstone.wiseculture.member.config.LoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    // 커스텀 로그인 어노테이션을 처리할 로그인 아규먼트 리졸버를 스프링에 등록
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver());
    }
}
