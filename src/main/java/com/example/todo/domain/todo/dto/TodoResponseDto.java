package com.example.todo.domain.todo.dto;

import lombok.Builder;

public class TodoResponseDto {

    // 할 일 신규 등록 결과
    @Builder
    public record CreateTodoResultDTO(
            Long todoId,
            String name
    ) {}
}
