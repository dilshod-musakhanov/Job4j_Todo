package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Log4j
public class HibUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            log.error("Exception in saving User: " + e, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            return crudRepository.optional(
                    "FROM User AS u WHERE u.email = :fEmail AND u.password = :fPassword",
                    User.class,
                    Map.of(
                            "fEmail", email,
                            "fPassword", password
                    )
            );
        } catch (Exception e) {
            log.error("Exception in finding User by email and password: " + e, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        try {
            return crudRepository.optional(
                    "FROM User AS u WHERE u.id = :fId",
                    User.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            log.error("Exception in finding User by id: " + id + " " + e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        try {
            Collection<User> allUsers = crudRepository.query(
                    "FROM User",
                    User.class
            );
            return allUsers;
        } catch (Exception e) {
            log.error("Exception in finding all User: " + " " + e);
        }
        return Collections.emptyList();
    }
}
