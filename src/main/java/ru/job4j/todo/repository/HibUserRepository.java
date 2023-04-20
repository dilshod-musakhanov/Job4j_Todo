package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibUserRepository implements UserRepository {

    private static final Logger LOG = Logger.getLogger(HibUserRepository.class.getName());
    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return Optional.of(user);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in saving User: " + e, e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User AS u WHERE u.email = :fEmail AND u.password = :fPassword", User.class)
                    .setParameter("fEmail", email)
                    .setParameter("fPassword", password);
            transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in finding User by email and password: " + e, e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(
                    "FROM User AS u WHERE u.id = :fId", User.class)
                    .setParameter("fId", id);
            transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in finding User by id: " + id + " " + e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Collection<User> allUsers = session.createQuery(
                    "FROM User", User.class)
                    .list();
            transaction.commit();
            return allUsers;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in finding all User: " + " " + e);
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }
}
