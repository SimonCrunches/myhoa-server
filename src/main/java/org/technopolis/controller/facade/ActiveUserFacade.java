package org.technopolis.controller.facade;

import org.springframework.http.ResponseEntity;
import org.technopolis.dto.EditInitiativeDTO;
import org.technopolis.dto.EditUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;

import javax.annotation.Nonnull;

public interface ActiveUserFacade {

    ResponseEntity<?> addInitiative(@Nonnull final String token,
                                    @Nonnull final InitiativeDTO model);

    ResponseEntity<?> editInitiative(@Nonnull final String token,
                                     @Nonnull final EditInitiativeDTO model,
                                     @Nonnull final Integer id);

    ResponseEntity<Object> getInitiatives(@Nonnull final String token);

    ResponseEntity<?> editUser(@Nonnull final String token,
                               @Nonnull final EditUserDTO model);

    ResponseEntity<Object> getUser(@Nonnull final String token);

    ResponseEntity<?> deleteInitiative(@Nonnull final String token,
                                       @Nonnull final Integer id);

    ResponseEntity<?> addFavourites(@Nonnull final String token,
                                   @Nonnull final Integer id);

    ResponseEntity<Object> getFavourites(@Nonnull final String token);

    ResponseEntity<?> deleteFavourites(@Nonnull final String token,
                                       @Nonnull final Integer id);
}
