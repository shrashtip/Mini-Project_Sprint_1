package play_store;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import connector.DbUtil;

import java.util.Set;
import java.util.HashSet;

public class PlayStore {
    private List<App> apps;
    private List<Users> users;
    private List<Owner> owners;
    private Users currentUser; // To store the currently logged-in user
    private Owner currentOwner; // To store the currently logged-in owner
    private Scanner scanner;

    private List<String> categories;
    
    public PlayStore() {
        this.apps = new ArrayList<>();
        this.users = new ArrayList<>();
        this.owners = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        loadAppsFromDatabase();
        loadCategoriesFromDatabase();
    }

    private void loadAppsFromDatabase() {
        // Load apps from the database and categorize them
        this.apps = DbUtil.getApps();
    }

  

    private void loadCategoriesFromDatabase() {
        // Load categories from the database
        this.categories = DbUtil.getCategories();
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

    public List<Users> getUsers() {
        return users;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void addUser(Users user) {
        users.add(user);
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users user) {
        this.currentUser = user;
    }

    public Owner getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Owner owner) {
        this.currentOwner = owner;
    }
    
    public List<App> getAllApps() {
        return apps;
    }


    public static void main(String[] args) {
        PlayStore playStore = new PlayStore();

        while (true) {
            System.out.println("Hi,Enter the role to perform (user/owner):");
            String role = playStore.scanner.nextLine();
            
            if (role.equalsIgnoreCase("user")) {
                System.out.println("Enter the task to perform (login/register):");
                String task = playStore.scanner.nextLine();

                if (task.equalsIgnoreCase("login")) {
                    playStore.login();
                } else if (task.equalsIgnoreCase("register")) {
                    playStore.register();
                } else {
                    System.out.println("Invalid task.");
                }
                System.out.println("Do you want to continue? (yes/no):");
                String continueChoice = playStore.scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                    System.out.println("Thank you for using the PlayStore app!");
                    
                    break;
                }
            } else if (role.equalsIgnoreCase("owner")) {
                System.out.println("Hi,Enter the task to perform (login/register):");
                String task = playStore.scanner.nextLine();

                if (task.equalsIgnoreCase("login")) {
                    playStore.ownerLogin();
                } else if (task.equalsIgnoreCase("register")) {
                    playStore.ownerRegister();
                } else {
                    System.out.println("Invalid task.");
                }
            } else {
                System.out.println("Invalid role.");
            }

          
        }

        // Close scanner
        playStore.scanner.close();
    }

 public void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        boolean authenticated = DbUtil.authenticateUser(username, password);
        if (authenticated) {
            setCurrentUser(new Users(username, password));
            System.out.println("User logged in successfully.");

            System.out.println("Do you want to search by app name or category? (App/Category):");
            String searchChoice = scanner.nextLine();
            if (searchChoice.equalsIgnoreCase("app")) {
                // Display all apps available in the database
                List<App> allApps = getAllApps();
                System.out.println("Apps available in the play store:");
                for (App allApp : allApps) {
                    System.out.println("Name: " + allApp.getName());
                    // Print other details as needed
                }
            } else if (searchChoice.equalsIgnoreCase("category")) {
                // Display available categories
                displayCategories();

                // Prompt for category input
                System.out.println("Enter the category you are interested in:");
                String category = scanner.nextLine();

                // Check if the entered category is valid
                boolean categoryExists = false;
                for (App app : apps) {
                    if (app.getCategory().equalsIgnoreCase(category)) {
                        categoryExists = true;
                        break;
                    }
                }
                if (!categoryExists) {
                    System.out.println("Invalid category name. Please choose a category from the available categories.");
                    return; // Go back to the previous step
                }

                // Display apps by the selected category
                displayAppsByCategory(category);

              
            } else {
                System.out.println("Invalid choice.");
                return; // Go back to the previous step
            }
            
            // Prompt for app input
            System.out.println("Enter the name of the app you want to use:");
            String appName = scanner.nextLine();
            displayAppDetails(appName);

            // Prompt for logout
            System.out.println("Do you want to logout? (yes/no):");
            String logoutChoice = scanner.nextLine();
            if (logoutChoice.equalsIgnoreCase("yes")) {
                setCurrentUser(null);
                System.out.println("Logged out successfully.");
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }



    public void register() {
        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();

        // Check if the username already exists in the database
        if (DbUtil.doesUserExist(newUsername)) {
            System.out.println("User already exists. Please choose a different username.");
            return;
        }

        String newPassword = getPassword(scanner);

        boolean registered = DbUtil.registerUser(newUsername, newPassword);
        if (registered) {
            // Create a new user and add it to the list of users
            Users newUser = new Users(newUsername, newPassword);
            addUser(newUser);
            setCurrentUser(newUser);
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. Please try again later.");
        }
    }

    public static String getPassword(Scanner scanner) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);

