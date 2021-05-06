package org.technopolis.data.logic;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.technopolis.data.BaseRepository;
import org.technopolis.entity.logic.Initiative;
import org.technopolis.entity.logic.Payment;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends BaseRepository<Payment> {

    @Query(value = "SELECT sum(u.payment) FROM payment u WHERE u.initiative_id = ?1", nativeQuery = true)
    Optional<Integer> findSumPaymentsByInitiative(@Nonnull final Integer initiativeId);

    @Query(value = "SELECT u.active_user_id FROM payment u WHERE u.initiative_id = ?1", nativeQuery = true)
    List<Integer> findActiveUsersByInitiative(@Nonnull final Integer initiativeId);

    List<Payment> findAllByInitiative(@Nonnull final Initiative initiative);
}
