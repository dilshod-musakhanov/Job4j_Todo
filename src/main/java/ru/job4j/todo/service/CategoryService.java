package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> findAll();
    List<Category> findAllByIds(List<Integer> ids);
}
