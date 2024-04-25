package connector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import play_store.App;

public class DbUtil {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/PlayStore";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    // Method to establish a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    // Method to register a new user
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO User (username, password) VALUES (?, ?)";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if a user exists
    public static boolean doesUserExist(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the user exists
            }
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate a user
    public static boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if the user exists with the provided credentials
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to register a new owner
    public static boolean registerOwner(String username, String password) {
        String sql = "INSERT INTO owner (username, password) VALUES (?, ?)";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if an owner exists
    public static boolean doesOwnerExist(String username) {
        String sql = "SELECT * FROM owner WHERE username = ?";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the owner exists
            }
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to authenticate an owner
    public static boolean authenticateOwner(String username, String password) {
        String sql = "SELECT * FROM owner WHERE username = ? AND password = ?";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if the owner exists with the provided credentials
        } catch (SQLException e) {
            // Log or handle the exception
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve apps from the database
    public static List<App> getApps() {
        List<App> apps = new ArrayList<>();
        String sql = "SELECT * FROM apps"; // Changed table name from 'app' to 'apps'
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("App_name"); // Changed column name to 'App_name'
                String description = rs.getString("description");
                Date releaseDate = rs.getDate("release_date");
                String version = rs.getString("version");
                double ratings = rs.getDouble("ratings");
                boolean visible = rs.getBoolean("visible");
                String category = rs.getString("category"); // Changed column name to 'category'
                App app = new App(name, description, releaseDate, version, ratings, category, visible);
                apps.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apps;
    }

    public static List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM apps"; // Changed table name and column name
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String categoryName = rs.getString("category"); // Changed column name
                categories.add(categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    // Method to add an app to the database
    public static boolean addApp(App app) {
        String sql = "INSERT INTO apps (App_name, description, release_date, version, ratings, category, visible) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, app.getName());
            stmt.setString(2, app.getDescription());
            stmt.setDate(3, app.getReleaseDate());
            stmt.setString(4, app.getVersion());
            stmt.setDouble(5, app.getRatings());
            stmt.setString(6, app.getCategory());
            stmt.setBoolean(7, app.isVisible());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an app in the database
    public static boolean updateApp(App app) {
        try {
            Connection connection = getConnection();
            String query = "UPDATE apps SET description=?, release_date=?, version=?, ratings=?, category=?, visible=? WHERE App_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, app.getName());
            preparedStatement.setString(2, app.getDescription());
            preparedStatement.setDate(3, app.getReleaseDate());
            preparedStatement.setString(4, app.getVersion());
            preparedStatement.setDouble(5, app.getRatings());
            preparedStatement.setString(6, app.getCategory());
            preparedStatement.setBoolean(7, app.isVisible());

            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteAppByName(String appName) {
        String sql = "DELETE FROM apps WHERE App_name = ?";
        try (Connection con = getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, appName);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
