package testCases;

import org.junit.Before;
import org.junit.Test;
import play_store.App;
import play_store.Owner;
import play_store.PlayStore;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AppTestCases {
    private PlayStore playStore;
    private Owner owner;

    @Before
    public void setUp() {
        playStore = new PlayStore();
        owner = new Owner("ownerUsername", "ownerPassword");
    }

    @Test
    public void testAddNewApp() {
       
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

       
        owner.addApp(app);

     
        List<App> ownerApps = owner.getApps();

        assertTrue(ownerApps.contains(app));
    }

    @Test
    public void testUpdateExistingApp() {
        
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

      
        owner.addApp(app);


        List<App> ownerApps = owner.getApps();

        
        App appToUpdate = null;
        for (App a : ownerApps) {
            if (a.getName().equals("Test App")) {
                appToUpdate = a;
                break;
            }
        }

        assertNotNull(appToUpdate);

        // Update the app
        appToUpdate.setDescription("Updated Description");

        // Check if the app description is updated
        assertEquals("Updated Description", appToUpdate.getDescription());
    }

    @Test
    public void testDeleteApp() {
        // Create a new app
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

        // Add the app to the owner's list of apps
        owner.addApp(app);

        // Delete the app from the owner's list of apps
        owner.getApps().remove(app);

        // Retrieve the list of apps from the owner
        List<App> ownerApps = owner.getApps();

        // Check if the app is deleted successfully
        assertFalse(ownerApps.contains(app));
    }

    @Test
    public void testSearchAppByName() {
        // Create a new app
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

        // Add the app to the owner's list of apps
        owner.addApp(app);

        // Search for the app by name
        App searchedApp = owner.searchAppByName("Test App");

        // Check if the searched app matches the original app
        assertEquals(app, searchedApp);
    }

    @Test
    public void testDisplayAppsByCategory() {
        // Create a new app
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

        // Add the app to the owner's list of apps
        owner.addApp(app);

        // Display apps by category
        owner.getApps().forEach(a -> {
            if (a.getCategory().equals("Test Category")) {
                System.out.println(a.getName());
            }
        });

        // Validate the output by checking the console or log
        // Here, you might need to check the console or log to ensure that the apps are displayed correctly.
    }

    @Test
    public void testDisplayAppDetails() {
        // Create a new app
        App app = new App("Test App", "Description", Date.valueOf("2024-04-05"), "1.0", 4.5, "Test Category", true);

        // Add the app to the owner's list of apps
        owner.addApp(app);

        // Display app details
        owner.getApps().forEach(a -> {
            if (a.getName().equals("Test App")) {
                System.out.println("Name: " + a.getName());
                System.out.println("Description: " + a.getDescription());
                System.out.println("Release Date: " + a.getReleaseDate());
                System.out.println("Version: " + a.getVersion());
                System.out.println("Rating: " + a.getRatings());
                System.out.println("Category: " + a.getCategory());
                System.out.println("Visible: " + a.isVisible());
            }
        });

        // Validate the output by checking the console or log
        // Here, you might need to check the console or log to ensure that the app details are displayed correctly.
    }
}
