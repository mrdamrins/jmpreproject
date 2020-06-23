package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {

  private Connection connection;

  public UserDaoJDBCImpl(Connection mysqlConnection) {
    this.connection = Util.getMysqlConnection();
  }

  public void createUsersTable() {
    String query = "CREATE TABLE IF NOT EXISTS USER (\n" +
        "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
        "  `name` VARCHAR(255) NOT NULL,\n" +
        "  `lastname` VARCHAR(255) NOT NULL,\n" +
        "  `age` INT NOT NULL,\n" +
        "  PRIMARY KEY (`id`))\n";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.execute();
    } catch (SQLException e) {
    }
  }

  public void dropUsersTable() {
    String query = "DROP TABLE IF EXISTS USER";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.execute();
    } catch (SQLException e) {
    }
  }

  public boolean getUserByName(String name) {
    String query = "SELECT id FROM USER WHERE name = ?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setString(1, name);
      long id = preparedStatement.getResultSet().getLong(1);
      return false;
    } catch (SQLException e) {
    } catch (NullPointerException e) {
      return true;
    }
    return false;
  }

  public void saveUser(String name, String lastName, byte age) {
    String query = "INSERT INTO USER(name, lastname, age) VALUES (?, ?, ?)";
    try {
      if (getUserByName(name)) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setByte(3, age);
        preparedStatement.execute();
        preparedStatement.close();
        System.out.println("User с именем " + name + " добавлен в базу данных!");
      }
    } catch (SQLException e) {

    }
  }

  public void removeUserById(long id) {
    String query = "DELETE FROM USER WHERE id = ?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, 1);
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
    }
  }

  public List<User> getAllUsers() {
    String query = "SELECT * FROM USER";
    List<User> userList = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setLastName(rs.getString("lastname"));
        user.setAge(rs.getByte("age"));
        userList.add(user);
      }
    } catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return userList;
  }

  public void cleanUsersTable() {
    String query = "DELETE FROM USER";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }
}
