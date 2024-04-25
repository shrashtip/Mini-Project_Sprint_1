package play_store;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connector.DbUtil;

public class Users {
    private String username;
    private String password;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticateUser(String enteredPassword) {
        try (Connection con = DbUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?")) {
            stmt.setString(1, this.username);
            stmt.setString(2, enteredPassword); // Using entered password instead of stored password
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Returns true if the user exists with the provided credentials
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // User not found or password doesn't match
    }

    public boolean registerUser() {
        try (Connection con = DbUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement("INSERT INTO User (username, password) VALUES (?, ?)")) {
            stmt.setString(1, this.username);
            stmt.setString(2, this.password);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public String getPassword() {
		 return password;
	}
}
