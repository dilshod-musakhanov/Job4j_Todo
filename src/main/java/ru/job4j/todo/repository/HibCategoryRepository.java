package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.*;


@Repository
@AllArgsConstructor
@Log4j
public class HibCategoryRepository implements CategoryRepository {
    private CrudRepository crudRepository;

    @Override
    public List<Category> findAll() {
        try {
            List<Category> allCategories = crudRepository.query(
                    "FROM Category",
                    Category.class
            );
            return allCategories;
        } catch (Exception e) {
            log.error("Exception in finding all Category: " + e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Category> findAllByIds(List<Integer> ids) {
        try {
            List<Category> categories = crudRepository.query(
                    "FROM Category WHERE id IN :fIds",
                    Category.class,
                    Map.of("fIds", ids)
            );
            return categories;
        } catch (Exception e) {
            log.error("Exception in finding all Category by Ids: " + e);
        }
        return Collections.emptyList();
    }
}
