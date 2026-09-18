package com.example.todo.domain.todo.service;

import com.example.todo.domain.todo.converter.TodoConverter;
import com.example.todo.domain.todo.dto.TodoRequestDto;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.entity.Todo;
import com.example.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
