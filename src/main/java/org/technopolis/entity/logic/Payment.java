package org.technopolis.entity.logic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.technopolis.entity.actors.ActiveUser;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_user_id")
    private ActiveUser activeUser;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiative_id", nullable = false)
    private Initiative initiative;

    @Column(name = "payment", nullable = false)
    private Integer payment;

    public Payment(@Nonnull final Initiative initiative,
                   final ActiveUser activeUser,
                   @Nonnull final Integer payment) {
        this.activeUser = activeUser;
        this.initiative = initiative;
        this.payment = payment;
    }
}
