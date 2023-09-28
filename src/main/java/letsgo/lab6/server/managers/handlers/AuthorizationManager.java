package letsgo.lab6.server.managers.handlers;

import letsgo.lab6.server.managers.databaseManagers.dml.UserDMLManager;

import java.sql.SQLException;

public class AuthorizationManager {

    public static int logIn(String username, String password) {
        try {
            if (!UserDMLManager.userIsRegistered(username)) {
                return 1;
            } else {
                if (!UserDMLManager.checkPassword(username, password)) {
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
            if (UserDMLManager.userIsRegistered(username)) {
                return 1;
            }
            UserDMLManager.insertUser(username, password);
            return 0;
        } catch (SQLException e) {
            return 2;
        }
    }

}
