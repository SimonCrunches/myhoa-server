package org.technopolis.data.actor;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.ActiveUser;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface ActiveUserRepository extends BaseRepository<ActiveUser> {

    Optional<ActiveUser> findByFirstName(@Nonnull final String firstName);

    Optional<ActiveUser> findByLastName(@Nonnull final String lastName);

    Optional<ActiveUser> findByFirebaseToken(@Nonnull final String firebaseToken);

    Optional<ActiveUser> findByJwtToken(@Nonnull final String jwtToken);
}
