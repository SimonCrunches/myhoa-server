package org.technopolis.data.actor;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.Credentials;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface CredentialsRepository extends BaseRepository<Credentials> {

    Boolean existsCredentialsByEmail(@Nonnull final String email);

    Boolean existsCredentialsByLogin(@Nonnull final String login);

    Optional<Credentials> findByEmailAndPassword(@Nonnull final String email,
                                                 @Nonnull final String password);

    Optional<Credentials> findByEmail(@Nonnull final String email);

    Optional<Credentials> findByLogin(@Nonnull final String login);

    @Override
    void delete(@Nonnull final Credentials credentials);
}
