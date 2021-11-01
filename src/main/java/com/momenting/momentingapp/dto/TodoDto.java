package com.momenting.momentingapp.dto;

import com.momenting.momentingapp.model.Todo;
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

    public TodoDto(final Todo entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdDate = entity.getCreatedDate();
        this.endDate = entity.getEndDate();
        this.done = entity.isDone();
    }

    public static Todo toEntity(final TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .endDate(dto.getEndDate())
                .done(dto.isDone())
                .build();
    }
}
