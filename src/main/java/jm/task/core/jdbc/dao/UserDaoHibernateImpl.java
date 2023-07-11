package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
                    name varchar(50) NOT NULL ,
                    lastName varchar(50) NOT NULL ,
                    age TINYINT NOT NULL )
                """;
        session.createSQLQuery(sql).addEntity(User.class);// может быть executeUopdate надо запустить
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {

        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;

        User user = new User(name, lastName, age);
        try {
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("delete User where id = id").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;

        try(  Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User ").getResultList();
            session.getTransaction().commit();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
