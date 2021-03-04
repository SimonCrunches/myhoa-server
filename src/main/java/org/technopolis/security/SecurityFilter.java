package org.technopolis.security;

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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.technopolis.security.model.Credentials;
import org.technopolis.security.model.SecurityProperties;
import org.technopolis.security.model.User;
import org.technopolis.security.role.RoleConstants;
import org.technopolis.security.role.RoleService;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityService securityService;
    private final CookieService cookieUtils;
    private final SecurityProperties securityProps;
    private final RoleService securityRoleService;

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Autowired
    public SecurityFilter(@Nonnull final SecurityService securityService,
                          @Nonnull final CookieService cookieUtils,
                          @Nonnull final SecurityProperties securityProps,
                          @Nonnull final RoleService securityRoleService) {
        this.securityService = securityService;
        this.cookieUtils = cookieUtils;
        this.securityProps = securityProps;
        this.securityRoleService = securityRoleService;
    }

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
                                    @Nonnull final HttpServletResponse response,
                                    @Nonnull final FilterChain filterChain)
            throws ServletException, IOException {
        authorize(request);
        filterChain.doFilter(request, response);
    }

    private void authorize(@Nonnull final HttpServletRequest request) {
        String sessionCookieValue = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;
        // Token verification
        final boolean strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
        final Cookie sessionCookie = cookieUtils.getCookie("session");
        final String token = securityService.getBearerToken(request);
        try {
            if (sessionCookie != null) {
                sessionCookieValue = sessionCookie.getValue();
                decodedToken = firebaseAuth.verifySessionCookie(sessionCookieValue,
                        securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
                type = Credentials.CredentialType.SESSION;
            } else if (!strictServerSessionEnabled && token != null && !token.equals("null")
                    && !token.equalsIgnoreCase("undefined")) {
                decodedToken = firebaseAuth.verifyIdToken(token);
                type = Credentials.CredentialType.ID_TOKEN;
            }
        } catch (FirebaseAuthException e) {
            log.error("Firebase Exception:: ", e.getLocalizedMessage());
        }
        final List<GrantedAuthority> authorities = new ArrayList<>();
        final User user = firebaseTokenToUserDto(decodedToken);
        // Handle roles
        if (user != null) {
            // Handle Super Role
            if (securityProps.getSuperAdmins().contains(user.getEmail())) {
                assert decodedToken != null;
                if (!decodedToken.getClaims().containsKey(RoleConstants.ROLE_SUPER.getRole())) {
                    try {
                        securityRoleService.addRole(decodedToken.getUid(), RoleConstants.ROLE_SUPER.getRole());
                    } catch (Exception e) {
                        log.error("Super Role registration exception ", e);
                    }
                }
                authorities.add(new SimpleGrantedAuthority(RoleConstants.ROLE_SUPER.getRole()));
            }
            // Handle Other roles
            assert decodedToken != null;
            decodedToken.getClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));
            // Set security context
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
                    new Credentials(type, decodedToken, token, sessionCookieValue), authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private User firebaseTokenToUserDto(final FirebaseToken decodedToken) {
        User user = null;
        if (decodedToken != null) {
            user = new User();
            user.setUid(decodedToken.getUid());
            user.setName(decodedToken.getName());
            user.setEmail(decodedToken.getEmail());
            user.setPicture(decodedToken.getPicture());
            user.setIssuer(decodedToken.getIssuer());
            user.setEmailVerified(decodedToken.isEmailVerified());
        }
        return user;
    }

}
