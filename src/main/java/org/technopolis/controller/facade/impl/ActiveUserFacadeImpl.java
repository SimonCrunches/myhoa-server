package org.technopolis.controller.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.configuration.security.auth.jwt.JwtUtils;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.data.actor.ActiveUserRepository;
import org.technopolis.data.logic.FavouriteInitiativeRepository;
import org.technopolis.data.logic.InitiativeRepository;
import org.technopolis.data.logic.PaymentRepository;
import org.technopolis.dto.entities.OwnerFavouriteInitiativeDTO;
import org.technopolis.dto.logic.EditInitiativeDTO;
import org.technopolis.dto.logic.EditUserDTO;
import org.technopolis.dto.entities.ActiveUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.enums.Category;
import org.technopolis.entity.logic.FavouriteInitiative;
import org.technopolis.entity.logic.Initiative;
import org.technopolis.response.MessageResponse;
import org.technopolis.utils.CommonUtils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActiveUserFacadeImpl implements ActiveUserFacade {

    private final ActiveUserRepository activeUserRepository;
    private final InitiativeRepository initiativeRepository;
    private final PaymentRepository paymentRepository;
    private final FavouriteInitiativeRepository favouriteInitiativeRepository;
    private final JwtUtils jwtUtils;

    @Autowired
    public ActiveUserFacadeImpl(@Nonnull final ActiveUserRepository activeUserRepository,
                                @Nonnull final InitiativeRepository initiativeRepository,
                                @Nonnull final PaymentRepository paymentRepository,
                                @Nonnull final FavouriteInitiativeRepository favouriteInitiativeRepository,
                                @Nonnull final JwtUtils jwtUtils) {
        this.activeUserRepository = activeUserRepository;
        this.initiativeRepository = initiativeRepository;
        this.paymentRepository = paymentRepository;
        this.favouriteInitiativeRepository = favouriteInitiativeRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> addInitiative(@Nonnull final String token,
                                           @Nonnull final InitiativeDTO model) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        Initiative initiative = initiativeRepository.findByActiveUserAndTitle(user, model.getTitle()).orElse(null);
        if (initiative != null) {
            return new ResponseEntity<>("Initiative already exists", HttpStatus.FOUND);
        }
        initiative = Initiative.builder()
                .category(Category.convertToEntityAttribute(model.getCategory()))
                .title(model.getTitle())
                .description(model.getDescription())
                .latitude(model.getLatitude())
                .longitude(model.getLongitude())
                .activeUser(user)
                .creationDate(LocalDateTime.parse(LocalDateTime.now().format(CommonUtils.LOCALDATETIME), CommonUtils.LOCALDATETIME))
                .milestone(LocalDate.parse(model.getMilestone(), CommonUtils.LOCALDATE))
                .price(model.getPrice())
                .contractor(model.getContractor())
                .trash(false)
                .imageUrl(model.getImageUrl()).build();
        initiativeRepository.save(initiative);
        final Initiative addedInitiative = initiativeRepository.findByActiveUserAndTitle(user, model.getTitle()).orElse(null);
        if (addedInitiative == null) {
            return new ResponseEntity<>("Error when adding new initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully added!"));
    }

    @Override
    public ResponseEntity<?> editInitiative(@Nonnull final String token,
                                            @Nonnull final EditInitiativeDTO model,
                                            @Nonnull final Integer id) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findById(id).orElse(null);
        if (initiative == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        }
        if (model.getCategory() != null) {
            initiative.setCategory(Category.convertToEntityAttribute(model.getCategory()));
        }
        if (model.getTitle() != null) {
            initiative.setTitle(model.getTitle());
        }
        if (model.getDescription() != null) {
            initiative.setDescription(model.getDescription());
        }
        if (model.getMilestone() != null) {
            initiative.setMilestone(LocalDate.parse(model.getMilestone(), CommonUtils.LOCALDATE));
        }
        if (model.getPrice() != null) {
            initiative.setPrice(model.getPrice());
        }
        if (model.getContractor() != null) {
            initiative.setContractor(model.getContractor());
        }
        if (model.getImageUrl() != null) {
            initiative.setImageUrl(model.getImageUrl());
        }
        initiativeRepository.save(initiative);
        final Initiative addedInitiative = initiativeRepository.findByActiveUserAndTitle(user, model.getTitle()).orElse(null);
        if (addedInitiative == null) {
            return new ResponseEntity<>("Error when editing initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully edited!"));
    }

    @Override
    public ResponseEntity<Object> getInitiatives(@Nonnull final String token) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final List<OwnerFavouriteInitiativeDTO> initiatives = new ArrayList<>();
        for (final Initiative initiative : initiativeRepository.findByActiveUserAndTrash(user, false)) {
            initiatives.add(new OwnerFavouriteInitiativeDTO(initiative,
                    paymentRepository.findSumPaymentsByInitiative(initiative.getId()).orElse(0),
                    true,
                    false));
        }
        return ResponseEntity.ok(initiatives);
    }

    @Override
    public ResponseEntity<?> editUser(@Nonnull final String token,
                                      @Nonnull final EditUserDTO model) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        if (model.getFirstName() != null) {
            user.setFirstName(model.getFirstName());
        }
        if (model.getLastName() != null) {
            user.setLastName(model.getLastName());
        }
        if (model.getUsername() != null) {
            user.setUsername(model.getUsername());
        }
        if (model.getEmail() != null) {
            user.setEmail(model.getEmail());
        }
        activeUserRepository.save(user);
        final ActiveUser addedActiveUser = activeUserRepository.findByUsername(user.getUsername()).orElse(null);
        if (addedActiveUser == null) {
            return new ResponseEntity<>("Error when editing user", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("User successfully edited!"));
    }

    @Override
    public ResponseEntity<Object> getUser(@Nonnull final String token) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        return user == null ? new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(new ActiveUserDTO(user));
    }

    @Override
    public ResponseEntity<?> deleteInitiative(@Nonnull final String token,
                                              @Nonnull final Integer id) {
        if (!activeUserRepository.existsByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token))) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findById(id).orElse(null);
        if (initiative == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        }
        initiativeRepository.deleteById(initiative.getId());
        final Initiative deletedInitiative = initiativeRepository.findById(id).orElse(null);
        if (deletedInitiative != null) {
            return new ResponseEntity<>("Error when deleting initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully deleted!"));
    }

    @Override
    public ResponseEntity<?> addFavourites(@Nonnull final String token,
                                           @Nonnull final Integer id) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findById(id).orElse(null);
        if (initiative == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        } else if (initiative.getTrash()) {
            return new ResponseEntity<>("Can't add trash initiative to favourite", HttpStatus.BAD_REQUEST);
        }
        FavouriteInitiative favInit = favouriteInitiativeRepository.findByActiveUserAndInitiative(user, initiative).orElse(null);
        if (favInit != null) {
            return ResponseEntity.ok(new MessageResponse("Favourite initiative already exists"));
        }
        favInit = FavouriteInitiative.builder()
                .activeUser(user)
                .initiative(initiative).build();
        favouriteInitiativeRepository.save(favInit);
        final FavouriteInitiative addedFavInit = favouriteInitiativeRepository.findByActiveUserAndInitiative(user, initiative).orElse(null);
        if (addedFavInit == null) {
            return new ResponseEntity<>("Error when adding new favourite initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Favourite initiative successfully added!"));
    }

    @Override
    public ResponseEntity<Object> getFavourites(@Nonnull final String token) {
        final ActiveUser user = activeUserRepository.findByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token)).orElse(null);
        return user == null ? new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(favouriteInitiativeRepository.findByActiveUser(user).stream()
                .map(favouriteInitiative ->
                        new OwnerFavouriteInitiativeDTO(initiativeRepository.findById(favouriteInitiative.getInitiative().getId()).get(),
                                paymentRepository.findSumPaymentsByInitiative(favouriteInitiative.getInitiative().getId()).orElse(0),
                                false, true))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<?> deleteFavourites(@Nonnull final String token,
                                              @Nonnull final Integer id) {
        if (!activeUserRepository.existsByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token))) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final FavouriteInitiative favInit = favouriteInitiativeRepository.findById(id).orElse(null);
        if (favInit == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        }
        favouriteInitiativeRepository.deleteById(favInit.getId());
        final FavouriteInitiative deletedFavInit = favouriteInitiativeRepository.findById(id).orElse(null);
        if (deletedFavInit != null) {
            return new ResponseEntity<>("Error when deleting initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully deleted!"));
    }

    @Override
    public ResponseEntity<?> reportInitiative(@Nonnull final String token,
                                              @Nonnull final Integer id) {
        if (!activeUserRepository.existsByFirebaseToken(jwtUtils.getFirebaseTokenFromJwtToken(token))) {
            return new ResponseEntity<>("User doesnt exist", HttpStatus.NOT_FOUND);
        }
        final Initiative initiative = initiativeRepository.findById(id).orElse(null);
        if (initiative == null) {
            return new ResponseEntity<>("Initiative doesnt exist", HttpStatus.NOT_FOUND);
        }
        initiative.incrementReports();
        if (initiative.getReports() > 10) {
            initiative.setTrash(true);
        }
        initiativeRepository.save(initiative);
        final Initiative trashInitiative = initiativeRepository.findById(id).orElse(null);
        if (trashInitiative != null && !trashInitiative.getTrash()) {
            return new ResponseEntity<>("Error when trashing initiative", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(new MessageResponse("Initiative successfully added to trash!"));
    }
}
