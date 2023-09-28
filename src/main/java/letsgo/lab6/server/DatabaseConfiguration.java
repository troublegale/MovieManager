package letsgo.lab6.server;

public class DatabaseConfiguration {

    public static String databaseURL;
    public static String databaseLogin;
    public static String databasePassword;

    public static String getDatabaseURL() {
        return databaseURL;
    }

    public static void setDatabaseURL(String databaseURL) {
        DatabaseConfiguration.databaseURL = databaseURL;
    }

    public static String getDatabaseLogin() {
        return databaseLogin;
    }

    public static void setDatabaseLogin(String databaseLogin) {
        DatabaseConfiguration.databaseLogin = databaseLogin;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }

    public static void setDatabasePassword(String databasePassword) {
        DatabaseConfiguration.databasePassword = databasePassword;
    }
}
