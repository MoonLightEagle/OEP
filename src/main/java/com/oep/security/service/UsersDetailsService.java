package com.oep.security.service;

import com.oep.exception.ServiceException;
import com.oep.repository.UserRepository;
import com.oep.security.UsersDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UsersDetails(userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ServiceException("User not found!")));
    }

}
