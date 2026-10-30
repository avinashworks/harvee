package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {
    private static final By ITEMS = By.cssSelector(".inventory_item");
    private static final By PRICES = By.cssSelector("[data-test='inventory-item-price']");
    private static final By SORT = By.cssSelector("[data-test='product-sort-container']");
    private static final By BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By CART_LINK = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage waitUntilLoaded() {
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        wait.until(ExpectedConditions.numberOfElementsToBe(ITEMS, 6));
        return this;
    }

    public int itemCount() {
        return driver.findElements(ITEMS).size();
    }

    public void sortBy(String optionValue) {
        new Select(visible(SORT)).selectByValue(optionValue);
    }

    public List<Double> prices() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(PRICES));
        return driver.findElements(PRICES).stream()
                .map(WebElement::getText)
                .map(t -> Double.parseDouble(t.replace("$", "")))
                .toList();
    }

    private By itemButton(String productName, String label) {
        return By.xpath("//div[@class='inventory_item'][.//div[@data-test='inventory-item-name']"
                + "[normalize-space()='" + productName + "']]//button[normalize-space()='" + label + "']");
    }

    public void add(String productName) {
        click(itemButton(productName, "Add to cart"));
    }

    public void remove(String productName) {
        click(itemButton(productName, "Remove"));
    }

    /** Waits for the cart badge to show n items (n = 0 means no badge). */
    public void waitForBadge(int n) {
        if (n == 0) {
            wait.until(ExpectedConditions.numberOfElementsToBe(BADGE, 0));
        } else {
            wait.until(ExpectedConditions.textToBe(BADGE, String.valueOf(n)));
        }
    }

    public int badgeCount() {
        List<WebElement> badge = driver.findElements(BADGE);
        return badge.isEmpty() ? 0 : Integer.parseInt(badge.get(0).getText());
    }

    public void openCart() {
        click(CART_LINK);
    }
}
