package org.technopolis.configuration.security.auth.firebase;

import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.technopolis.service.UserService;
import org.technopolis.service.exception.FirebaseUserNotExistsException;

import javax.annotation.Nonnull;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(@Nonnull final Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;
        final UserDetails details = userService.loadUserByUsername(authentication.getName());
        if (details == null) {
            throw new FirebaseUserNotExistsException();
        }

        return new FirebaseAuthenticationToken(details, token.getCredentials(), details.getAuthorities());
    }

}
