package com.momenting.momentingapp.service;

import com.momenting.momentingapp.model.TodoEntity;
import com.momenting.momentingapp.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public String testService() {
        TodoEntity entity = TodoEntity.builder()
                .content("첫번째 투두 리스트")
                .endDate(LocalDate.of(2021,10,20))
                .build();
        todoRepository.save(entity);
        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();
        return savedEntity.getContent();
    }
    
    //todo생성
    public List<TodoEntity> create(final TodoEntity entity) {
        // Validations
        validate(entity);

        todoRepository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());
        return todoRepository.findByUserId(entity.getUserId());
    }

    //검색
    public List<TodoEntity> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    //수정 구현
    public List<TodoEntity> update(final TodoEntity entity) {
        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setContent(entity.getContent());
            todo.setEndDate(entity.getEndDate());
            todo.setDone(entity.isDone());

            todoRepository.save(todo);
        });
        return retrieve(entity.getUserId());
    }
    
    //삭제 구현
    public List<TodoEntity> delete(final TodoEntity entity) {
        validate(entity);
        try{
            todoRepository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw  new RuntimeException("error deleting entity" + entity.getId(), e);
        }
        return retrieve(entity.getUserId());
    }

    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
            
        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
    
    

}
