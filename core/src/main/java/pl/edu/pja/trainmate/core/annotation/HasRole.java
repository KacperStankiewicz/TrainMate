package pl.edu.pja.trainmate.core.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pl.edu.pja.trainmate.core.config.Profiles.DEV;
import static pl.edu.pja.trainmate.core.config.Profiles.INTEGRATION;
import static pl.edu.pja.trainmate.core.config.Profiles.PROD;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Profile;
import pl.edu.pja.trainmate.core.config.security.RoleType;

@Retention(RUNTIME)
@Target({METHOD})
@Profile({DEV, PROD, INTEGRATION})
public @interface HasRole {

    RoleType[] roleType() default {};
}