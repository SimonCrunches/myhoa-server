package org.technopolis.service;

import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;

public interface FirebaseService {

    FirebaseTokenHolder parseToken(final String idToken);

}