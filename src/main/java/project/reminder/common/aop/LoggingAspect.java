//package project.reminder.common.aop;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import project.reminder.login.dto.SessionUserDto;
//import project.reminder.login.service.LoginService;
//
//@Slf4j
//@Aspect
//@Component
//public class LoggingAspect {
//
////    @Before("execution(* project.reminder..*(..))")
////    public void loggingMethod(JoinPoint joinPoint) {
////        String methodName = joinPoint.getSignature().getName();
////        log.debug("method={}", methodName);
////    }
//
//    @Around("execution(* project.reminder..*(..))")
//    public Object loggingMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String methodName = joinPoint.getSignature().getName();
//        HttpSession session = request.getSession();
//        SessionUserDto user = (SessionUserDto) session.getAttribute("user");
//        if (user == null) {
//            log.debug("method={}", methodName);
//        } else {
//            log.debug("user={}, method={}",user.getId(), methodName);
//        }
//
//        return joinPoint.proceed();
//    }
//}
package project.reminder.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import project.reminder.login.dto.SessionUserDto;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* project.reminder..*(..))")
    public void allMethods() {}

    @Before("allMethods()")
    public void loggingMethod(JoinPoint joinPoint) {
        Map<String, String> map = getUserIdAndMethodName(joinPoint);
        String methodName = map.get("methodName");
        String userId = map.get("userId");
        log.info("methodName = {}, userId = {}", methodName, userId);
    }

    private Map<String, String> getUserIdAndMethodName(JoinPoint joinPoint) {
        Map<String, String> map = new HashMap<>();

        // 메소드 이름
        String methodName =
                ((MethodSignature) joinPoint.getSignature())
                        .getMethod()
                        .getName();
        map.put("methodName", methodName);

        // 세션 유저
        HttpSession session = getSession();
        if (session != null) {
            SessionUserDto user = (SessionUserDto) session.getAttribute("user");

            if (user != null) {
                map.put("userId", String.valueOf(user.getId()));
            }
        }

        return map;
    }

    // 현재 요청의 HttpSession 가져오기
    private HttpSession getSession() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getSession(false); // HttpSession 이 존재하지 않으면 null 리턴
        }
        return null;
    }
}
