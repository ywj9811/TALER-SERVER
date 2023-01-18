package com.demo.service;

import com.demo.domain.User;
import com.demo.repository.ParentRepo;
import com.demo.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        if(username.contains(")")){
            String parentName = username.substring(username.lastIndexOf(")")+1);

            return parentRepo.findByNickname(parentName)
                    .map(parent -> createUser(parentName, parent))
                    .orElseThrow(() -> new UsernameNotFoundException(parentName + " -> 데이터베이스에서 찾을 수 없습니다."));
        }

        return userRepo.findByNickname(username)
                .map(user -> createUser(username, user))
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, UserDetails userDetails) {
        if (userDetails.isEnabled()) {
            throw new RuntimeException(username + " -> 삭제된 유저 입니다.");
        }

        return new org.springframework.security.core.userdetails.User(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}