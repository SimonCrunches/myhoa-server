package org.technopolis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.technopolis.controller.facade.ExpertFacade;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/expert")
@PreAuthorize("hasRole('ROLE_EXPERT')")
public class ExpertController {

    @Autowired
    private ExpertFacade facade;
}
