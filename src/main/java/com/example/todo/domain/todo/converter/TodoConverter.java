package com.example.todo.domain.todo.converter;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;

public class TodoConverter {

    public static Todo toTodo(TodoRequestDto.CreateTodoDTO request) {
        return Todo.builder()
                .name(request.name().strip())
                .build();
    }

    public static TodoResponseDto.CreateTodoResultDTO toCreateTodoResultDTO(Todo todo) {
        return TodoResponseDto.CreateTodoResultDTO.builder()
                .todoId(todo.getId())
                .name(todo.getName())
                .build();
    }

    public static TodoResponseDto.TodoDTO toTodoDTO(Todo todo) {
        return TodoResponseDto.TodoDTO.builder()
                .todoId(todo.getId())
                .name(todo.getName())
                .completed(todo.isCompleted())
                .build();
    }
}
