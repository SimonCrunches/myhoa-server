package org.technopolis.data.actor;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.ActiveUser;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface ActiveUserRepository extends BaseRepository<ActiveUser> {

    Optional<ActiveUser> findByUsername(@Nonnull final String login);

    Optional<ActiveUser> findByFirebaseToken(@Nonnull final String firebaseToken);

    Boolean existsByFirebaseToken(@Nonnull final String firebaseToken);

}
