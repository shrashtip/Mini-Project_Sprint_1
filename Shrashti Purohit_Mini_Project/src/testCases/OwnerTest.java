package testCases;

import org.junit.Before;
import org.junit.Test;
import play_store.Owner;
import play_store.PlayStore;

import static org.junit.Assert.*;

public class OwnerTest {
    private Owner owner;
    private PlayStore playStore;

    @Before
    public void setUp() {
        playStore = new PlayStore();
        owner = new Owner("testOwner", "password");
    }

    @Test
    public void testOwnerLoginWithValidCredentials() {
        assertTrue(owner.login("testOwner", "password"));
    }

    @Test
    public void testOwnerLoginWithInvalidCredentials() {
        assertFalse(owner.login("invalidOwner", "password"));
        assertFalse(owner.login("testOwner", "wrongPassword"));
    }

    @Test
    public void testOwnerRegistrationWithNewUsernameAndPassword() {
        Owner newOwner = new Owner("newOwner", "newPassword");
        assertNotNull(newOwner);
        assertEquals("newOwner", newOwner.getUsername());
        assertEquals("newPassword", newOwner.getPassword());
    }

    @Test
    public void testOwnerRegistrationWithExistingUsername() {
        // Set up
        Owner existingOwner = new Owner("testOwner", "password");

        // Action
        playStore.addOwner(existingOwner);

        // Assertion
        assertEquals(1, playStore.getOwners().size()); // There should be only one owner after adding
    }

}
