package org.technopolis.configuration.security;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;
import org.technopolis.configuration.security.model.UserDTO;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

import static org.technopolis.configuration.security.model.SecurityConstants.HEADER_STRING;
import static org.technopolis.configuration.security.model.SecurityConstants.TOKEN_PREFIX;

@Service
public class SecurityService {

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

    public UserDTO firebaseTokenToUserDto(@Nonnull final FirebaseToken decodedToken) {
        return UserDTO.builder()
                .uid(decodedToken.getUid())
                .name(decodedToken.getName())
                .email(decodedToken.getEmail())
                .isEmailVerified(decodedToken.isEmailVerified())
                .claims(decodedToken.getClaims().keySet().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .build();
    }

}
