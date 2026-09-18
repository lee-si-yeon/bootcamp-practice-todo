package com.example.todo.domain.todo.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class TodoResponseDto {

    // 할 일 신규 등록 결과
    @Builder
    public record CreateTodoResultDTO(
            Long todoId,
            String name,
            LocalDateTime createdAt
    ) {}

    // 할 일 조회 결과
    @Builder
    public record TodoDTO(
            Long todoId,
            String name,
            boolean completed,
            LocalDateTime createdAt
    ) {}

    // 할 일 수정 결과
    @Builder
    public record UpdateTodoResultDTO(
            Long todoId,
            String name,
            LocalDateTime updatedAt
    ) {}

    // 할 일 완료 상태 변경 결과
    @Builder
    public record UpdateCompletionResultDTO(
            Long todoId,
            boolean completed,
            LocalDateTime updatedAt
    ) {}
}
