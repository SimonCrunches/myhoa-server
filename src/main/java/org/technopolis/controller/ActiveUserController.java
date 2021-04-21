package org.technopolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.technopolis.configuration.security.SecurityConstants;
import org.technopolis.controller.facade.ActiveUserFacade;
import org.technopolis.dto.logic.EditInitiativeDTO;
import org.technopolis.dto.logic.EditUserDTO;
import org.technopolis.dto.entities.InitiativeDTO;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/active_user")
@PreAuthorize("hasAnyRole('ROLE_ACTIVE_USER', 'ROLE_EXPERT')")
public class ActiveUserController {

    @Autowired
    private ActiveUserFacade facade;

    @PostMapping(value = "/initiatives")
    public ResponseEntity<?> addInitiative(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                           @Valid @RequestBody final InitiativeDTO model) {
        return facade.addInitiative(token.substring(7), model);
    }

    @PutMapping(value = "/initiatives/{id}")
    public ResponseEntity<?> editInitiative(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                            @Valid @RequestBody final EditInitiativeDTO model,
                                            @PathVariable final Integer id) {
        return facade.editInitiative(token.substring(7), model, id);
    }

    @GetMapping(value = "/initiatives")
    public ResponseEntity<Object> getInitiatives(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token) {
        return facade.getInitiatives(token.substring(7));
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

    @DeleteMapping(value = "/initiatives/{id}")
    public ResponseEntity<?> deleteInitiative(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                              @PathVariable final Integer id) {
        return facade.deleteInitiative(token.substring(7), id);
    }

    @GetMapping(value = "/favourites")
    public ResponseEntity<Object> getFavourites(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token) {
        return facade.getFavourites(token.substring(7));
    }

    @PostMapping(value = "/favourites/{id}")
    public ResponseEntity<?> addFavourites(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                              @PathVariable final Integer id) {
        return facade.addFavourites(token.substring(7), id);
    }

    @DeleteMapping(value = "/favourites/{id}")
    public ResponseEntity<?> deleteFavourites(@RequestHeader(value = SecurityConstants.HEADER_STRING) String token,
                                              @PathVariable final Integer id) {
        return facade.deleteFavourites(token.substring(7), id);
    }
}
