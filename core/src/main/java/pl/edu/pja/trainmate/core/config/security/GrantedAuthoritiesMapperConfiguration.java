package pl.edu.pja.trainmate.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

@Configuration
class GrantedAuthoritiesMapperConfiguration {

    @Bean
    public GrantedAuthoritiesMapper authoritiesMapper() {
        return new SimpleAuthorityMapper();
    }
}