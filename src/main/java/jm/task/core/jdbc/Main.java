package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
//        userService.saveUser("Ivan", "Sidorov", (byte) 20);
//        userService.saveUser("Nikita", "Volkov", (byte) 15);
//        userService.saveUser("Tolik", "Zubov", (byte) 54);
//        userService.saveUser("German", "Smirnov", (byte) 77);

        userService.cleanUsersTable();
        userService.dropUsersTable();




        
    }
}
