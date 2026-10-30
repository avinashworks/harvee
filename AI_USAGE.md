# AI Usage

I used an AI coding assistant (Claude) during this assessment. This file lists each place I used it, what I asked for, and what I changed or checked myself.

## Where I used it

| # | Area | What I asked for | What I changed or checked |
|---|---|---|---|
| 1 | A1: questions and assumptions | A draft list of questions for the product owner and assumptions to test against | Kept the questions that affect test results (tax rounding, postal code rules, empty cart, cart persistence). [Confirm: checked each assumption against the live site.] |
| 2 | A1: manual test cases | A draft of 20–25 cases covering positive, negative, boundary, usability, browser, accessibility and security | Merged two sort cases to reach exactly 25 and renumbered the IDs, so the manual and automated IDs match. Reviewed the "Automate?" column and kept cases with unconfirmed rules (odd input values, empty cart) manual. |
| 3 | A1: exploratory charter and strategy | A 15-minute charter for `problem_user` and a strategy under 150 words | Checked the strategy against my own case list and kept the biggest risk as unconfirmed money and validation rules. |
| 4 | A2: project setup | A first Playwright structure, which I then replaced | I chose Selenium with Java, JUnit 5 and Maven for the final suite, so the Playwright files were removed. |
| 5 | A2: automation code | Page objects, base test, config, a failure-screenshot extension, and tests named by manual case ID (TC01, TC02, TC06, TC07, TC08, TC10, TC14–TC18) | Ran `mvn test` myself: 11 tests, 0 failures, BUILD SUCCESS. Checked that tests use explicit waits (no fixed sleeps), keep locators in page objects, read settings from config, and start a fresh browser each so they run in any order. Judged the Selenium CDP version warnings as harmless because no test uses DevTools. |
| 6 | A2: README | README with setup, run commands, the TC-to-test mapping table, and the 200-test growth paragraph | Compared the table with the test classes to confirm every mapped test exists. |
| 7 | Environment help | Fixing a TypeScript `process` error, installing Maven on Windows, and a nested folder that hid `pom.xml` | Followed the steps and confirmed `mvn -version` and the test run worked. |
| 8 | Section B | A draft of B1–B4 | Changed the B1 rewrite to Selenium and Java so it matches my suite. Reviewed the severity reasoning in B3 and the 150-word limit in B4. Edited headings and wording. [Confirm: ran or reviewed the B1 rewritten test.] |

## How I checked the output

- Ran the full suite locally and read the results instead of trusting the generated code.
- Checked that assertions are meaningful: the sort tests compare the whole list, and the checkout test compares item total plus tax with the total.
- Read the selectors and error messages against the Sauce Demo site.
- Made sure the manual cases and automated tests trace to each other by ID.

## My own decisions

- Choice of Selenium, Java and JUnit 5.
- Which cases to automate and which to keep manual.
- The final wording and recommendations in Section B.
