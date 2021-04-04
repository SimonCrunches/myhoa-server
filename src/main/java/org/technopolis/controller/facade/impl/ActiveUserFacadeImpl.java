package org.technopolis.controller.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.data.actor.ActiveUserRepository;
import org.technopolis.data.logic.InitiativeRepository;
import org.technopolis.dto.EditInitiativeDTO;
import org.technopolis.dto.EditUserDTO;
import org.technopolis.dto.InitiativeDTO;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.enums.Category;
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

    @Transactional
    @Override
    public ResponseEntity<?> addInitiative(@Nonnull final String token,
                                           @Nonnull final InitiativeDTO model) {
        final ActiveUser activeUser = activeUserRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token)).orElse(null);
        if (activeUser == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findByActiveUserAndTitle(activeUser, model.getTitle()).orElse(null);
        if (initiative != null) {
            return new ResponseEntity<>("Initiative already exist", HttpStatus.FOUND);
        }
        final Initiative newInitiative = Initiative.builder()
                .category(Category.convertToEntityAttribute(model.getCategory()))
                .title(model.getTitle())
                .description(model.getDescription())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .activeUser(activeUser)
                .creationDate(LocalDateTime.parse(LocalDateTime.now().format(CommonUtils.LOCALDATETIME), CommonUtils.LOCALDATETIME))
                .milestone(LocalDate.parse(model.getMilestone(), CommonUtils.LOCALDATE))
                .price(model.getPrice())
                .contractor(model.getContractor()).build();
        initiativeRepository.save(newInitiative);
        final Initiative addedInitiative = initiativeRepository.findByActiveUserAndTitle(activeUser, model.getTitle()).orElse(null);
        if (addedInitiative == null) {
            return new ResponseEntity<>("Error when adding new initiative", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully added!"));
    }

    @Override
    public ResponseEntity<?> editInitiative(@Nonnull final String token,
                                            @Nonnull final EditInitiativeDTO model) {
        /*final ActiveUser activeUser = activeUserRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token)).orElse(null);
        if (activeUser == null) {
            return new ResponseEntity<>("ActiveUser doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findByActiveUserAndTitle(activeUser, model.getTitle()).orElse(null);
        if (initiative == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        }

        initiativeRepository.save(initiative);
        final Initiative addedInitiative = initiativeRepository.findByActiveUserAndTitle(activeUser, model.getTitle()).orElse(null);
        if (addedInitiative == null) {
            return new ResponseEntity<>("Error when adding new initiative", HttpStatus.NOT_FOUND);
        }*/
        return ResponseEntity.ok(new MessageResponse("Initiative successfully edited!"));
    }

    @Override
    public ResponseEntity<?> editUser(@Nonnull final String token,
                                      @Nonnull final EditUserDTO model) {
        return ResponseEntity.ok(new MessageResponse("User successfully edited!"));
    }

    @Override
    public ResponseEntity<Object> getUser(@Nonnull final String token) {
        final ActiveUser user = activeUserRepository.findByUsername(jwtUtils.getUserNameFromJwtToken(token)).orElse(null);
        return user == null ? new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(user);
    }
}
