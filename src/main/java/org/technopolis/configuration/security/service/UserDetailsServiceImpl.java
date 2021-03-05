package org.technopolis.configuration.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.data.actor.CredentialsRepository;
import org.technopolis.entity.actors.Credentials;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialsRepository credentialsRepository;

    @Autowired
    public UserDetailsServiceImpl(@Nonnull final CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(@NotNull final String username) throws UsernameNotFoundException {
        final Credentials credentials = credentialsRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(credentials);
    }
}
