package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Collections;

@Repository
@AllArgsConstructor
@Log4j
public class HibPriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        try {
            Collection<Priority> allPriorities = crudRepository.query(
                    "FROM Priority",
                    Priority.class
            );
            return allPriorities;
        } catch (Exception e) {
            log.error("Exception in finding all Priority: " + e);
        }
        return Collections.emptyList();
    }
}
