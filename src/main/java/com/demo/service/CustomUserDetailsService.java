package com.demo.service;

import com.demo.domain.User;
import com.demo.repository.ParentRepo;
import com.demo.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    private final ParentRepo parentRepo;

    public CustomUserDetailsService(UserRepo userRepo, ParentRepo parentRepo) {
        this.userRepo = userRepo;
        this.parentRepo = parentRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        Optional<User> optionalUser = userRepo.findByNickname(username);
        if(optionalUser.orElse(null) != null){
            return optionalUser
                    .map(this::createUser)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        }else{
            return parentRepo.findByNickname(username)
                    .map(this::createUser)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
        }
    }

    private org.springframework.security.core.userdetails.User createUser(UserDetails userDetails) {
        if (!userDetails.isEnabled()) {
            throw new RuntimeException(userDetails.getUsername() + " -> 활성화되어 있지 않습니다.");
        }

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }


}