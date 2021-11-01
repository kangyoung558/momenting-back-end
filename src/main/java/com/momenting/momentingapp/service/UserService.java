package com.momenting.momentingapp.service;

import com.momenting.momentingapp.model.User;
import com.momenting.momentingapp.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(final User user) {
        if (user == null || user.getEmail() == null) {
            throw new RuntimeException("유효하지 않은 인자값들 입니다.");
        }

        final String email = user.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("해당 이메일은 이미 존재합니다. {}", email);
            throw new RuntimeException("해당 이메일이 이미 존재 합니다");
        }
        return userRepository.save(user);
    }

    public User getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final User originalUser = userRepository.findByEmail(email);

        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }
}

