package org.technopolis.controller.facade;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;

public interface AuthFacade {

    ResponseEntity<?> registerUser(final String firebaseToken) throws FirebaseAuthException;
}
