package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibTaskRepository implements TaskRepository {

    private static final Logger LOG = Logger.getLogger(HibTaskRepository.class.getName());
    private final SessionFactory sf;

    @Override
    public Optional<Task> save(Task task) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(task);
            transaction.commit();
            return Optional.of(task);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in saving Task: " + task + " " + e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Collection<Task> allTasks = session.createQuery(
                    "FROM Task ORDER BY title ASC", Task.class).list();
            transaction.commit();
            return allTasks;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        LOG.error("Exception in finding all Tasks: " + e);
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "FROM Task AS t WHERE t.id = :fId", Task.class)
                    .setParameter("fId", id);
            transaction.commit();
            return query.uniqueResultOptional();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in finding Task by id: " + id + " " + e);
        } finally {
            session.close();
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        Transaction transaction = null;
        boolean flag = false;
        try {
            transaction = session.beginTransaction();
            int rowsAffected = session.createQuery(
                    "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            transaction.commit();
            flag = rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in deleting Task by id: " + id + " " + e);
        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        Transaction transaction = null;
        boolean flag = false;
        try {
            transaction = session.beginTransaction();
            session.update(task);
            transaction.commit();
            flag = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in updating Task: " + e);
        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public Collection<Task> findByStatus(boolean flag) {
        Session session = sf.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Collection<Task> tasks = session.createQuery(
                    "FROM Task AS t WHERE t.done = :fDone", Task.class)
                    .setParameter("fDone", flag)
                    .list();
            transaction.commit();
            return tasks;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error("Exception in finding by Task status: " + flag + " " + e);
        } finally {
            session.close();
        }
        return Collections.emptyList();
    }
}
