package org.technopolis.service.shared;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.apache.commons.lang3.StringUtils;
import org.technopolis.configuration.security.auth.firebase.FirebaseTokenHolder;
import org.technopolis.service.exception.FirebaseTokenInvalidException;

import java.util.Map;

public class FirebaseParser {

    public FirebaseTokenHolder parseToken(final String idToken) {
        if (StringUtils.isBlank(idToken)) {
            throw new IllegalArgumentException("FirebaseTokenBlank");
        }
        try {
            final FirebaseToken token = FirebaseAuth.getInstance().verifyIdToken(idToken);
            final Map<String, Object> claims = token.getClaims();
            for (final Map.Entry<String, Object> entry : claims.entrySet()) {
                System.out.println("Claim: key - " + entry.getKey() + ", value - " + entry.getValue());
            }
            return new FirebaseTokenHolder(token, idToken);
        } catch (Exception e) {
            throw new FirebaseTokenInvalidException(e.getMessage());
        }
    }
}
