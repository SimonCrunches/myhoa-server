package org.technopolis.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.technopolis.configuration.security.jwt.AuthTokenFilter;
import org.technopolis.configuration.security.service.UserDetailsServiceImpl;

import javax.annotation.Nonnull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        //securedEnabled = true,
        //jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final AuthTokenFilter authTokenFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(@Nonnull final ObjectMapper objectMapper,
                             @Nonnull final AuthTokenFilter authTokenFilter,
                             @Nonnull final UserDetailsServiceImpl userDetailsService) {
        this.objectMapper = objectMapper;
        this.authTokenFilter = authTokenFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(@Nonnull final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
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

    @Override
    protected void configure(@Nonnull final HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().formLogin().disable().httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/api/v1.0/user/**").hasRole("ACTIVE_USER")
                    .anyRequest().permitAll();

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
