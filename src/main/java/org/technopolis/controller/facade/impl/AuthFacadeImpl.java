package org.technopolis.controller.facade.impl;

import com.google.firebase.auth.FirebaseAuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.controller.facade.AuthFacade;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.response.FirebaseResponse;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;

@Service
public class AuthFacadeImpl implements AuthFacade {

    private final FirebaseService firebaseService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthFacadeImpl(@Nonnull final FirebaseService firebaseService,
                          @Nonnull final UserService userService,
                          @Nonnull final JwtUtils jwtUtils) {
        this.firebaseService = firebaseService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Transactional
    @Override
    public ResponseEntity<?> registerUser(final String firebaseToken) throws FirebaseAuthException {
        if (StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        final FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
        /*final UserRecord userRecord = firebaseAuth.getUser(tokenHolder.getUid());
        firebaseAuth.setCustomUserClaims(userRecord.getUid(), Map.of(SecurityConfig.Roles.ROLE_ACTIVE_USER, true));*/
        final ActiveUser user = userService.registerUser(new RegisterUserInit(tokenHolder.getName(), tokenHolder.getEmail(), tokenHolder.getUid()));
        final String jwt = jwtUtils.generateJwtToken(user.getUsername());
        return ResponseEntity.ok(new FirebaseResponse(jwt,
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities().stream().findFirst().get()));
    }

}
