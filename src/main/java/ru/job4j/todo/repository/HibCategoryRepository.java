package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.*;


@Repository
@AllArgsConstructor
public class HibCategoryRepository implements CategoryRepository {
    private static final Logger LOG = Logger.getLogger(HibTaskRepository.class.getName());
    private CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        try {
            Collection<Category> allCategories = crudRepository.query(
                    "FROM Category",
                    Category.class
            );
            return allCategories;
        } catch (Exception e) {
            LOG.error("Exception in finding all Category: " + e);
        }
        return Collections.emptyList();
    }

    @Override
    public Collection<Category> findAllByIds(List<Integer> ids) {
        Collection<Category> categories = new ArrayList<>();
        try {
            for (var id : ids) {
                var category = crudRepository.optional(
                        "FROM Category WHERE id = :fId",
                        Category.class,
                        Map.of("fId", id)
                );
                category.ifPresent(categories::add);
            }
            return categories;
        } catch (Exception e) {
            LOG.error("Exception in finding all Category by Ids: " + e);
        }
        return Collections.emptyList();
    }
}
