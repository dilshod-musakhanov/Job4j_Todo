package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> save(Task task);
    Collection<Task> findAll();
    Optional<Task> findById(int id);
    void setTaskDone(int id);
    void delete(int id);
    void update(Task task);
    Collection<Task> findByStatus(boolean flag);
}
