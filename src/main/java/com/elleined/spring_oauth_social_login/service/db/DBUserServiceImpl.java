package com.elleined.spring_oauth_social_login.service.db;

import com.elleined.spring_oauth_social_login.dto.user.DBUserDTO;
import com.elleined.spring_oauth_social_login.exception.resource.ResourceNotFoundException;
import com.elleined.spring_oauth_social_login.mapper.UserMapper;
import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.model.user.User;
import com.elleined.spring_oauth_social_login.repository.DBUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class DBUserServiceImpl implements DBUserService {
    private final PasswordEncoder passwordEncoder;

    private final DBUserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public DBUser save(String email, String password, String name, String image) {
        String encodedPassword = passwordEncoder.encode(password);
        DBUser dbUser = userMapper.toEntity(email, encodedPassword, name, image);

        userRepository.save(dbUser);
        log.debug("Saving db user success");
        return dbUser;
    }

    @Override
    public DBUser getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email of " + email + " doesn't exists!"));
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(email::equalsIgnoreCase);
    }

    @Override
    public DBUserDTO loadUserByUsername(String username) throws UsernameNotFoundException {
        DBUser dbUser = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User with email of " + username + " doesn't exists!"));
        return userMapper.toDTO(dbUser, new ArrayList<>()); // No authorities
    }
}
