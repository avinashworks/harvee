package tests;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SortingTest extends BaseTest {

    @Test
    void TC07_sortsByPriceLowToHigh() {
        loginAsStandardUser();
        inventoryPage.sortBy("lohi");
        List<Double> prices = inventoryPage.prices();
        assertEquals(6, prices.size());
        assertEquals(prices.stream().sorted().toList(), prices);
    }

    @Test
    void TC08_sortsByPriceHighToLow() {
        loginAsStandardUser();
        inventoryPage.sortBy("hilo");
        List<Double> prices = inventoryPage.prices();
        assertEquals(6, prices.size());
        assertEquals(prices.stream().sorted(Comparator.reverseOrder()).toList(), prices);
    }
}
