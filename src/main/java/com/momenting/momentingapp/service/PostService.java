package com.momenting.momentingapp.service;


import com.momenting.momentingapp.model.PostEntity;
import com.momenting.momentingapp.model.TodoEntity;
import com.momenting.momentingapp.persistence.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostEntity> create(final PostEntity postEntity) {
        log.info("Post create service.....");

        validate(postEntity);

        postRepository.save(postEntity);

        log.info("Entity Id : {} is saved", postEntity.getId());

        return postRepository.findByUserId(postEntity.getUserId());
    }

    private void validate(final PostEntity entity) {
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
