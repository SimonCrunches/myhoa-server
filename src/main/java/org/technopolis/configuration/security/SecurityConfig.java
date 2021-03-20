package org.technopolis.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.technopolis.configuration.security.auth.firebase.FirebaseAuthenticationProvider;
import org.technopolis.configuration.security.auth.firebase.FirebaseFilter;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;

import javax.annotation.Nonnull;
import java.sql.Timestamp;
import java.util.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    public final static class Roles {
        public static final String USER = "USER";
        public static final String ACTIVE_USER = "ACTIVE_USER";
        public static final String EXPERT = "EXPERT";

        private static final String ROLE_ = "ROLE_";
        public static final String ROLE_USER = ROLE_ + USER;
        public static final String ROLE_ACTIVE_USER = ROLE_ + ACTIVE_USER;
        public static final String ROLE_EXPERT = ROLE_ + EXPERT;
    }

    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected static class AuthenticationSecurity extends GlobalAuthenticationConfigurerAdapter {

        private final UserService userService;
        private final FirebaseAuthenticationProvider firebaseProvider;

        @Autowired
        public AuthenticationSecurity(@Nonnull final UserService userService,
                                      @Nonnull final FirebaseAuthenticationProvider firebaseProvider) {
            this.userService = userService;
            this.firebaseProvider = firebaseProvider;
        }

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userService);
            auth.authenticationProvider(firebaseProvider);
        }
    }

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        private final FirebaseService firebaseService;
        private final ObjectMapper objectMapper;

        @Autowired
        public ApplicationSecurity(@Nonnull final FirebaseService firebaseService,
                                   @Nonnull final ObjectMapper objectMapper) {
            this.firebaseService = firebaseService;
            this.objectMapper = objectMapper;
        }

        @Override
        public void configure(@Nonnull final WebSecurity web) {
            web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                    "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v2/swagger.json");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(tokenAuthorizationFilter(), BasicAuthenticationFilter.class).authorizeRequests()//
                    .antMatchers("/api/open/**").permitAll()//
                    .antMatchers("/api/active_user/**").hasRole(Roles.ACTIVE_USER)//
                    .antMatchers("/api/expert/**").hasRole(Roles.EXPERT)//
                    /*.antMatchers("/**").permitAll()*///
                    .and().cors().and().csrf().disable()//
                    /*.anonymous().authorities(Roles.ROLE_ANONYMOUS)
                    .and()*/.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint());//
        }

        @Bean
        public AuthenticationEntryPoint restAuthenticationEntryPoint() {
            return (httpServletRequest, httpServletResponse, e) -> {
                final Map<String, Object> errorObject = new HashMap<>();
                final int errorCode = 403;
                errorObject.put("message", "Access Denied");
                errorObject.put("error", HttpStatus.FORBIDDEN);
                errorObject.put("code", errorCode);
                errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.setStatus(errorCode);
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
            };
        }

        private FirebaseFilter tokenAuthorizationFilter() {
            return new FirebaseFilter(firebaseService);
        }

    }
}
