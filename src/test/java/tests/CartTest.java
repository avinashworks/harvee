package tests;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest extends BaseTest {
    private static final String BACKPACK = "Sauce Labs Backpack";
    private static final String LIGHT = "Sauce Labs Bike Light";

    @Test
    void TC10_addTwoItemsRemoveOne() {
        loginAsStandardUser();
        inventoryPage.add(BACKPACK);
        inventoryPage.add(LIGHT);
        inventoryPage.waitForBadge(2);
        inventoryPage.remove(LIGHT);
        inventoryPage.waitForBadge(1);
        assertEquals(1, inventoryPage.badgeCount());

        inventoryPage.openCart();
        assertEquals(List.of(BACKPACK), cartPage.itemNames());
    }
}
