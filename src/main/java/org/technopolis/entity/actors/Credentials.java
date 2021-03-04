package org.technopolis.entity.actors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.technopolis.entity.AbstractEntity;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "credentials")
public final class Credentials extends AbstractEntity {

    @Column(unique = true, name = "login", nullable = false)
    @NotBlank(message = "Login is mandatory")
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(unique = true, name = "email", nullable = false)
    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();*/

    public Credentials(@Nonnull final String email,
                       @Nonnull final String password) {
        this.email = email;
        this.password = password;
        this.login = email.split("@")[0];
    }

    public Credentials(@Nonnull final String login,
                       @Nonnull final String password,
                       @Nonnull final String email) {
        this.login = login;
        this.email = email;
        this.password = password;
    }
}
