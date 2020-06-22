package jm.task.core.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    try {
      Statement stmt = connection.createStatement();
      stmt.execute("CREATE TABLE IF NOT EXISTS USER (\n" +
          "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
          "  `name` VARCHAR(255) NOT NULL,\n" +
          "  `lastname` VARCHAR(255) NOT NULL,\n" +
          "  `age` INT NOT NULL,\n" +
          "  PRIMARY KEY (`id`))\n");
      stmt.close();
    } catch (SQLException e) {
    }
  }

  public void dropUsersTable() {
    try {
      Statement stmt = connection.createStatement();
      stmt.execute("DROP TABLE IF EXISTS USER");
      stmt.close();
    } catch (SQLException e) {
    }
  }

  public boolean getUserByName(String name) {
    String query = "SELECT id FROM USER WHERE name = ?";
    try {
      PreparedStatement stmt = connection.prepareStatement(query);
      stmt.setString(1, name);
      long id = stmt.getResultSet().getLong(1);
      return false;
    } catch (SQLException e) {
    } catch (NullPointerException e) {
      return true;
    }
    return false;
  }

  public void saveUser(String name, String lastName, byte age) {
    try {
      if (getUserByName(name)) {
        String query = "INSERT INTO USER(name, lastname, age) VALUES (?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, lastName);
        stmt.setByte(3, age);
        stmt.execute();
        stmt.close();
      }
    } catch (SQLException e) {

    }
  }

  public void removeUserById(long id) {
    try {
      Statement stmt = connection.createStatement();
      stmt.execute("DELETE FROM USER WHERE id =" + id);
      stmt.close();
    } catch (SQLException e) {
    }
  }

  public List<User> getAllUsers() {
    String sql = "SELECT * FROM USER";
    List<User> userList = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
    try {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("TRUNCATE USER");
      stmt.close();
    } catch (SQLException e) {
    }
  }
}
