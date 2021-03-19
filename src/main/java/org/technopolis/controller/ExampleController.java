package org.technopolis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api")
public class ExampleController {

    @GetMapping(value = "/open/example")
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiOpen() {
        return new HashMap<String, String>();
    }

    @GetMapping(value = "/active_user/example")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiActiveUser() {
        return new HashMap<String, String>();
    }

    @GetMapping(value = "/expert/example")
    @PreAuthorize("hasRole('ROLE_EXPERT')")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiExpert() {
        return new HashMap<String, String>();
    }
}