package com.jwt.auth.Service.auth;

import com.jwt.auth.Entity.Modal.User;

import com.jwt.auth.Repository.UserRepository;
import com.jwt.auth.Service.Token.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = repository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()){
            return "username was already in use";
        }

        if(repository.findByEmail(user.getEmail()).isPresent()){
            return "email was already in use";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return "User Added Successfully";
    }
}
