package com.momenting.momentingapp.controller;

import com.momenting.momentingapp.dto.ResponseDto;
import com.momenting.momentingapp.dto.UserDto;
import com.momenting.momentingapp.model.User;
import com.momenting.momentingapp.security.TokenProvider;
import com.momenting.momentingapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            //요청을 이용해 저장할 사용자 만들기
            User user = User.builder()
                    .email(userDto.getEmail())
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();

            //서비스를 이용해 리포지터리에 저장
            User registerUser = userService.create(user);

            // 사용자 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDto를 사용하지 않고 UserDto를 리턴
            UserDto reponseUserDto = UserDto.builder()
                    .email(registerUser.getEmail())
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(reponseUserDto);
        } catch (Exception e) {

            ResponseDto responseDto = ResponseDto.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        User user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword(), passwordEncoder);

        if (user != null) {
            //Token 생성
            final String token = tokenProvider.create(user);
            final  UserDto responseUserDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .username(user.getUsername())
                    .token(token)
                    .build();
            return  ResponseEntity.ok().body(responseUserDto);
        } else {
            ResponseDto responseDto = ResponseDto.builder()
                    .error("로그인 정보가 맞지 않습니다.")
                    .build();
            return  ResponseEntity.badRequest().body(responseDto);
        }
    }
}
