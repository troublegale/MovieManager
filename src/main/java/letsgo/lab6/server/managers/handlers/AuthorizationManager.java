package letsgo.lab6.server.managers.handlers;

import letsgo.lab6.server.managers.databaseManagers.dml.UserDMLManager;

import java.sql.SQLException;

public class AuthorizationManager {

    private static final UserDMLManager manager = new UserDMLManager();

    public static int logIn(String username, String password) {
        try {
            if (!manager.userIsRegistered(username)) {
                return 1;
            } else {
                if (!manager.checkPassword(username, password)) {
                    return 2;
                }
            }
            return 0;
        } catch (SQLException e) {
            return 3;
        }
    }

    public static int register(String username, String password) {
        try {
            if (manager.userIsRegistered(username)) {
                return 1;
            }
            manager.insertUser(username, password);
            return 0;
        } catch (SQLException e) {
            return 2;
        }
    }

}
