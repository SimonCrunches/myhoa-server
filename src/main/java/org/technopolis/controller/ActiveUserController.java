package org.technopolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.dto.EditInitiativeDTO;
import org.technopolis.dto.EditUserDTO;
import org.technopolis.dto.InitiativeDTO;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/active_user")
@PreAuthorize("hasRole('ROLE_ACTIVE_USER')")
public class ActiveUserController {

    @Autowired
    private ActiveUserFacade facade;

    @PostMapping(value = "/initiatives")
    public ResponseEntity<?> addInitiative(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                           @Valid @RequestBody final InitiativeDTO model) {
        return facade.addInitiative(token.substring(7), model);
    }

    @PutMapping(value = "/initiatives")
    public ResponseEntity<?> editInitiative(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                            @Valid @RequestBody final EditInitiativeDTO model) {
        return facade.editInitiative(token.substring(7), model);
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<Object> getUser(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token) {
        return facade.getUser(token.substring(7));
    }

    @PutMapping(value = "/profile")
    public ResponseEntity<?> editUser(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                      @Valid @RequestBody final EditUserDTO model) {
        return facade.editUser(token.substring(7), model);
    }
}
