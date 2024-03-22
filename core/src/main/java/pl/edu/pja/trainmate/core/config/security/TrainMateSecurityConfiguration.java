package pl.edu.pja.trainmate.core.config.security;

import static pl.edu.pja.trainmate.core.config.Profiles.DEV;
import static pl.edu.pja.trainmate.core.config.Profiles.INTEGRATION;
import static pl.edu.pja.trainmate.core.config.Profiles.LOCAL;
import static pl.edu.pja.trainmate.core.config.Profiles.PROD;

import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
@Profile({DEV, INTEGRATION, LOCAL, PROD})
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@KeycloakConfiguration
@EnableWebSecurity
class TrainMateSecurityConfiguration extends WebSecurityConfigurerAdapter {

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
            .disable();
    }
}