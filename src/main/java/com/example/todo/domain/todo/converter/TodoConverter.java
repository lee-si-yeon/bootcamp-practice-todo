package com.example.todo.domain.todo.converter;

import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;

public class TodoConverter {

    // DTO -> 할 일 엔티티
    public static Todo toTodo(TodoRequestDto.CreateTodoDTO request) {
        return Todo.builder()
                .name(request.name().strip())
                .build();
    }

    // entity -> 할 일 신규 등록 DTO
    public static TodoResponseDto.CreateTodoResultDTO toCreateTodoResultDTO(Todo todo) {
        return TodoResponseDto.CreateTodoResultDTO.builder()
                .todoId(todo.getId())
                .name(todo.getName())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    // entity page -> 할 일 목록 조회 DTO
    public static TodoResponseDto.TodoPageDTO toTodoPageDTO(Page<Todo> todos) {
        Page<TodoResponseDto.TodoDTO> todoDTOs = todos.map(TodoConverter::toTodoDTO);

        return TodoResponseDto.TodoPageDTO.builder()
                .todos(todoDTOs.getContent())
                .page(todoDTOs.getNumber())
                .size(todoDTOs.getSize())
                .totalElements(todoDTOs.getTotalElements())
                .totalPages(todoDTOs.getTotalPages())
                .build();
    }

    // entity -> 할 일 조회 DTO
    public static TodoResponseDto.TodoDTO toTodoDTO(Todo todo) {
        return TodoResponseDto.TodoDTO.builder()
                .todoId(todo.getId())
                .name(todo.getName())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .build();
    }

    // entity -> 할 일 수정 DTO
    public static TodoResponseDto.UpdateTodoResultDTO toUpdateTodoResultDTO(Todo todo) {
        return TodoResponseDto.UpdateTodoResultDTO.builder()
                .todoId(todo.getId())
                .name(todo.getName())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }

    // entity -> 할 일 완료 상태 변경 DTO
    public static TodoResponseDto.UpdateCompletionResultDTO toUpdateCompletionResultDTO(Todo todo) {
        return TodoResponseDto.UpdateCompletionResultDTO.builder()
                .todoId(todo.getId())
                .completed(todo.isCompleted())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }
}
