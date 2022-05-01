package com.company.springsecurity_jwt_emailsending.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.UUID;

@Configuration
@EnableJpaAuditing// jpa ni auditing qilishiga ruxsat berish
public class AuditConfig {

    @Bean
    AuditorAware<UUID> auditorAware(){
        return new SpringSecurityAuditImpl();
    }
}
