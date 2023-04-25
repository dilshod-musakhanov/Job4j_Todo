package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;
import java.util.Collections;

@Repository
@AllArgsConstructor
public class HibPriorityRepository implements PriorityRepository {
    private static final Logger LOG = Logger.getLogger(HibTaskRepository.class.getName());
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
            LOG.error("Exception in finding all Priority: " + e);
        }
        return Collections.emptyList();
    }
}
