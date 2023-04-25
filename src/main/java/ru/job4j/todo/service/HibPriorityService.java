package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.Collection;

@Service
public class HibPriorityService implements PriorityService {
    private final PriorityRepository priorityRepository;

    public HibPriorityService(PriorityRepository hibPriorityRepository) {
        this.priorityRepository = hibPriorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
