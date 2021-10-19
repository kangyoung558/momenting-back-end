package com.momenting.momentingapp.dto;

import com.momenting.momentingapp.model.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private String id;
    private String content;
    private String writer;
    private boolean secret;

    public PostDto(final PostEntity entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.secret = entity.isSecret();
    }

    public static PostEntity toEntity(final PostDto dto) {
        return  PostEntity.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .secret(dto.isSecret())
                .build();
    }
}
