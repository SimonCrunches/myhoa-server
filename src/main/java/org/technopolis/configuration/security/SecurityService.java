package org.technopolis.configuration.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;
import org.technopolis.configuration.security.model.UserDTO;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.technopolis.configuration.security.model.SecurityConstants.HEADER_STRING;
import static org.technopolis.configuration.security.model.SecurityConstants.TOKEN_PREFIX;

@Service
public class SecurityService {

    public UserDTO getUser(@Nonnull final SecurityContext securityContext) {
        UserDTO userPrincipal = null;
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof UserDTO) {
            userPrincipal = ((UserDTO) principal);
        }
        return userPrincipal;
    }

    public String getTokenFromRequest(@Nonnull final HttpServletRequest request) {
        String token = null;
        final Cookie cookieToken = WebUtils.getCookie(request, "token");
        if (cookieToken != null) {
            token = cookieToken.getValue();
        } else {
            final String bearerToken = request.getHeader(HEADER_STRING);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
                token = bearerToken.substring(7);
            }
        }
        return token;
    }

}
