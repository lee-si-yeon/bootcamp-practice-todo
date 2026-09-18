package com.example.todo.domain.todo.service;

import com.example.todo.domain.todo.converter.TodoConverter;
import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;
import com.example.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoCommandService {

    private final TodoRepository todoRepository;

    // 할 일 신규 등록
    public TodoResponseDto.CreateTodoResultDTO createTodo(TodoRequestDto.CreateTodoDTO request) {
        Todo todo = todoRepository.save(TodoConverter.toTodo(request));
        return TodoConverter.toCreateTodoResultDTO(todo);
    }

    // 할 일 수정
    public TodoResponseDto.UpdateTodoResultDTO updateTodo(Long todoId, TodoRequestDto.UpdateTodoDTO request) {
        Todo todo = getTodo(todoId);
        todo.updateName(request.name());
        todoRepository.flush();
        return TodoConverter.toUpdateTodoResultDTO(todo);
    }

    // 할 일 삭제
    public void deleteTodo(Long todoId) {
        todoRepository.delete(getTodo(todoId));
    }

    private Todo getTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할 일을 찾을 수 없습니다."));
    }
}
