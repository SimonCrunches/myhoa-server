package org.technopolis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ExampleController {
    @GetMapping(value = "/api/open/example")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiOpen() {
        return new HashMap<String, String>();
    }

    @GetMapping(value = "/api/active_user/example")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiActiveUser() {
        return new HashMap<String, String>();
    }

    @GetMapping(value = "/api/expert/example")
    @ResponseStatus(code = HttpStatus.OK)
    public Object apiExpert() {
        return new HashMap<String, String>();
    }
}