package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User where id = id").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<User> userList = session.createQuery("from User ").getResultList();
        session.getTransaction().commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
