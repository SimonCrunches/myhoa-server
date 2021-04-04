package org.technopolis.controller.facade;

import org.springframework.http.ResponseEntity;
import org.technopolis.dto.EditInitiativeDTO;
import org.technopolis.dto.EditUserDTO;
import org.technopolis.dto.InitiativeDTO;

import javax.annotation.Nonnull;

public interface ActiveUserFacade {

    ResponseEntity<?> addInitiative(@Nonnull final String token,
                                    @Nonnull final InitiativeDTO model);

    ResponseEntity<?> editInitiative(@Nonnull final String token,
                                     @Nonnull final EditInitiativeDTO model);

    ResponseEntity<?> editUser(@Nonnull final String token,
                               @Nonnull final EditUserDTO model);

    ResponseEntity<Object> getUser(@Nonnull final String token);
}
