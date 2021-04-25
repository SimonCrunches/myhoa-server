package org.technopolis.controller.facade;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;
import org.technopolis.dto.entities.ActiveUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface UserFacade {

    ResponseEntity<?> authenticate(final String firebaseToken) throws FirebaseAuthException;

    ResponseEntity<Object> getInitiatives();

    ResponseEntity<Object> getUser(@Nonnull final Integer id);
}
