package org.technopolis.configuration.security.jwt;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.technopolis.configuration.security.model.UserDTO;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private FirebaseAuth firebaseAuth;

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
                                    @Nonnull final HttpServletResponse response,
                                    @Nonnull final FilterChain filterChain) throws ServletException, IOException {
        authorize(request);
        filterChain.doFilter(request, response);
    }

    private void authorize(@Nonnull final HttpServletRequest request) {
        final String idToken = jwtUtils.getTokenFromRequest(request);
        FirebaseToken decodedToken = null;
        try {
            decodedToken = firebaseAuth.verifyIdToken(idToken);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception {}", e.getLocalizedMessage());
        }
        if (decodedToken != null) {
            final UserDTO user = firebaseTokenToUserDto(decodedToken);
            final List<GrantedAuthority> authorities = new ArrayList<>();
            decodedToken.getClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, decodedToken, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private UserDTO firebaseTokenToUserDto(@Nonnull final FirebaseToken decodedToken) {
        return UserDTO.builder()
                .uid(decodedToken.getUid())
                .name(decodedToken.getName())
                .email(decodedToken.getEmail())
                .isEmailVerified(decodedToken.isEmailVerified())
                .build();
    }
}
