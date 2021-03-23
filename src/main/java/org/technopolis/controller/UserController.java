package org.technopolis.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.AuthFacade;
import org.technopolis.data.logic.InitiativeRepository;

import javax.annotation.Nonnull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open")
public class UserController {

    private final AuthFacade facade;
    private final InitiativeRepository initiativeRepository;

    @Autowired
    public UserController(@Nonnull final AuthFacade facade,
                          @Nonnull final InitiativeRepository initiativeRepository) {
        this.facade = facade;
        this.initiativeRepository = initiativeRepository;
    }

    @PostMapping(value = "/firebase/login")
    public ResponseEntity<?> signUp(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String firebaseToken)
            throws FirebaseAuthException {
        return facade.registerUser(firebaseToken);
    }

    @GetMapping("/initiatives")
    public ResponseEntity<Object> getInitiatives() {
        return ResponseEntity.ok(initiativeRepository.findAll());
    }
}
