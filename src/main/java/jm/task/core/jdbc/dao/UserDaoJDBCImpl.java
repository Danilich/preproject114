package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection conn = Util.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS users ("
                             + "Id BIGINT NOT NULL AUTO_INCREMENT,"
                             + "Name VARCHAR(45) NOT NULL,"
                             + "LastName VARCHAR(45) NOT NULL,"
                             + "Age TINYINT NOT NULL,"
                             + "PRIMARY KEY (Id))")) {

            stmt.executeUpdate();

        } catch (SQLException error) {
            Util.showError(error);
        }
    }

    public void dropUsersTable() {

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS users");

        } catch (SQLException error) {
            Util.showError(error);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users(Name,LastName,Age) " + "VALUES(?,?,?)")) {

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

        try (Connection conn = Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE Id = ?")) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Util.showError(ex);
        }
    }


    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

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

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("TRUNCATE users");

        } catch (SQLException error) {
            Util.showError(error);
        }
    }
}
