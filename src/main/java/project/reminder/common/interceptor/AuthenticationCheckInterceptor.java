package project.reminder.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.service.HttpCallService;

import java.lang.reflect.Method;

@RequiredArgsConstructor
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

    private final HttpCallService httpCallService;

    private final String TOKEN_CHECK_URI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            if (method.isAnnotationPresent(AuthenticatedOnly.class)) {
                String token = (String) request.getSession().getAttribute("token");
                httpCallService.callWithToken(HttpConst.GET, TOKEN_CHECK_URI, token);
            }
        }

        return true;
    }
}
