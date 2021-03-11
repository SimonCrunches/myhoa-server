package org.technopolis.configuration.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;
import org.technopolis.configuration.security.model.SecurityConstants;
import org.technopolis.configuration.security.model.UserDTO;

import javax.annotation.Nonnull;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.technopolis.configuration.security.model.SecurityConstants.HEADER_STRING;
import static org.technopolis.configuration.security.model.SecurityConstants.TOKEN_PREFIX;

@Component
@Slf4j
public class JwtUtils {

    public String generateJwtToken(@Nonnull final UserDTO user) {
        return Jwts.builder().setSubject((user.getName())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public String getLoginFromJwtToken(@Nonnull final String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(@Nonnull final String authToken) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public UserDTO getUserDTO() {
        UserDTO userPrincipal = null;
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Object principal = securityContext.getAuthentication().getPrincipal();
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
