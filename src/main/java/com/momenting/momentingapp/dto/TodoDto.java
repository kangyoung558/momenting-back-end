package com.momenting.momentingapp.dto;

import com.momenting.momentingapp.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDto {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDate endDate;
    private boolean done;

    public TodoDto(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.endDate = entity.getEndDate();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDto dto) {
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .endDate(dto.getEndDate())
                .done(dto.isDone())
                .build();
    }
}
