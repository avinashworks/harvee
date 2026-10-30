package tests;

import config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import support.ScreenshotExtension;

import java.util.Map;

@ExtendWith(ScreenshotExtension.class)
public abstract class BaseTest {
    public WebDriver driver;

    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1280,900");
        if (Config.HEADLESS) {
            options.addArguments("--headless=new");
        }
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false));
        driver = new ChromeDriver(options);

        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /** Logs in as the standard user and waits for the products page. */
    protected void loginAsStandardUser() {
        loginPage.open().login(Config.STANDARD_USER, Config.PASSWORD);
        inventoryPage.waitUntilLoaded();
    }
}
