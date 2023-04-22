package ru.job4j.todo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDto {
    @Include
    private int id;
    @Include
    private String title;

    private String description;
    private LocalDateTime created;
    private String userName;
}
