package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDto;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class HibTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public HibTaskService(TaskRepository hibTaskRepository, UserRepository userRepository) {
        this.taskRepository = hibTaskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Task> save(Task task) {
        task.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
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
    public boolean delete(int id) {
        return taskRepository.delete(id);
    }

    @Override
    public boolean update(Task task) {
        return taskRepository.update(task);
    }

    @Override
    public Collection<Task> findByStatusDone() {
        return taskRepository.findByStatus(true);
    }

    @Override
    public Collection<Task> findByStatusPending() {
        return taskRepository.findByStatus(false);
    }

    @Override
    public Collection<TaskDto> findAllTaskDto() {
        var tasks = taskRepository.findAll();
        var users = userRepository.findAll();
        var taskDtos = new ArrayList<TaskDto>();
        for (var task : tasks) {
            for (var user : users) {
                if (task.getUser().getId() == user.getId()) {
                    var taskDto = createDto(task, user);
                    taskDtos.add(taskDto);
                }
            }
        }
        return taskDtos;
    }

    @Override
    public Optional<TaskDto> convertToTaskDtoById(int id) {
        var task = taskRepository.findById(id);
        var user = userRepository.findById(task.get().getUser().getId());
        return Optional.of(createDto(task.get(), user.get()));
    }

    @Override
    public Collection<TaskDto> covertAllToTaskDto(Collection<Task> tasks) {
        var users = userRepository.findAll();
        var taskDtos = new ArrayList<TaskDto>();
        for (var task : tasks) {
            for (var user : users) {
                if (task.getUser().getId() == user.getId()) {
                    var taskDto = createDto(task, user);
                    taskDtos.add(taskDto);
                }
            }
        }
        return taskDtos;
    }

    private TaskDto createDto(Task task, User user) {
        var taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreated(task.getCreated());
        taskDto.setUserName(user.getName());
        return taskDto;
    }
}
