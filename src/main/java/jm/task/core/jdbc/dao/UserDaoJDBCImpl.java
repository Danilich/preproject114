package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private final List<User> userList = new ArrayList<>();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users ("
                + "Id BIGINT NOT NULL AUTO_INCREMENT,"
                + "Name VARCHAR(45) NOT NULL,"
                + "LastName VARCHAR(45) NOT NULL,"
                + "Age TINYINT NOT NULL,"
                + "PRIMARY KEY (Id))";

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(CREATE_TABLE_SQL)) {

            stmt.executeUpdate();

        } catch (SQLException error) {
            Util.showError(error);
        }
    }

    public void dropUsersTable() {
        final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(DROP_TABLE_SQL);

        } catch (SQLException error) {
            Util.showError(error);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String INSERT_SQL = "INSERT INTO users(Name,LastName,Age) " + "VALUES(?,?,?)";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {

            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.format("User с именем – %s %s добавлен в базу данных\n", name, lastName);
            }
        } catch (SQLException ex) {
            Util.showError(ex);
        }
    }

    public void removeUserById(long id) {
        String SQL = "DELETE FROM users WHERE Id = ?";

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Util.showError(ex);
        }
    }


    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                User user = new User(rs.getString("Name"), rs.getString("LastName"), rs.getByte("Age"));
                user.setId(rs.getLong("Id"));
                userList.add(user);
            }
        } catch (SQLException ex) {
            Util.showError(ex);
        }
        return userList;
    }

    public void cleanUsersTable() {
        final String DROP_TABLE_SQL = "TRUNCATE users";

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(DROP_TABLE_SQL);

        } catch (SQLException error) {
            Util.showError(error);
        }
    }
}
