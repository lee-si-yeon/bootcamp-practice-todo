package com.example.todo.domain.todo.dto;

import lombok.Builder;

public class TodoResponseDto {

    // 할 일 신규 등록 결과
    @Builder
    public record CreateTodoResultDTO(
            Long todoId,
            String name
    ) {}

    // 할 일 조회 결과
    @Builder
    public record TodoDTO(
            Long todoId,
            String name,
            boolean completed
    ) {}
}
