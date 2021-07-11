package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.createSQLQuery("CREATE TABLE IF NOT EXISTS users ("
                    + "id BIGINT NOT NULL AUTO_INCREMENT,"
                    + "name VARCHAR(45) NOT NULL,"
                    + "lastName VARCHAR(45) NOT NULL,"
                    + "age TINYINT NOT NULL,"
                    + "PRIMARY KEY (id))").executeUpdate();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.save(new User(name, lastName, age));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }


    @Override
    public void removeUserById(long id) {
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            Query query = sess.createQuery("delete User where id = :id");
            query.setParameter("id", id).executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> userList;
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            userList = sess.createQuery("from User").list();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session sess = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = sess.beginTransaction();
            sess.createSQLQuery("truncate table users").executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            sess.close();
        }
    }
}
