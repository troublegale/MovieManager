package letsgo.lab6.server.managers.databaseManagers.dml;

import letsgo.lab6.server.DatabaseConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DMLManager {

    protected final String databaseURL;
    protected final String databaseLogin;
    protected final String databasePassword;

    public DMLManager() {
        databaseURL = DatabaseConfiguration.getDatabaseURL();
        databaseLogin = DatabaseConfiguration.getDatabaseLogin();
        databasePassword = DatabaseConfiguration.getDatabasePassword();
    }

    protected final Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseURL, databaseLogin, databasePassword);
    }

}
