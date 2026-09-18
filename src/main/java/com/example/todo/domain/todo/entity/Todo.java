package com.example.todo.domain.todo.entity;

import com.example.todo.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "todo")
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;  // 제목

    @Builder.Default
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = false;  // 완료 여부
}
