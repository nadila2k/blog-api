package com.nadila.blogapi.service.authService;

import com.nadila.blogapi.exception.ResourceNotFound;
import com.nadila.blogapi.model.Users;
import com.nadila.blogapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new ResourceNotFound("User not found"));

        return new BlogUserDetails(user);
    }
}
