package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckoutPage extends BasePage {
    private static final By FIRST_NAME = By.cssSelector("[data-test='firstName']");
    private static final By LAST_NAME = By.cssSelector("[data-test='lastName']");
    private static final By POSTAL_CODE = By.cssSelector("[data-test='postalCode']");
    private static final By CONTINUE = By.cssSelector("[data-test='continue']");
    private static final By ERROR = By.cssSelector("[data-test='error']");
    private static final By SUBTOTAL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL = By.cssSelector("[data-test='total-label']");
    private static final By FINISH = By.cssSelector("[data-test='finish']");
    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");

    private static final Pattern AMOUNT = Pattern.compile("\\$(\\d+(?:\\.\\d+)?)");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInfo(String first, String last, String postal) {
        type(FIRST_NAME, first);
        type(LAST_NAME, last);
        type(POSTAL_CODE, postal);
        click(CONTINUE);
    }

    public String errorText() {
        return visible(ERROR).getText();
    }

    private BigDecimal amount(By by) {
        String text = visible(by).getText();
        Matcher m = AMOUNT.matcher(text);
        if (!m.find()) {
            throw new IllegalStateException("No amount found in: " + text);
        }
        return new BigDecimal(m.group(1));
    }

    public BigDecimal subtotal() { return amount(SUBTOTAL); }
    public BigDecimal tax() { return amount(TAX); }
    public BigDecimal total() { return amount(TOTAL); }

    public void finish() {
        click(FINISH);
    }

    public String completeHeader() {
        return visible(COMPLETE_HEADER).getText();
    }
}
