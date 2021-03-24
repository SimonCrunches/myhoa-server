package org.technopolis.configuration.security.auth.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.technopolis.configuration.security.SecurityConstants;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    public String generateJwtToken(@Nonnull final String username) {
        return Jwts.builder().setSubject((username)).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public String getUserNameFromJwtToken(@Nonnull final String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public Integer getIdFromJwtToken(@Nonnull final String token) {
        return Integer.parseInt(Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody().getId());
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
        final String headerAuth = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
