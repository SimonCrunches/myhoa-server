package org.technopolis.configuration.security.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    private final FirebaseAuth firebaseAuth;

    @Autowired
    public FirebaseAuthenticationProvider(@Nonnull final FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public boolean supports(@Nonnull final Class<?> authentication) {
        return (FirebaseAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(@Nonnull final Authentication authentication)
            throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        final FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;
        final FirebaseTokenHolder holder = (FirebaseTokenHolder) token.getCredentials();
        FirebaseAuthenticationToken result = null;
        try {
            final FirebaseToken firebaseToken = firebaseAuth.verifyIdToken(holder.getIdToken());
            final FirebaseTokenHolder tokenHolder = new FirebaseTokenHolder(firebaseToken, holder.getIdToken());
            final UserRecord userRecord = firebaseAuth.getUser(tokenHolder.getUid());
            result = new FirebaseAuthenticationToken(userRecord.getUid(),
                    tokenHolder,
                    userRecord.getCustomClaims().keySet().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return result;
    }

}
