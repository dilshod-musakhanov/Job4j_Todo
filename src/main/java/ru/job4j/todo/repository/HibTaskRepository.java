package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class HibTaskRepository implements TaskRepository {

    private static final Logger LOG = Logger.getLogger(HibTaskRepository.class.getName());
    private final CrudRepository crudRepository;

    @Override
    public Optional<Task> save(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
            return Optional.of(task);
        } catch (Exception e) {
            LOG.error("Exception in saving Task: " + task + " " + e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        try {
            var allTasks = crudRepository.query(
                    "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories ORDER BY title ASC",
                    Task.class
            );
            return allTasks.stream().distinct().collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Exception in finding all Tasks: " + e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Task> findById(int id) {
        try {
            return crudRepository.optional(
                    "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.id = :fId",
                    Task.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            LOG.error("Exception in finding Task by id: " + id + " " + e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        try {
            crudRepository.run(
                    "DELETE Task WHERE id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            LOG.error("Exception in deleting Task by id: " + id + " " + e);
        }
        return false;
    }

    @Override
    public boolean update(Task task) {
        try {
            crudRepository.run(session -> session.update(task));
            return true;
        } catch (Exception e) {
            LOG.error("Exception in updating Task: " + e);
        }
        return false;
    }

    @Override
    public Collection<Task> findByStatus(boolean flag) {
        try {
            Collection<Task> allTasks = crudRepository.query(
                    "FROM Task AS t JOIN FETCH t.priority WHERE t.done = :fDone",
                    Task.class,
                    Map.of("fDone", flag)
            );
            return allTasks;
        } catch (Exception e) {
            LOG.error("Exception in finding by Task status: " + flag + " " + e);
        }
        return Collections.emptyList();
    }
}
