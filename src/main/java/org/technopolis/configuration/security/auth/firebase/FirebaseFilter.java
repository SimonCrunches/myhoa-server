package org.technopolis.configuration.security.auth.firebase;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.exception.FirebaseTokenInvalidException;
import org.technopolis.service.impl.UserServiceImpl;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FirebaseFilter extends OncePerRequestFilter {

    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
                                    @Nonnull final HttpServletResponse response,
                                    @Nonnull final FilterChain filterChain)
            throws ServletException, IOException {
        final String xAuth = request.getHeader(SecurityConstants.HEADER_FIREBASE);
        if (StringUtils.isBlank(xAuth)) {
            try {
                final String jwt = jwtUtils.parseJwt(request);
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    final String token = jwtUtils.getFirebaseTokenFromJwtToken(jwt);

                    final UserDetails userDetails = userService.loadUserByFirebaseToken(token);
                    final UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication", e);
            }
        } else {
            try {
                final FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
                final Authentication auth = new FirebaseAuthenticationToken(holder.getUid(), holder);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (FirebaseTokenInvalidException e) {
                throw new SecurityException(e);
            }
        }
        filterChain.doFilter(request, response);
    }

}
