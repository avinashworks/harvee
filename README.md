# Harvee QA Assessment – Sauce Demo (Selenium + Java)

Manual suite: `docs/TEST_CASES.md`. Automation: Selenium 4, Java 17, JUnit 5, Maven.

## Setup

Requirements: JDK 17+, Maven 3.9+, Google Chrome. Selenium Manager downloads the matching driver automatically.

Settings come from environment variables and have defaults, so none are required:

| Variable | Default |
|---|---|
| BASE_URL | https://www.saucedemo.com |
| STANDARD_USER | standard_user |
| LOCKED_USER | locked_out_user |
| PASSWORD | secret_sauce |
| HEADLESS | false |

## Run

```bash
mvn test                          # all tests
mvn test -Dtest=CheckoutTest      # one class
HEADLESS=true mvn test            # headless (PowerShell: $env:HEADLESS="true"; mvn test)
```

## Reports

- JUnit XML: `target/surefire-reports/`
- HTML: `mvn surefire-report:report-only` creates `target/site/surefire-report.html`
- Screenshots of failed tests: `target/screenshots/`

## Structure

- `src/test/java/config` base URL, users and password
- `src/test/java/pages` page objects (all locators live here)
- `src/test/java/tests` tests, each named with its manual case ID
- `src/test/java/support` failure screenshot extension

Every test starts its own browser and logs in itself, so tests run alone or in any order. Only explicit waits are used (no fixed sleeps).

## Traceability

| Manual case | Automated test | Class |
|---|---|---|
| TC01 | TC01_standardUserLogsIn | LoginTest |
| TC02 | TC02_lockedOutUserIsBlocked | LoginTest |
| TC03–TC05 | manual only for now (planned) | – |
| TC06 | TC06_productsPageByUrlIsBlockedWhenLoggedOut | SecurityTest |
| TC07 | TC07_sortsByPriceLowToHigh | SortingTest |
| TC08 | TC08_sortsByPriceHighToLow | SortingTest |
| TC09 | manual only for now (planned) | – |
| TC10 | TC10_addTwoItemsRemoveOne | CartTest |
| TC11–TC13 | manual only for now (planned) | – |
| TC14 | TC14_fullCheckoutTotalEqualsItemTotalPlusTax | CheckoutTest |
| TC15 | TC15_finishPlacesOrderAndEmptiesCart | CheckoutTest |
| TC16 | TC16 missing first name shows an error | CheckoutTest (parameterized) |
| TC17 | TC17 missing last name shows an error | CheckoutTest (parameterized) |
| TC18 | TC18 missing postal code shows an error | CheckoutTest (parameterized) |
| TC19, TC21–TC24 | manual only | – |
| TC20, TC25 | manual only for now (planned) | – |

## Growing to 200 tests

Keep page objects as the only place for locators, and extract components (header, cart badge) as they repeat. Group tests by feature package and tag them (`@Tag("smoke")`, `@Tag("regression")`) so every build runs the smoke set and the full suite runs nightly. Move calculation and validation checks to API or unit level to keep the UI suite small and fast. Enable JUnit parallel execution (one driver per test, already the case here) and run on Selenium Grid or CI shards, with Firefox and Edge added through a browser parameter. Create test data through the API where possible, and track flaky tests with the JUnit XML history instead of adding retries.
