package org.technopolis.data.logic;

import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.actors.ActiveUser;
import org.technopolis.entity.logic.FavouriteInitiative;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteInitiativeRepository extends BaseRepository<FavouriteInitiative> {

    Boolean existsByActiveUserAndInitiative(@Nonnull final ActiveUser activeUser,
                                             @Nonnull final Initiative initiative);

    List<FavouriteInitiative> findByActiveUser(@Nonnull final ActiveUser activeUser);

    List<FavouriteInitiative> findByInitiative(@Nonnull final Initiative initiative);

    Optional<FavouriteInitiative> findByActiveUserAndInitiative(@Nonnull final ActiveUser activeUser,
                                                                @Nonnull final Initiative initiative);
}
