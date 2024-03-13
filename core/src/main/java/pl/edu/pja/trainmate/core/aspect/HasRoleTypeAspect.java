package pl.edu.pja.trainmate.core.aspect;

import static pl.edu.pja.trainmate.core.common.error.SecurityErrorCode.NO_ACCESS_TO_THE_RESOURCE;
import static pl.edu.pja.trainmate.core.config.security.RoleType.ADMIN;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import pl.edu.pja.trainmate.core.annotation.HasRoleType;
import pl.edu.pja.trainmate.core.common.exception.SecurityException;
import pl.edu.pja.trainmate.core.config.security.KeycloakRoleConverter;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Configuration
@EnableAspectJAutoProxy
@Component
class HasRoleTypeAspect {

    private final KeycloakRoleConverter keycloakRoleConverter;

    @Around("@within(pl.edu.pja.trainmate.core.annotation.HasRoleType) && execution(* *(..))")
    public Object authorizeClass(ProceedingJoinPoint point) throws Throwable {
        return authorize(point, true);
    }

    @Around("@annotation(pl.edu.pja.trainmate.core.annotation.HasRoleType)")
    public Object authorizeMethod(ProceedingJoinPoint point) throws Throwable {
        return authorize(point, false);
    }

    private Object authorize(ProceedingJoinPoint point, boolean authorizeClass) throws Throwable {
        var principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var methodSignature = (MethodSignature) point.getSignature();
        var declaration = authorizeClass
            ? methodSignature.getMethod().getDeclaringClass()
            : methodSignature.getMethod();
        var requiredUserRoles = List.of(declaration.getAnnotation(HasRoleType.class).roleType());
        var actualUserRole = keycloakRoleConverter.convert(principal);

        if (actualUserRole == ADMIN) {
            return point.proceed();
        }

        if (!requiredUserRoles.contains(actualUserRole)) {
            throw new SecurityException(NO_ACCESS_TO_THE_RESOURCE);
        }

        return point.proceed();
    }
}
