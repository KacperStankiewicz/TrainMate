package pl.edu.pja.trainmate.core.aspect;

import static pl.edu.pja.trainmate.core.common.error.SecurityErrorCode.NO_ACCESS_TO_THE_RESOURCE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.ADMIN;

import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import pl.edu.pja.trainmate.core.annotation.HasRole;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Configuration
@EnableAspectJAutoProxy
@Component
class HasWorkContextAspect {

    private final LoggedUserDataProvider userProvider;

    @Around("@within(pl.edu.pja.trainmate.core.annotation.HasRole) && execution(* *(..))")
    public Object authorizeClass(ProceedingJoinPoint point) throws Throwable {
        return authorize(point, true);
    }

    @Around("@annotation(pl.edu.pja.trainmate.core.annotation.HasRole)")
    public Object authorizeMethod(ProceedingJoinPoint point) throws Throwable {
        return authorize(point, false);
    }

    private Object authorize(ProceedingJoinPoint point, boolean authorizeClass) throws Throwable {
        var userActiveContext = Option.of(userProvider.getUserDetails())
            .map(LoggedUserDataDto::getRole)
            .getOrNull();

        var methodSignature = (MethodSignature) point.getSignature();
        var declaration = authorizeClass
            ? methodSignature.getMethod().getDeclaringClass()
            : methodSignature.getMethod();
        var annotation = declaration.getAnnotation(HasRole.class);

        if (ADMIN.equals(userActiveContext)) {
            return point.proceed();
        }

        if (annotation.roleType().length != 0 && !List.of(annotation.roleType()).contains(userActiveContext)) {
            throw new SecurityException(NO_ACCESS_TO_THE_RESOURCE);
        }

        return point.proceed();
    }
}