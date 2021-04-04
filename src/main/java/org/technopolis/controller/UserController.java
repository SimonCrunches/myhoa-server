package org.technopolis.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.UserFacade;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open")
public class UserController {

    @Autowired
    private UserFacade facade;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String firebaseToken)
            throws FirebaseAuthException {
        return facade.authenticate(firebaseToken);
    }

    @GetMapping("/initiatives")
    public ResponseEntity<Object> getInitiatives() {
        return facade.getInitiatives();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUser(@PathVariable final String username) {
        return facade.getUser(username);
    }
}
