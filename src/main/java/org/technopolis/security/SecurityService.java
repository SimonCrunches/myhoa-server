package org.technopolis.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.technopolis.security.model.Credentials;
import org.technopolis.security.model.User;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityService {

    public User getUser() {
        User userPrincipal = null;
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof User) {
            userPrincipal = ((User) principal);
        }
        return userPrincipal;
    }

    public Credentials getCredentials() {
        return (Credentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        final String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }

}
