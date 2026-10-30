package tests;

import config.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SecurityTest extends BaseTest {

    @Test
    void TC06_productsPageByUrlIsBlockedWhenLoggedOut() {
        driver.get(Config.BASE_URL + "/inventory.html");
        assertTrue(loginPage.errorText().contains("You can only access"));
        assertFalse(driver.getCurrentUrl().contains("inventory.html"));
    }
}
