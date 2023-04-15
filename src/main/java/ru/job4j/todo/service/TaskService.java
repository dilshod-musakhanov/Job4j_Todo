package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Optional<Task> save(Task task);
    Collection<Task> findAll();
    Optional<Task> findById(int id);
    boolean delete(int id);
    boolean update(Task task);
    Collection<Task> findByStatusDone();
    Collection<Task> findByStatusPending();
}
