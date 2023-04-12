package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class HibTaskService implements TaskService {
    private final TaskRepository taskRepository;

    public HibTaskService(TaskRepository hibTaskRepository) {
        this.taskRepository = hibTaskRepository;
    }

    @Override
    public Optional<Task> save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public void setTaskDone(int id) {
        taskRepository.setTaskDone(id);
    }

    @Override
    public void delete(int id) {
        taskRepository.delete(id);
    }

    @Override
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Override
    public Collection<Task> findByStatusDone() {
        return taskRepository.findByStatus(true);
    }

    @Override
    public Collection<Task> findByStatusPending() {
        return taskRepository.findByStatus(false);
    }
}
