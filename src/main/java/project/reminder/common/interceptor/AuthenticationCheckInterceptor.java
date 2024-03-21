package project.reminder.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import project.reminder.common.annotation.AuthenticatedOnly;
import project.reminder.common.enums.HttpConst;
import project.reminder.common.util.SessionUtil;
import project.reminder.common.util.TransUtil;
import project.reminder.common.util.service.HttpCallService;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @AuthenticatedOnly 이 붙은 권한이 필요한 api 들의 검증 인터셉터
 */
@RequiredArgsConstructor
public class AuthenticationCheckInterceptor implements HandlerInterceptor {

    private final HttpCallService httpCallService;
    private final SessionUtil sessionUtil;
    private final TransUtil transUtil;
    private final String TOKEN_CHECK_URI;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AuthenticatedOnly annotation = method.getAnnotation(AuthenticatedOnly.class);

            if (annotation != null) {
                String userId = sessionUtil.getUserId();
                //1. 세션 유저가 없을 경우 false
                if (!StringUtils.hasText(userId)) return false;

                // 요청바디 복사해야함..


                //2. @RequestBody 가 있을 경우
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    for (int j = 0; j < parameterAnnotations[i].length; j++) {
                        // @RequestBody 가 존재할 경우 그 파라미터의 유저를 가져와 세션 유저와 비교
                        if (parameterAnnotations[i][j] instanceof RequestBody) {
                            Class<?> parameterType = method.getParameterTypes()[i];
                            Object o = transUtil.parseBody(request.getInputStream(), parameterType);
                            if (o != null) {
                                Method method1 = parameterType.getMethod(annotation.parameter());
                                String userId1 = (String) method1.invoke(o);
                                return userId.equals(userId1);
                            }
                        }
                    }
                }

                //3. 유효한 토큰 여부 체크
                String token = (String) request.getSession().getAttribute("token");
                httpCallService.callWithToken(HttpConst.GET, TOKEN_CHECK_URI, token);
            }
        }

        return true;
    }
}
