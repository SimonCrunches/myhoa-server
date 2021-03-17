package org.technopolis.data.actor;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.Role;

import javax.annotation.Nonnull;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {
    Optional<Role> findRoleByAuthority(@Nonnull final String authority);
}
