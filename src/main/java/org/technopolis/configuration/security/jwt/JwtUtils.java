package org.technopolis.configuration.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.technopolis.configuration.security.model.SecurityConstants;
import org.technopolis.configuration.security.model.UserDTO;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.technopolis.configuration.security.model.SecurityConstants.HEADER_STRING;
import static org.technopolis.configuration.security.model.SecurityConstants.TOKEN_PREFIX;

@Component
@Slf4j
public class JwtUtils {

    public String generateJwtToken(@Nonnull final Authentication authentication) {
        final UserDTO userPrincipal = (UserDTO) authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUid()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public String getTokenFromJwtToken(@Nonnull final String token) {
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

    public String parseJwt(@Nonnull final HttpServletRequest request) {
        final String headerAuth = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(TOKEN_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
