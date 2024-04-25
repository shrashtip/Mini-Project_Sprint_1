package play_store;

import java.util.ArrayList;
import java.util.List;

public class Owner {
    private String username;
    private String password;
    private List<App> apps;

    public Owner(String username, String password) {
        this.username = username;
        this.password = password;
        this.apps = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public boolean login(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username) && enteredPassword.equals(password);
    }

    public void logout() {
        System.out.println("Logged out successfully.");
    }

    public void addApp(App app) {
        apps.add(app);
    }

    public App searchAppByName(String name) {
        for (App app : apps) {
            if (app.getName().equalsIgnoreCase(name)) {
                return app;
            }
        }
        return null; // App not found
    }

    public List<App> getApps() {
        return apps;
    }
}




