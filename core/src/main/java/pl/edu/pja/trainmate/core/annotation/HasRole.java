package pl.edu.pja.trainmate.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import pl.edu.pja.trainmate.core.config.security.RoleType;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface HasRole {

    RoleType[] roleType() default {};
}