package com.momenting.momentingapp.service;


import com.momenting.momentingapp.model.Post;
import com.momenting.momentingapp.persistence.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    //post 생성
    @Transactional
    public List<Post> create(final Post post) {
        log.info("Post create service.....");

        validate(post);

        postRepository.save(post);

        log.info("Entity Id : {} is saved", post.getId());

        return postRepository.findByUserId(post.getUserId());
    }

    public Post getPost (String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 게시글은  없습니다. id=" + postId));
    }


    //검색
    @Transactional
    public List<Post> retrieve(final String userId) {
        return postRepository.findByUserId(userId);
    }

    //수정
    @Transactional
    public List<Post> update(final Post post) {
        log.info("Post update service...");
        validate(post);

        final Optional<Post> original = postRepository.findById(post.getId());

        original.ifPresent(p -> {
            p.setContent(p.getContent());
            p.setSecret(p.isSecret());

            postRepository.save(p);
            log.info("Entity Id : {} is updated", post.getId());
        });
        return retrieve(post.getUserId());
    }

    //삭제
    @Transactional
    public List<Post> delete(final Post post) {
        log.info("Post delete service...");
        validate(post);

        try {
            postRepository.delete(post);
            log.info("Entity Id : {} is deleted", post.getId());
        } catch (Exception e) {
            log.error("error deleting entity", post.getId(), e);
            throw new RuntimeException("error deleting entity" + post.getId(), e);
        }
        return retrieve(post.getUserId());
    }


    private void validate(final Post entity) {
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
