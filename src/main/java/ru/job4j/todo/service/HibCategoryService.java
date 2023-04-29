package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;

@Service
public class HibCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    public HibCategoryService(CategoryRepository hibCategoryRepository) {
        this.categoryRepository = hibCategoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findAllByIds(List<Integer> ids) {
        return categoryRepository.findAllByIds(ids);
    }
}
