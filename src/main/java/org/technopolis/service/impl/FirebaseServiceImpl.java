package org.technopolis.service.impl;

import org.springframework.stereotype.Service;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.service.FirebaseService;
import org.technopolis.service.shared.FirebaseParser;

@Service
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public FirebaseTokenHolder parseToken(String firebaseToken) {
        return new FirebaseParser().parseToken(firebaseToken);
    }
}
