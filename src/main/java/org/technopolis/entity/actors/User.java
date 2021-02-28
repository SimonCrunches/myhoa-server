package org.technopolis.entity.actors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name is mandatory")
    protected String firstName;

    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name is mandatory")
    protected String lastName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credentials_id")
    protected Credentials credentials;

}
