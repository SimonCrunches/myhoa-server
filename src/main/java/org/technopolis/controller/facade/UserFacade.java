package org.technopolis.controller.facade;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;

public interface UserFacade {

    ResponseEntity<?> authenticate(final String firebaseToken) throws FirebaseAuthException;

    ResponseEntity<Object> getInitiatives();
}
