package pages;

import config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private static final By USERNAME = By.cssSelector("[data-test='username']");
    private static final By PASSWORD = By.cssSelector("[data-test='password']");
    private static final By LOGIN_BUTTON = By.cssSelector("[data-test='login-button']");
    private static final By ERROR = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.BASE_URL);
        return this;
    }

    public void login(String user, String password) {
        type(USERNAME, user);
        type(PASSWORD, password);
        click(LOGIN_BUTTON);
    }

    public String errorText() {
        return visible(ERROR).getText();
    }
}
