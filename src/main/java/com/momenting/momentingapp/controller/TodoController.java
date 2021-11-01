package com.momenting.momentingapp.controller;

import com.momenting.momentingapp.dto.ResponseDto;
import com.momenting.momentingapp.dto.TodoDto;
import com.momenting.momentingapp.model.Todo;
import com.momenting.momentingapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@Slf4j
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDto<String> reponse = ResponseDto.<String>builder().data(list).build();
        return ResponseEntity.ok().body(reponse);
    }

    @PostMapping
    public TodoDto createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto) {
        Todo entity = TodoDto.toEntity(dto);
            entity.setId(null);
            entity.setUserId(userId);
        return service.create(entity);
    }

    @GetMapping
    public ResponseEntity<?> getTodoList(@AuthenticationPrincipal String userId) {

        List<Todo> entities = service.retrieve(userId);

        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto) {

        Todo entity = TodoDto.toEntity(dto);

        entity.setUserId(userId);

        List<Todo> entities = service.update(entity);

        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());

        ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto) {

        try {
            Todo entity = TodoDto.toEntity(dto);
            entity.setUserId(userId);
            List<Todo> entities = service.delete(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<TodoDto> response = ResponseDto.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

}
