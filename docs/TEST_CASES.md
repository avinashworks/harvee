# Section A1 – Manual Test Suite

**Use case:** Shop and check out (Sauce Demo, https://www.saucedemo.com)
**Users:** standard_user, locked_out_user, problem_user (password: secret_sauce)

## 1. Questions and assumptions

| # | Question for the product owner | Assumption I will test against |
|---|---|---|
| 1 | Is the postal code checked for format or length? | Any non-empty value is accepted |
| 2 | Are names checked (digits, symbols, whitespace only)? | Any non-empty value is accepted |
| 3 | What is the tax rate, and how is it rounded? | Fixed 8%, rounded to 2 decimals |
| 4 | Does the cart survive a page refresh or a logout and login? | It survives a refresh, and it is cleared on logout |
| 5 | Is a sort choice kept after navigating away? What is the default sort? | Default is Name (A to Z), and the sort resets on reload |
| 6 | Can an empty cart go to checkout? | It should be blocked, so a bug is raised if it is allowed |
| 7 | Is there an API behind the UI, and which browsers, screen sizes and accessibility standard (for example WCAG 2.1 AA) are in scope? | Latest Chrome, Firefox and Safari, a 375px mobile width, and WCAG 2.1 AA |

## 2. Test cases (25)

| ID | Area | Type | Title | Pre-conditions | Steps | Test data | Expected result | Priority | Automate? |
|---|---|---|---|---|---|---|---|---|---|
| TC01 | Login | Positive | Standard user logs in | On login page | 1. Enter credentials 2. Click Login | standard_user / secret_sauce | Products page shown with 6 items | P1 | Yes – smoke test on every build |
| TC02 | Login | Negative | Locked-out user is blocked | On login page | 1. Enter credentials 2. Click Login | locked_out_user / secret_sauce | Stays on login page; error "this user has been locked out" | P1 | Yes – key business rule |
| TC03 | Login | Negative | Invalid credentials rejected | On login page | 1. Enter wrong password 2. Repeat with an unknown user | standard_user / wrong; fake_user / secret_sauce | Error "do not match any user"; no login | P1 | Yes – parameterized |
| TC04 | Login | Negative | Empty username or password | On login page | 1. Leave one or both fields blank 2. Click Login | blank / blank; user / blank | "Username is required" or "Password is required" | P2 | Yes – parameterized |
| TC05 | Login | Boundary | Credentials are case- and space-sensitive | On login page | 1. Enter "Standard_User" 2. Enter " standard_user " with a leading space | secret_sauce | Login rejected in both cases | P3 | Yes – cheap to add to TC03 |
| TC06 | Security | Negative | Inner page blocked by URL when logged out | Logged out, new session | 1. Open /inventory.html directly | – | Redirected to login; error says the page can only be accessed when logged in | P1 | Yes – business rule |
| TC07 | Sort | Positive | Price low to high, full list | Logged in as standard_user | 1. Select "Price (low to high)" 2. Read all 6 prices | – | All 6 prices are in ascending order | P1 | Yes – check the whole list |
| TC08 | Sort | Positive | Price high to low, full list | Logged in as standard_user | 1. Select "Price (high to low)" 2. Read all 6 prices | – | All 6 prices are in descending order | P1 | Yes |
| TC09 | Sort | Positive | Name sort A to Z (default) and Z to A, stable after add-to-cart | Logged in as standard_user | 1. Check default order 2. Select "Name (Z to A)" 3. Add an item 4. Check the list order | – | A to Z on load; reversed after selecting Z to A; order unchanged after add-to-cart and the button changes to Remove | P2 | Yes – parameterized |
| TC10 | Cart | Positive | Add two items, remove one | Logged in, empty cart | 1. Add 2 items 2. Remove 1 on the products page 3. Open cart | Backpack, Bike Light | Badge shows 1; cart lists only the remaining item | P1 | Yes – core flow |
| TC11 | Cart | Positive | Remove items from the cart page | 2 items in cart | 1. Open cart 2. Remove each item in turn | – | Badge drops to 1, then disappears; cart is empty | P2 | Yes |
| TC12 | Cart | Boundary | Add all 6 items | Logged in, empty cart | 1. Add every item 2. Open cart | – | Badge shows 6; cart has 6 correct items | P2 | Yes |
| TC13 | Cart | Positive | Cart persists on refresh and navigation | 1 item in cart | 1. Refresh the page 2. Go Products, Cart, Products | – | Badge and cart contents unchanged | P2 | Yes |
| TC14 | Checkout | Positive | Full checkout, total is correct | 2 items in cart | 1. Click Checkout 2. Fill the form 3. Click Continue 4. Read the overview | Jane / Doe / 12345 | Item total + tax (8%) = Total, to 2 decimals | P1 | Yes – money-critical |
| TC15 | Checkout | Positive | Finish places the order and empties the cart | On overview page | 1. Click Finish 2. Return to products | – | "Thank you for your order!" shown; badge gone; cart empty | P1 | Yes |
| TC16 | Checkout | Negative | Missing first name | On checkout info page | 1. Leave first name blank 2. Click Continue | "" / Doe / 12345 | "First Name is required"; stays on page | P1 | Yes – parameterized |
| TC17 | Checkout | Negative | Missing last name | On checkout info page | 1. Leave last name blank 2. Click Continue | Jane / "" / 12345 | "Last Name is required"; stays on page | P1 | Yes – parameterized |
| TC18 | Checkout | Negative | Missing postal code | On checkout info page | 1. Leave postal code blank 2. Click Continue | Jane / Doe / "" | "Postal Code is required"; stays on page | P1 | Yes – parameterized |
| TC19 | Checkout | Boundary | Odd name and postal code values | On checkout info page | 1. Submit a 1-character value 2. Submit 256 characters 3. Submit symbols 4. Submit whitespace only | "A", "!!!", " ", 256 x "9" | Behaviour matches the assumption; no crash or layout break | P3 | No – the rule is unconfirmed, so run it manually |
| TC20 | Checkout | Negative | Cancel on the overview page | On overview page | 1. Click Cancel | – | Back to products; no order placed; cart kept | P2 | Yes |
| TC21 | Checkout | Boundary | Checkout with an empty cart | Logged in, empty cart | 1. Open cart 2. Click Checkout | – | Blocked (per assumption); otherwise raise a defect | P2 | No – wait for the answer to Q6 |
| TC22 | Usability | Positive | Navigation and message clarity | Logged in | 1. Use Continue Shopping, Back and the menu 2. Read each error message | – | Flow is predictable; error messages are clear | P3 | No – needs human judgement |
| TC23 | Browser / screen size | Positive | Cross-browser and mobile layout | Logged in | 1. Run the TC14 flow on Firefox, Safari and a 375px width | – | No layout break; the flow completes | P2 | No – manual first, then add Playwright projects |
| TC24 | Accessibility | Positive | Keyboard-only checkout | Logged in | 1. Tab through login, cart and checkout using Enter and Space only 2. Check labels and focus | – | Every control is reachable; focus is visible; fields have labels | P2 | No – manual, with an axe scan later |
| TC25 | Security | Negative | Password masked; no access after logout | Logged in | 1. Check the password field type 2. Log out 3. Press browser Back | – | Password masked and not in the URL; Back does not show inner pages | P2 | Yes – extends TC06 |

## 3. Exploratory charter (15 minutes)

- **Mission:** Explore the shop flow as `problem_user` and find where it differs from `standard_user`.
- **Areas:**
  - Product images and details
  - Add and remove buttons, and the badge count
  - Sorting
  - Checkout form fields
  - The final total
- **Approach:** Repeat TC10, TC09 and TC14 side by side with both users, and note every difference.
- **Output:** Defects found, questions raised, and areas not covered.

## 4. Strategy

- **UI layer:** P1 and P2 business-rule cases (TC01–TC18, TC20, TC25). These are stable, high-value flows that run on every build.
- **API or unit layer, if the client has one:** Tax and total calculation, price sorting logic, and input validation for names and postal codes. These are faster and more precise to check there than through the UI.
- **Manual:** Usability, accessibility, cross-browser and mobile, odd input values (TC19, TC21), and the `problem_user` exploratory charter. Cases whose rules are still unanswered stay manual.
- **Biggest remaining risk:** Money calculations and validation rules that are unconfirmed (tax rounding, postal code format) and are checked only through the UI. A wrong total could ship while every UI test still passes. I would report this together with the untested browsers and screen sizes.
