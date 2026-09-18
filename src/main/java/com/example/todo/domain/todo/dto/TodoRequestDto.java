package com.example.todo.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TodoRequestDto {

    // 할 일 신규 등록
    public record CreateTodoDTO(
            @NotBlank(message = "과제명은 필수 입력 값입니다.")
            @Size(max = 100, message = "과제명은 100자 이하여야 합니다.")
            @Schema(description = "제목", example = "과제하기")
            String name
    ) {}

    // 할 일 수정
    public record UpdateTodoDTO(
            @NotBlank(message = "과제명은 필수 입력 값입니다.")
            @Size(max = 100, message = "과제명은 100자 이하여야 합니다.")
            @Schema(description = "수정할 제목", example = "과제 제출하기")
            String name
    ) {}

    // 할 일 완료 상태 변경
    public record UpdateCompletionDTO(
            @NotNull(message = "완료 여부는 필수 입력 값입니다.")
            @Schema(description = "완료 여부", example = "true")
            Boolean completed
    ) {}
}
