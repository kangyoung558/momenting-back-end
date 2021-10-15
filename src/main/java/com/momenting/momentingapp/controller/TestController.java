package com.momenting.momentingapp.controller;

import com.momenting.momentingapp.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testControoler() {
        return "Hello do about something";
    }

    @GetMapping("/id")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello do about" + id;
    }

    @GetMapping("/testResponseBody")
    public ResponseDto<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("hello i am ResponseDto");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResposeEntity() {
        List<String> list = new ArrayList<>();
        list.add("hello i am ResponseEntity and 400");
        ResponseDto<String> response = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.badRequest().body(response);
    }
}
