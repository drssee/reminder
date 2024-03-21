package project.reminder.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthenticatedOnly {
    /**
     * 접근 리소스의 소유자를 식별하기 위한 요청 파라메터명
     **/
    String parameter() default "getUserId";
}
