package org.technopolis.security.model;

import lombok.Data;

@Data
public class FirebaseProperties {

    private int sessionExpiryInDays;
    private boolean enableStrictServerSession;
    private boolean enableCheckSessionRevoked;
    private boolean enableLogoutEverywhere;

}
