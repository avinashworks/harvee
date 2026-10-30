package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutTest extends BaseTest {
    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String LIGHT = "Sauce Labs Bike Light";

    private void startCheckoutWith(String... products) {
        loginAsStandardUser();
        for (String product : products) {
            inventoryPage.add(product);
        }
        inventoryPage.openCart();
        cartPage.checkout();
    }

    @Test
    void TC14_fullCheckoutTotalEqualsItemTotalPlusTax() {
        startCheckoutWith(BACKPACK, LIGHT);
        checkoutPage.fillInfo("Jane", "Doe", "12345");

        BigDecimal expected = checkoutPage.subtotal().add(checkoutPage.tax()).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, expected.compareTo(checkoutPage.total()));
    }

    @Test
    void TC15_finishPlacesOrderAndEmptiesCart() {
        startCheckoutWith(BACKPACK);
        checkoutPage.fillInfo("Jane", "Doe", "12345");
        checkoutPage.finish();

        assertEquals("Thank you for your order!", checkoutPage.completeHeader());
        inventoryPage.waitForBadge(0);
        assertEquals(0, inventoryPage.badgeCount());
    }

    static Stream<Arguments> missingFieldCases() {
        return Stream.of(
                Arguments.of("TC16", "first name", "", "Doe", "12345", "First Name is required"),
                Arguments.of("TC17", "last name", "Jane", "", "12345", "Last Name is required"),
                Arguments.of("TC18", "postal code", "Jane", "Doe", "", "Postal Code is required"));
    }

    @ParameterizedTest(name = "{0} missing {1} shows an error")
    @MethodSource("missingFieldCases")
    void checkoutRequiresAllFields(String id, String field, String first, String last, String postal, String error) {
        startCheckoutWith(BACKPACK);
        checkoutPage.fillInfo(first, last, postal);
        assertTrue(checkoutPage.errorText().contains(error));
    }
}
