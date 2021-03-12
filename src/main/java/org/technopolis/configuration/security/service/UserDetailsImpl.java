package org.technopolis.configuration.security.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.technopolis.entity.actors.User;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Autowired
    private FirebaseAuth firebaseAuth;

    private static final long serialVersionUID = 1L;

    private String username;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(@Nonnull final User user) throws ExecutionException, InterruptedException {

        final FirebaseToken decodedToken = firebaseAuth.verifyIdTokenAsync(user.getFirebaseToken()).get();
        final List<GrantedAuthority> authorities = new ArrayList<>();
        decodedToken.getClaims().forEach((k, v) -> authorities.add(new SimpleGrantedAuthority(k)));

        this.username = decodedToken.getName();
        this.email = decodedToken.getEmail();
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }
}
