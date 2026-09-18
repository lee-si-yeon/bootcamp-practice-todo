package com.example.todo.domain.todo.controller;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.service.TodoCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoCommandService todoCommandService;

    @Operation(summary = "할 일 등록")
    @PostMapping
    public ResponseEntity<TodoResponseDto.CreateTodoResultDTO> createTodo(
            @Valid @RequestBody TodoRequestDto.CreateTodoDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(todoCommandService.createTodo(request));
    }
}
