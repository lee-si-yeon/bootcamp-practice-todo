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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<TodoResponseDto.TodoDTO>> getTodos() {
        return ResponseEntity.ok(todoQueryService.getTodos());
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
}
