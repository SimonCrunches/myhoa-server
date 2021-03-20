package org.technopolis.configuration.security.auth.firebase;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.exception.FirebaseTokenInvalidException;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FirebaseFilter extends OncePerRequestFilter {

    private final FirebaseService firebaseService;

    public FirebaseFilter(@Nonnull final FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
                                    @Nonnull final HttpServletResponse response,
                                    @Nonnull final FilterChain filterChain)
            throws ServletException, IOException {
        final String xAuth = request.getHeader(SecurityConstants.HEADER_FIREBASE);
        if (StringUtils.isBlank(xAuth)) {
            filterChain.doFilter(request, response);
        } else {
            try {
                final FirebaseTokenHolder holder = firebaseService.parseToken(xAuth);
                final Authentication auth = new FirebaseAuthenticationToken(holder.getUid(), holder);
                SecurityContextHolder.getContext().setAuthentication(auth);

                filterChain.doFilter(request, response);
            } catch (FirebaseTokenInvalidException e) {
                throw new SecurityException(e);
            }
        }
    }

}
