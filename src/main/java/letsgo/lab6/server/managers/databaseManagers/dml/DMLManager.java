package letsgo.lab6.server.managers.databaseManagers.dml;

import letsgo.lab6.server.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DMLManager {

    protected static final String databaseURL = DatabaseConfiguration.getDatabaseURL();
    protected static final String databaseLogin = DatabaseConfiguration.getDatabaseLogin();
    protected static final String databasePassword = DatabaseConfiguration.getDatabasePassword();


    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseURL, databaseLogin, databasePassword);
    }

}