        while (true) {
            System.out.println("Enter password:");
            String password = scanner.nextLine();

            if (!pattern.matcher(password).matches()) {
                System.out.println("Password must contain at least 8 characters, one uppercase, one lowercase, one digit, and one special character.");
            } else {
                System.out.println("Confirm password:");
                String confirmPassword = scanner.nextLine();

                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                } else {
                    return password;
                }
            }
        }
    }

   

    public void displayCategories() {
        // Display the available categories
        if (categories != null && !categories.isEmpty()) {
            System.out.println("Available categories:");
            for (String category : categories) {
                System.out.println(category);
            }
        } else {
            System.out.println("No categories available.");
        }
    }
    
    


    
    //Using lambda expressions to displayAppsByCategory
    public void displayAppsByCategory(String category) {
        System.out.println("Apps in Category '" + category + "':");
        boolean found = apps.stream()
                            .filter(app -> app.getCategory().equalsIgnoreCase(category))
                            .peek(app -> System.out.println(app.getName()))
                            .count() > 0; // Use count() to check if any apps were found
        if (!found) {
            System.out.println("No apps found in this category.");
        }
    }



    public void displayAppDetails(String appName) {
        for (App app : apps) {
            if (app.getName().equalsIgnoreCase(appName)) {
                System.out.println("App Name: " + app.getName());
                System.out.println("Description: " + app.getDescription());
                System.out.println("Category: " + app.getCategory());
                System.out.println("Version: " + app.getVersion());
                System.out.println("Rating: " + app.getRatings());
                return;
            }
        }
        System.out.println("App not found.");
    }

 

    public void ownerRegister() {
        System.out.println("Enter new owner username:");
        String newUsername = scanner.nextLine();

        // Check if the username already exists in the database
        if (DbUtil.doesOwnerExist(newUsername)) {
            System.out.println("Owner already exists. Please choose a different username.");
            return ;
        }

        String newPassword = getPassword(scanner);

        boolean registered = DbUtil.registerOwner(newUsername, newPassword);
        if (registered) {
            // Create a new owner and add it to the list of owners
            Owner newOwner = new Owner(newUsername, newPassword);
            addOwner(newOwner);
            setCurrentOwner(newOwner);
            System.out.println("Owner registered successfully.");
        } else {
            System.out.println("Registration failed. Please try again later.");
        }
    }
    
    
    public void ownerLogin() {
    	
        System.out.println("Enter owner username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        boolean authenticated = DbUtil.authenticateOwner(username, password);
        if (authenticated) {
            setCurrentOwner(new Owner(username, password));
            System.out.println("Owner logged in successfully.");
            
            // Display available categories
            displayCategories();

            System.out.println("Enter the category you are interested in:");
            String category = scanner.nextLine();
//            displayAppsByCategory(category);
            
            boolean categoryExists = false;
            for (App app : apps) {
                if (app.getCategory().equalsIgnoreCase(category)) {
                    categoryExists = true;
                    break;
                }
            }
            if (!categoryExists) {
                System.out.println("Invalid category name. Please choose a category from the available categories.");
                return; // Go back to the previous step
            }
            displayAppsByCategory(category);
            
//            // Call ownerActions() method for owner
//            if (getCurrentOwner() != null) {
//                ownerActions();
//            }
            ownerActions();
//            System.out.println("Enter the name of the app you want to use:");
//            String appName = scanner.nextLine();
//            displayAppDetails(appName);

          
        } else {
            System.out.println("Invalid username or password.");
        }
    }

   
    public void ownerActions() {
        boolean continueActions = true;
        
    	// Prompt the owner to choose the app they want to make changes to
        System.out.println("Enter the name of the app you want to make changes to:");
        String appName = scanner.nextLine();
        App app = searchAppByName(appName);
        if (app == null) {
            System.out.println("App not found.");
            return;
         }
          while (continueActions) {
            System.out.println("Owner actions:");
            System.out.println("1. Add new app");
            System.out.println("2. Update existing app");
            System.out.println("3. Delete app");
            System.out.println("4. View app details");
            System.out.println("------------------------");
            System.out.println("Enter your choice:");
           
            String choiceStr = scanner.nextLine();

            // Check if the input is a number
            if (choiceStr.matches("\\d+")) {
                int choice = Integer.parseInt(choiceStr);
                switch (choice) {
                    case 1:
                        addNewApp();
                        break;
                    case 2:
                        updateApp();
                        break;
                    case 3:
                        deleteApp();
                        break;
                    case 4:
                        displayAppDetails(appName);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                // Check if the input matches an action string
                switch (choiceStr.toLowerCase()) {
                    case "add new app":
                        addNewApp();
                        break;
                    case "update existing app":
                        updateApp();
                        break;
                    case "delete app":
                        deleteApp();
                        break;
                    case "view app details":
                        displayAppDetails(appName);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        continue;
                }  
            }
          
          
         // Ask whether to see all apps
            System.out.println("Do you want to see all apps which are available in the play store? (yes/no)");
            String seeAllAppsChoice = scanner.nextLine();
            if (seeAllAppsChoice.equalsIgnoreCase("yes")) {
                // Display all available apps
                List<App> allApps = getAllApps();
                System.out.println("Apps available in the play store:");
                for (App allApp : allApps) {
                    System.out.println("Name: " + allApp.getName());
                    // Print other details as needed
                }
            }

            
           //Ask whether to restrict the visibility of the app from the user
            System.out.println("Do you want to see restrict the visibility of the app from the user? (yes/no):");
            String restrictChoice = scanner.nextLine();
            if (restrictChoice.equalsIgnoreCase("yes")) {
            	  for (App a : apps) {
                      if (!a.isVisible()) {
                          System.out.println("These are the restrict the visibility from the user: "+a.getName());
                      }
                  }
            } else {
                System.out.println("Visibility of the app remains unchanged.");
            }

            // Ask whether to logout
            System.out.println("Do you want to logout? (yes/no):");
            String logoutChoice = scanner.nextLine();
            if (logoutChoice.equalsIgnoreCase("yes")) {
                System.out.println("Logged out successfully.");
                // Ask whether to continue
                System.out.println("Do you want to continue? (yes/no):");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equalsIgnoreCase("yes")) {
                	 System.out.println("We appreciate your usage as an owner. Have a great day!");
                	 break;
                   }
                continueActions = false;
            }
        }
    }


    

    private void addNewApp() {
        // Get app details from the owner
//    	 System.out.println("Enter app Id:");
//         int id = Integer.parseInt(scanner.nextLine());
         System.out.println("Enter app name:");
         String name = scanner.nextLine();
         System.out.println("Enter app description:");
         String description = scanner.nextLine();
         System.out.println("Enter app releaseDate (yyyy-mm-dd):");
         Date releaseDate = Date.valueOf(scanner.nextLine());
         System.out.println("Enter app version:");
         String version = scanner.nextLine();
         System.out.println("Enter app ratings:");
         double ratings = Double.parseDouble(scanner.nextLine());
         System.out.println("Enter app category:");
         String category = scanner.nextLine();
//         System.out.println("Enter app categoryId:");
//         int categoryId = Integer.parseInt(scanner.nextLine());
         System.out.println("Enter app visible (true/false):");
         boolean visible = Boolean.parseBoolean(scanner.nextLine());

        // Create the app object
        try {
            App app = new App( name, description, releaseDate, version, ratings, category, visible);

            // Add the app to the database
            boolean added = DbUtil.addApp(app);
            if (added) {
                System.out.println("App added successfully.");
            } else {
                System.out.println("Failed to add the app.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input format. Please check the provided values.");
        }
    }


    private void updateApp() {
        // Get app name from the owner
        System.out.println("For your confirmation, please enter the name of the app to update:");
        String appName = scanner.nextLine();

        // Search for the app by name
        App appToUpdate = searchAppByName(appName);
        if (appToUpdate == null) {
            System.out.println("App not found.");
            return;
        }

        // Get updated attributes from the owner
        System.out.println("Enter updated description:");
        String newDescription = scanner.nextLine();
        System.out.println("Enter updated app version:");
        String newVersion = scanner.nextLine();
        System.out.println("Enter updated app ratings:");
        double newRatings = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter updated app visibility (true/false):");
        boolean newVisible = Boolean.parseBoolean(scanner.nextLine());

        // Update the app in the database
        String query = "UPDATE apps SET description=?, version=?, ratings=?, visible=? WHERE App_name=?";
        try {
            PreparedStatement preparedStatement = DbUtil.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, newVersion);
            preparedStatement.setDouble(3, newRatings);
            preparedStatement.setBoolean(4, newVisible);
            preparedStatement.setString(5, appName);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("App updated successfully.");
            } else {
                System.out.println("Failed to update the app.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to update the app.");
        }
    }

    private void deleteApp() {
        // Get app name from the owner
        System.out.println("Enter the name of the app to delete:");
        String appName = scanner.nextLine();

        // Search for the app by name
        App appToDelete = searchAppByName(appName);
        if (appToDelete == null) {
            System.out.println("App not found.");
            return;
        }

        // Delete the app from the database by name
        boolean deleted = DbUtil.deleteAppByName(appName);
        if (deleted) {
            System.out.println("App deleted successfully.");
        } else {
            System.out.println("Failed to delete the app.");
        }
    }

}

