package jm.task.core.jdbc.service;

import java.util.List;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserServiceImpl implements UserService {

  private static UserDaoJDBCImpl getUserDAO() {
    return new UserDaoJDBCImpl(Util.getMysqlConnection());
  }

  public void createUsersTable() {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    userDaoJDBC.createUsersTable();
  }

  public void dropUsersTable() {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    userDaoJDBC.dropUsersTable();
  }

  public void saveUser(String name, String lastName, byte age) {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    userDaoJDBC.saveUser(name, lastName, age);
  }

  public void removeUserById(long id) {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    userDaoJDBC.removeUserById(id);
  }

  public List<User> getAllUsers() {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    return userDaoJDBC.getAllUsers();
  }

  public void cleanUsersTable() {
    UserDaoJDBCImpl userDaoJDBC = getUserDAO();
    userDaoJDBC.cleanUsersTable();
  }
}
