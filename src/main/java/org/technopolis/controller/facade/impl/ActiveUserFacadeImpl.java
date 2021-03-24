package org.technopolis.controller.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.data.actor.ActiveUserRepository;
import org.technopolis.data.logic.InitiativeRepository;
import org.technopolis.dto.InitiativeDTO;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.logic.Category;
import org.technopolis.entity.logic.Initiative;
import org.technopolis.response.MessageResponse;
import org.technopolis.utils.CommonUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ActiveUserFacadeImpl implements ActiveUserFacade {

    private final ActiveUserRepository activeUserRepository;
    private final InitiativeRepository initiativeRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public ActiveUserFacadeImpl(@Nonnull final ActiveUserRepository activeUserRepository,
                                @Nonnull final InitiativeRepository initiativeRepository,
                                @Nonnull final JwtUtils jwtUtils) {
        this.activeUserRepository = activeUserRepository;
        this.initiativeRepository = initiativeRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> addInitiative(@Nonnull final String token,
                                           @Nonnull final InitiativeDTO model) {
        final ActiveUser activeUser = activeUserRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token)).orElse(null);
        if (activeUser == null) {
            return new ResponseEntity<>("ActiveUser doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findByActiveUserAndTitle(activeUser, model.getTitle()).orElse(null);
        if (initiative != null) {
            return new ResponseEntity<>("Initiative already exist", HttpStatus.FOUND);
        }
        final Initiative newInitiative = new Initiative();
        newInitiative.setCategory(Category.valueOf(model.getCategory()));
        newInitiative.setTitle(model.getTitle());
        newInitiative.setDescription(model.getDescription());
        newInitiative.setLatitude(model.getLatitude());
        newInitiative.setLongitude(model.getLongitude());
        newInitiative.setActiveUser(activeUser);
        newInitiative.setCreationDate(LocalDateTime.now());
        newInitiative.setMilestone(LocalDate.parse(model.getMilestone(), CommonUtils.LOCALDATE));
        newInitiative.setPrice(model.getPrice());
        newInitiative.setContractor(model.getContractor() == 1 ? Boolean.TRUE : Boolean.FALSE);
        initiativeRepository.save(newInitiative);
        return ResponseEntity.ok(new MessageResponse("Initiative successfully added!"));
    }
}
