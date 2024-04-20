package pl.edu.pja.trainmate.core.config.security;

import static pl.edu.pja.trainmate.core.config.Profiles.DEV;
import static pl.edu.pja.trainmate.core.config.Profiles.INTEGRATION;
import static pl.edu.pja.trainmate.core.config.Profiles.LOCAL;
import static pl.edu.pja.trainmate.core.config.Profiles.PROD;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@Configuration
@Profile({DEV, INTEGRATION, LOCAL, PROD})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@KeycloakConfiguration
class RpwdlSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

    private final RpwdlExceptionHandlerFilter exceptionHandlerFilter;
    private final AuthenticationEventPublisher authenticationEventPublisher;
    private final GrantedAuthoritiesMapper authoritiesMapper;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        var authenticationProvider = keycloakAuthenticationProvider();
        authenticationProvider.setGrantedAuthoritiesMapper(authoritiesMapper);

        auth
            .authenticationProvider(authenticationProvider)
            .authenticationEventPublisher(authenticationEventPublisher);
    }

    @Bean
    FilterRegistrationBean<KeycloakAuthenticationProcessingFilter> keycloakAuthenticationProcessingFilter(KeycloakAuthenticationProcessingFilter filter) {
        return new FilterRegistrationBean<>(filter);
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .logout()
            .deleteCookies("JSESSIONID")
            .and()
            .csrf()
            .disable()
            .addFilterBefore(exceptionHandlerFilter, CorsFilter.class);
    }
}