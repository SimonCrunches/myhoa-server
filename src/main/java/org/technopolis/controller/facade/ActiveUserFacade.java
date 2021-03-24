package org.technopolis.controller.facade;

import org.springframework.http.ResponseEntity;
import org.technopolis.dto.InitiativeDTO;

import javax.annotation.Nonnull;

public interface ActiveUserFacade {

    ResponseEntity<?> addInitiative(@Nonnull final String token,
                                    @Nonnull final InitiativeDTO model);

}
