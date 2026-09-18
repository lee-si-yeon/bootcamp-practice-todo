package com.example.todo.domain.todo.service;

import com.example.todo.domain.todo.converter.TodoConverter;
import com.example.todo.domain.todo.dto.TodoResponseDto;
import com.example.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
