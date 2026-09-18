package com.example.todo.domain.todo.service;

import com.example.todo.domain.todo.converter.TodoConverter;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoQueryService {

    private final TodoRepository todoRepository;

    // 할 일 목록 조회
    public List<TodoResponseDto.TodoDTO> getTodos() {
        return todoRepository.findAll().stream()
                .map(TodoConverter::toTodoDTO)
                .toList();
    }

    // 할 일 상세 조회
    public TodoResponseDto.TodoDTO getTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .map(TodoConverter::toTodoDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "할 일을 찾을 수 없습니다."));
    }
}
