package org.technopolis.configuration.security.auth.firebase;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.auth.FirebaseToken;
import lombok.Data;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Map;

@Data
public class FirebaseTokenHolder {
    private FirebaseToken token;
    private String idToken;

    public FirebaseTokenHolder(@Nonnull final FirebaseToken token,
                               @Nonnull final String idToken) {
        this.token = token;
        this.idToken = idToken;
    }

    public String getEmail() {
        return token.getEmail();
    }

    public String getIssuer() {
        return token.getIssuer();
    }

    public String getName() {
        return token.getName();
    }

    public String getUid() {
        return token.getUid();
    }

    public Map<String, Object> getClaims() {
        return token.getClaims();
    }

    public String getPicture() {
        return token.getPicture();
    }

    /*public String getGoogleId() {
        return ((ArrayList<String>) ((ArrayMap) ((ArrayMap) token.getClaims().get("firebase"))
                .get("identities")).get("google.com")).get(0);
    }*/

}