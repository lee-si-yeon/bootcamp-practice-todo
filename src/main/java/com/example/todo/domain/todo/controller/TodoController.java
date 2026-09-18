package com.example.todo.domain.todo.controller;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.service.TodoCommandService;
import com.example.todo.domain.todo.service.TodoQueryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoCommandService todoCommandService;
    private final TodoQueryService todoQueryService;

    @Operation(summary = "할 일 등록")
    @PostMapping
    public ResponseEntity<TodoResponseDto.CreateTodoResultDTO> createTodo(
            @Valid @RequestBody TodoRequestDto.CreateTodoDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoCommandService.createTodo(request));
    }

    @Operation(summary = "할 일 목록 조회")
    @GetMapping
    public ResponseEntity<TodoResponseDto.TodoPageDTO> getTodos(
            @RequestParam(required = false) Boolean completed,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(todoQueryService.getTodos(completed, pageable));
    }

    @Operation(summary = "할 일 상세 조회")
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto.TodoDTO> getTodo(
            @PathVariable Long todoId
    ) {
        return ResponseEntity.ok(todoQueryService.getTodo(todoId));
    }

    @Operation(summary = "할 일 수정")
    @PatchMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto.UpdateTodoResultDTO> updateTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRequestDto.UpdateTodoDTO request
    ) {
        return ResponseEntity.ok(todoCommandService.updateTodo(todoId, request));
    }

    @Operation(summary = "할 일 삭제")
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId
    ) {
        todoCommandService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "할 일 완료 상태 변경")
    @PatchMapping("/{todoId}/completed")
    public ResponseEntity<TodoResponseDto.UpdateCompletionResultDTO> updateCompletion(
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRequestDto.UpdateCompletionDTO request
    ) {
        return ResponseEntity.ok(todoCommandService.updateCompletion(todoId, request));
    }
}
