package org.technopolis.configuration.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.technopolis.data.actor.ActiveUserRepository;
import org.technopolis.entity.actors.ActiveUser;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutionException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ActiveUserRepository activeUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(@Nonnull final String username) {
        final ActiveUser activeUser = activeUserRepository.findByFirebaseToken(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        try {
            return new UserDetailsImpl(activeUser);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
