package tests;

import config.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    @Test
    void TC01_standardUserLogsIn() {
        loginPage.open().login(Config.STANDARD_USER, Config.PASSWORD);
        inventoryPage.waitUntilLoaded();
        assertTrue(inventoryPage.itemCount() == 6);
    }

    @Test
    void TC02_lockedOutUserIsBlocked() {
        loginPage.open().login(Config.LOCKED_USER, Config.PASSWORD);
        assertTrue(loginPage.errorText().contains("this user has been locked out"));
        assertFalse(driver.getCurrentUrl().contains("inventory.html"));
    }
}
