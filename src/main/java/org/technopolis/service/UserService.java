package org.technopolis.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.service.shared.RegisterUserInit;

import javax.annotation.Nonnull;

public interface UserService extends UserDetailsService {

    ActiveUser registerUser(@Nonnull final RegisterUserInit init);

    ActiveUser getUser(@Nonnull final Integer id);

}
