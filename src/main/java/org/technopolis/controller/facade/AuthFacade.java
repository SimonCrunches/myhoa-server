package org.technopolis.controller.facade;

import javax.annotation.Nonnull;

public interface AuthFacade {

    void registerUser(final String firebaseToken);
}
