package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gang", "Jia", (byte) 29);
        userService.saveUser("Chin", "Guiying", (byte) 45);
        userService.saveUser("Yawen", "Lin", (byte) 12);
        userService.saveUser("Qing", "Da", (byte) 87);

        for (User user : userService.getAllUsers()) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.getSessionFactory().close();
    }
}
