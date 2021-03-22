package org.technopolis.controller.facade.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.SecurityConfig;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.controller.facade.AuthFacade;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

@Service
public class AuthFacadeImpl implements AuthFacade {

    private final FirebaseAuth firebaseAuth;
    private final FirebaseService firebaseService;
    private final UserService userService;

    @Autowired
    public AuthFacadeImpl(@Nonnull final FirebaseAuth firebaseAuth,
                          @Nonnull final FirebaseService firebaseService,
                          @Nonnull final UserService userService) {
        this.firebaseAuth = firebaseAuth;
        this.firebaseService = firebaseService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public ResponseEntity<?> registerUser(final String firebaseToken) throws FirebaseAuthException {
        if (StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        final FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
        final UserRecord userRecord = firebaseAuth.getUser(tokenHolder.getUid());
        firebaseAuth.setCustomUserClaims(userRecord.getUid(), Map.of(SecurityConfig.Roles.ROLE_ACTIVE_USER, true));
        System.out.println(userRecord.getCustomClaims());
        final ActiveUser user = userService.registerUser(new RegisterUserInit(tokenHolder.getName(), tokenHolder.getEmail(), tokenHolder.getUid()));

        return ResponseEntity.ok(List.of(
                Map.of("accessToken", user.getFirebaseToken(), "username", user.getUsername(), "email", user.getEmail(), "roles", user.getAuthorities())
        ));
    }

}
