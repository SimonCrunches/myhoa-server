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
import org.technopolis.dto.entities.ActiveUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.enums.Category;
import org.technopolis.entity.logic.Initiative;
import org.technopolis.response.FirebaseResponse;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.UserService;
import org.technopolis.service.shared.RegisterUserInit;
import org.technopolis.utils.CommonUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        final String jwt = jwtUtils.generateJwtToken(user.getPassword());
        return ResponseEntity.ok(new FirebaseResponse(jwt,
                user.getUsername(),
                user.getEmail(),
                user.getAuthorities().stream().findFirst().get()));
    }

    @Override
    public ResponseEntity<Object> getInitiatives() {
        final List<InitiativeDTO> initiatives = new ArrayList<>();
        for (final Initiative initiative : initiativeRepository.findAll()) {
            initiatives.add(new InitiativeDTO(initiative));
        }
        return ResponseEntity.ok(initiatives);
    }

    @Override
    public ResponseEntity<Object> getUser(@Nonnull final Integer id) {
        final ActiveUser user = userService.getUser(id);
        return user == null ? new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(new ActiveUserDTO(user));
    }

}
