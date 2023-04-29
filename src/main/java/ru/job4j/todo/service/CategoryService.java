package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

@Service
public interface CategoryService {
    Collection<Category> findAll();
    Collection<Category> findAllByIds(List<Integer> ids);
}
