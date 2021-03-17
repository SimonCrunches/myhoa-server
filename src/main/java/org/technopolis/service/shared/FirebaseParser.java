package org.technopolis.service.shared;

import com.google.firebase.auth.FirebaseAuth;
import org.apache.commons.lang3.StringUtils;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.service.exception.FirebaseTokenInvalidException;

public class FirebaseParser {

    public FirebaseTokenHolder parseToken(final String idToken) {
        if (StringUtils.isBlank(idToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        try {
            return new FirebaseTokenHolder(FirebaseAuth.getInstance().verifyIdToken(idToken));
        } catch (Exception e) {
            throw new FirebaseTokenInvalidException(e.getMessage());
        }
    }
}
