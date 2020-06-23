package jm.task.core.jdbc.dao;

import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

  private Session session;
  Transaction transaction = null;

  public UserDaoHibernateImpl() {

  }

  public UserDaoHibernateImpl(Session session) {
    this.session = session;
  }


  @Override
  public void createUsersTable() {
    transaction = session.beginTransaction();
    session.createSQLQuery("CREATE TABLE IF NOT EXISTS USER (\n" +
        "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
        "  `name` VARCHAR(255) NOT NULL,\n" +
        "  `lastname` VARCHAR(255) NOT NULL,\n" +
        "  `age` INT NOT NULL,\n" +
        "  PRIMARY KEY (`id`))\n");
    transaction.commit();
    session.close();
  }

  @Override
  public void dropUsersTable() {
    transaction = session.beginTransaction();
    session.createSQLQuery("DROP TABLE IF EXISTS USER");
    transaction.commit();
    session.close();
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    session = Util.getSessionFactory().openSession();
    User user = new User(name, lastName, age);
    session.save(user);
    session.close();
    System.out.println("User с именем " + name + " добавлен в базу данных!");
  }

  @Override
  public void removeUserById(long id) {
    session = Util.getSessionFactory().openSession();
    transaction = session.getTransaction();
    transaction.begin();
    User user = (User) session.get(User.class, 1L);
    if (user != null) {
      session.delete(user);
    }
  }

  @Override
  public List<User> getAllUsers() {
    return Util.getSessionFactory().openSession().createQuery("FROM User").list();
  }

  @Override
  public void cleanUsersTable() {
    try {
      transaction = session.beginTransaction();
      session.createQuery("DELETE user").executeUpdate();
      transaction.commit();
    } catch (HibernateException e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      session.close();
    }
  }
}