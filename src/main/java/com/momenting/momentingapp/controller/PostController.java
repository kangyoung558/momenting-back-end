package com.momenting.momentingapp.controller;

import com.momenting.momentingapp.dto.PostDto;
import com.momenting.momentingapp.dto.ResponseDto;
import com.momenting.momentingapp.model.Post;
import com.momenting.momentingapp.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("post")
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;



    @PostMapping
    public ResponseEntity<?> createPost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {

        try {
            log.info("psotdto="+postDto);

            Post postEntity = PostDto.toEntity(postDto);

            postEntity.setId(null);

            postEntity.setUserId(userId);

            List<Post> postEntities = postService.create(postEntity);

            List<PostDto> postDtos = postEntities.stream().map(PostDto::new).collect(Collectors.toList());

            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(postDtos).build();

            return  ResponseEntity.ok().body(response);
        } catch (Exception e) {

            String error = e.getMessage();
            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> getPostList(@AuthenticationPrincipal String userId) {

        List<Post> postEntities = postService.retrieve(userId);

        List<PostDto> postDtos = postEntities.stream().map(PostDto::new).collect(Collectors.toList());

        ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(postDtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {
        Post postEntity = PostDto.toEntity(postDto);

        postEntity.setUserId((userId));

        List<Post> postEntities = postService.update(postEntity);

        List<PostDto> postDtos = postEntities.stream().map(PostDto::new).collect(Collectors.toList());

        ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(postDtos).build();

        return  ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal String userId, @RequestBody PostDto postDto) {

        try {
            Post postEntity = PostDto.toEntity(postDto);

            postEntity.setUserId(userId);

            List<Post> postEntities = postService.delete(postEntity);

            List<PostDto> postDtos = postEntities.stream().map(PostDto::new).collect(Collectors.toList());

            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().data(postDtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDto<PostDto> response = ResponseDto.<PostDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }




}
