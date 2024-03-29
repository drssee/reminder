package project.reminder.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.reminder.common.interceptor.AuthenticationCheckInterceptor;
import project.reminder.common.util.SessionUtil;
import project.reminder.common.util.TransUtil;
import project.reminder.common.util.service.HttpCallService;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final HttpCallService httpCallService;
    private final SessionUtil sessionUtil;
    private final TransUtil transUtil;
    @Value("${kakao.token-check-uri}")
    private String TOKEN_CHECK_URI;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("프론트 주소")
                .allowedOrigins("*")
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new AuthenticationCheckInterceptor(
                        httpCallService,
                        sessionUtil,
                        transUtil,
                        TOKEN_CHECK_URI
                )
        );
    }
}
