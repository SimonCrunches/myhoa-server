package org.technopolis.controller.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.controller.facade.AuthFacade;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;

@Service
public class AuthFacadeImpl implements AuthFacade {

    private final FirebaseService firebaseService;
    private final UserService userService;

    @Autowired
    public AuthFacadeImpl(@Nonnull final FirebaseService firebaseService,
                          @Nonnull final UserService userService) {
        this.firebaseService = firebaseService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void registerUser(final String firebaseToken) {
        if (StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        final FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
        userService.registerUser(new RegisterUserInit(tokenHolder.getName(), tokenHolder.getEmail(), tokenHolder.getUid()));
    }

}
