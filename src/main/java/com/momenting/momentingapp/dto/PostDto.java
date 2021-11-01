package com.momenting.momentingapp.dto;

import com.momenting.momentingapp.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private String id;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private boolean secret;

    public PostDto(final Post entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.createdDate = entity.getCreatedDate();
        this.secret = entity.isSecret();
    }

    public static Post toEntity(final PostDto dto) {
        return  Post.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .secret(dto.isSecret())
                .build();
    }
}
