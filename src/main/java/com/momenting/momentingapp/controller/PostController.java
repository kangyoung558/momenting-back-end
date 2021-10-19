package com.momenting.momentingapp.controller;

import com.momenting.momentingapp.dto.PostDto;
import com.momenting.momentingapp.dto.ResponseDto;
import com.momenting.momentingapp.model.PostEntity;
import com.momenting.momentingapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {

        try {
            PostEntity postEntity = PostDto.toEntity(postDto);

            postEntity.setId(null);

            postEntity.setUserId(userId);

            List<PostEntity> postEntities = postService.create(postEntity);

            List<PostDto> postDtos = postEntities.stream().map(PostDto::new).collect(Collectors.toList());

            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(postDtos).build();

            return  ResponseEntity.ok().body(response);
        } catch (Exception e) {

            String error = e.getMessage();
            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
