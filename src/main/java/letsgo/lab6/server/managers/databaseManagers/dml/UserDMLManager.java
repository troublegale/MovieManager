package letsgo.lab6.server.managers.databaseManagers.dml;

import letsgo.lab6.server.entities.User;
import letsgo.lab6.server.managers.databaseManagers.PasswordManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDMLManager extends DMLManager {

    private static User getUser(String username) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from users where name = ?;");
        statement.setString(1, username);
        ResultSet queryResult = statement.executeQuery();
        connection.close();
        if (queryResult.next()) {
            Long id = queryResult.getLong("id");
            String name = queryResult.getString("name");
            String passwordDigest = queryResult.getString("password_digest");
            String salt = queryResult.getString("salt");
            return new User(id, name, passwordDigest, salt);
        } else {
            return null;
        }
    }

    public static void insertUser(String username, String password) throws SQLException {
        Connection connection = getConnection();
        String salt = PasswordManager.getSalt();
        String passwordDigest = PasswordManager.getHash(password, salt);
        PreparedStatement statement = connection.prepareStatement(
                "insert into users(name, password_digest, salt) values (?, ?, ?);");
        statement.setString(1, username);
        statement.setString(2, passwordDigest);
        statement.setString(3, salt);
        statement.execute();
        connection.close();
    }

    public static boolean userIsRegistered(String username) throws SQLException {
        return getUser(username) != null;
    }

    public static boolean checkPassword(String username, String password) throws SQLException {
        User realUser = getUser(username);
        if (realUser == null) {
            throw new SQLException();
        }
        String realPassword = realUser.passwordDigest();
        String realSalt = realUser.salt();
        String probablePassword = PasswordManager.getHash(password, realSalt);
        return probablePassword.equals(realPassword);
    }

    protected static Long getIDByUsername(String username) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("select id from users where name = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getLong(1);
    }

    protected static String getUsernameByID(Long id) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("select name from users where id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }

}
