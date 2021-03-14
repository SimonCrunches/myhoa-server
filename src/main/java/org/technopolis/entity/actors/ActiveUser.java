package org.technopolis.entity.actors;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.entity.AbstractEntity;
import org.technopolis.entity.logic.Initiative;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "active_user")
public class ActiveUser extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    protected String firstName;

    @Column(name = "last_name")
    protected String lastName;

    @Column(name = "firebaseToken", nullable = false)
    protected String firebaseToken;

    @Column(name = "jwtToken", nullable = false)
    protected String jwtToken;

    public ActiveUser(@Nonnull final String firstName,
                      final String lastName,
                      @Nonnull final String firebaseToken,
                      @Nonnull final String jwtToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firebaseToken = firebaseToken;
        this.jwtToken = jwtToken;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "activeUser", fetch = FetchType.LAZY, orphanRemoval = true)
    protected Set<Initiative> createdInitiatives = new HashSet<>();

}
