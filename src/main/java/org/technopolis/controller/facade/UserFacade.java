package org.technopolis.controller.facade;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;
import org.technopolis.dto.entities.ActiveUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;
import org.technopolis.dto.logic.PaymentDTO;

import javax.annotation.Nonnull;
import java.util.List;

public interface UserFacade {

    ResponseEntity<?> authenticate(final String firebaseToken) throws FirebaseAuthException;

    ResponseEntity<?> pay(@Nonnull final PaymentDTO paymentDTO);

    ResponseEntity<Object> getInitiatives();

    ResponseEntity<Object> getSponsors(@Nonnull final Integer id);

    ResponseEntity<Object> getUser(@Nonnull final Integer id);
}
