package org.technopolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.AuthFacade;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/open")
@PreAuthorize("hasAnyRole('ROLE_ANONYMOUS')")
public class AuthController {

    @Autowired
    private AuthFacade facade;

    @PostMapping(value = "/firebase/login")
    public void signUp(@RequestHeader(value = SecurityConstants.HEADER_FIREBASE) String firebaseToken) {
        facade.registerUser(firebaseToken);
    }
}
