package org.technopolis.security.role;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.technopolis.security.model.SecurityProperties;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RoleService {

    private final FirebaseAuth firebaseAuth;
    private final SecurityProperties securityProps;

    @Autowired
    public RoleService(@Nonnull final SecurityProperties securityProps,
                       @Nonnull final FirebaseAuth firebaseAuth) {
        this.securityProps = securityProps;
        this.firebaseAuth = firebaseAuth;
    }

    public void addRole(@Nonnull final String uid,
                        @Nonnull final String role) throws Exception {
        try {
            final UserRecord user = firebaseAuth.getUser(uid);
            final Map<String, Object> claims = new HashMap<>();
            user.getCustomClaims().forEach(claims::put);
            if (securityProps.getValidApplicationRoles().contains(role)) {
                if (!claims.containsKey(role)) {
                    claims.put(role, true);
                }
                firebaseAuth.setCustomUserClaims(uid, claims);
            } else {
                throw new Exception("Not a valid Application role, Allowed roles => "
                        + securityProps.getValidApplicationRoles().toString());
            }

        } catch (FirebaseAuthException e) {
            log.error("Firebase Auth Error ", e);
        }

    }

    public void removeRole(@Nonnull final String uid,
                           @Nonnull final String role) {
        try {
            final UserRecord user = firebaseAuth.getUser(uid);
            final Map<String, Object> claims = new HashMap<>();
            user.getCustomClaims().forEach(claims::put);
            claims.remove(role);
            firebaseAuth.setCustomUserClaims(uid, claims);
        } catch (FirebaseAuthException e) {
            log.error("Firebase Auth Error ", e);
        }
    }

}
