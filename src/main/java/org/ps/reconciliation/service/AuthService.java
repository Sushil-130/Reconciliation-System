package org.ps.reconciliation.service;

import org.ps.reconciliation.dto.AuthRequest;
import org.ps.reconciliation.model.User;
import org.ps.reconciliation.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public AuthService(PasswordEncoder passwordEncoder,UserRepository userRepository){
        this.passwordEncoder=passwordEncoder;
        this.userRepository=userRepository;
    }

    @Transactional
    public void signUp(AuthRequest authRequest){
        User user=new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        userRepository.save(user);
    }

//    @Transactional(readOnly = true)
//    public User getCurrentUser() {
//        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
//                getContext().getAuthentication().getPrincipal();
//        return userRepository.findByUsername(principal.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
//    }

}
