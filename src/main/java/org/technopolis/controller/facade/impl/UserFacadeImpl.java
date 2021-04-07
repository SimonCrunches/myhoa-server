package org.technopolis.controller.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.controller.facade.UserFacade;
import org.technopolis.data.logic.InitiativeRepository;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.response.FirebaseResponse;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;

@Service
public class UserFacadeImpl implements UserFacade {

    private final FirebaseService firebaseService;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final InitiativeRepository initiativeRepository;

    @Autowired
    public UserFacadeImpl(@Nonnull final FirebaseService firebaseService,
                          @Nonnull final UserService userService,
                          @Nonnull final JwtUtils jwtUtils,
                          @Nonnull final InitiativeRepository initiativeRepository) {
        this.firebaseService = firebaseService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.initiativeRepository = initiativeRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<?> authenticate(final String firebaseToken) {
        if (StringUtils.isBlank(firebaseToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        final FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
        final ActiveUser user = userService.registerUser(new RegisterUserInit(tokenHolder.getName(),
                tokenHolder.getEmail(),
                tokenHolder.getPicture(),
                tokenHolder.getUid()));
        final String jwt = jwtUtils.generateJwtToken(user.getUsername());
        return ResponseEntity.ok(new FirebaseResponse(jwt,
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities().stream().findFirst().get()));
    }

    @Override
    public ResponseEntity<Object> getInitiatives() {
        return ResponseEntity.ok(initiativeRepository.findAll());
    }

    @Override
    public ResponseEntity<Object> getUser(@Nonnull final Integer id) {
        final ActiveUser user = userService.getUser(id);
        return user == null ? new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(user);
    }

}
