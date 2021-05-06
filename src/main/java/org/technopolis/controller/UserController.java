package org.technopolis.controller;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.controller.facade.UserFacade;
import org.technopolis.dto.logic.PaymentDTO;

import javax.annotation.Nonnull;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open")
public class UserController {

    private final UserFacade userFacade;
    private final ActiveUserFacade activeUserFacade;

    @Autowired
    public UserController(@Nonnull final UserFacade userFacade,
                          @Nonnull final ActiveUserFacade activeUserFacade) {
        this.userFacade = userFacade;
        this.activeUserFacade = activeUserFacade;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String firebaseToken)
            throws FirebaseAuthException {
        return userFacade.authenticate(firebaseToken);
    }

    @GetMapping(value = "/initiatives")
    public ResponseEntity<Object> getInitiatives(
            @RequestHeader(required = false, value = SecurityConstants.HEADER_STRING) String token) {
        return token == null ? userFacade.getInitiatives() : activeUserFacade.getInitiatives(token.substring(7));
    }

    @PostMapping(value = "/payment")
    public ResponseEntity<?> pay(@Valid @RequestBody final PaymentDTO model) {
        return userFacade.pay(model);
    }

    @GetMapping(value = "/payment/{id}")
    public ResponseEntity<?> getSponsors(@PathVariable final Integer id) {
        return userFacade.getSponsors(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable final Integer id) {
        return userFacade.getUser(id);
    }
}
