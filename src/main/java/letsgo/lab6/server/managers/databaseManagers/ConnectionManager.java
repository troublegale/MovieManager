package letsgo.lab6.server.managers.databaseManagers;

import letsgo.lab6.server.database.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    public static Connection getConnection(DatabaseConfiguration configuration) throws SQLException {
        String url = configuration.databaseURL();
        String login = configuration.databaseLogin();
        String password = configuration.databasePassword();
        return DriverManager.getConnection(url, login, password);
    }

}
