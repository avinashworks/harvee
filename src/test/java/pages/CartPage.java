package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {
    private static final By ITEM_NAMES = By.cssSelector(".cart_item [data-test='inventory-item-name']");
    private static final By CHECKOUT = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<String> itemNames() {
        wait.until(ExpectedConditions.urlContains("cart.html"));
        return driver.findElements(ITEM_NAMES).stream().map(WebElement::getText).toList();
    }

    public void checkout() {
        click(CHECKOUT);
    }
}
