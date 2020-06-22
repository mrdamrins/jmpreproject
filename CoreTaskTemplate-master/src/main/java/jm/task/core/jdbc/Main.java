package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

  public static void main(String[] args) {
    UserService userService = UserServiceImpl.getInstance();
    userService.createUsersTable();
    userService.saveUser("Stepan", "Damrin", (byte) 30);
    userService.saveUser("Andrew", "Latyshev", (byte) 41);
    userService.saveUser("Dmitriy", "Grishanov", (byte) 15);
    userService.saveUser("Michail", "Seleznev", (byte) 65);
    System.out.println(userService.getAllUsers());
    userService.cleanUsersTable();
    userService.dropUsersTable();
  }
}
