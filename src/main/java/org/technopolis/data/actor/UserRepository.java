package org.technopolis.data.actor;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.Credentials;
import org.technopolis.entity.actors.User;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByFirstName(@Nonnull final String firstName);

    Optional<User> findByLastName(@Nonnull final String lastName);

    Optional<User> findByCredentials(@Nonnull final Credentials credentials);
}
