package org.technopolis.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/active_user")
@PreAuthorize("hasRole('ROLE_ACTIVE_USER')")
public class ActiveUserController {

    /*@PostMapping(value = "/add_initiative")
    public ResponseEntity<?> signUp(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String firebaseToken)
            throws FirebaseAuthException {
        return facade.registerUser(firebaseToken);
    }*/
}
