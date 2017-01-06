##Pre-defined steps fot UI automation:

####<a name="1"></a>1. Given As a [$type](#given-as-a-type-user) user

This method is used for initializing a user in the context of current test

All admins are pre-defined and are available before any test run.
Customers are created at runtime so to initialize a customer you first have to create it.

Credentials of admin users depend on environment, and are persisted as JSON files
in `../resources/com/copyright/rup/rightslink/tests/data/{ENV}` directory where {ENV} can be local, dev1, pre1

Examples of a step definition:

```
Given as a CCC_ADMIN user
Given as a ELSEVIER_ADMIN user
Given as a USA user
```

Parameters of a method:
#####<a name="given-as-a-type-user"></a>$type
Generrally, we use following user type as a parameter:

User's type | Value
----------- | -------------
customers   | USA, CH, DE, CAN
publisher admins | AIP_ADMIN, ELSEVIER_ADMIN, KARGER_ADMIN, MVB_ADMIN
CCC admins|CCC_ADMIN

####<a name="2"></a>2. Given I go to [$page](#given-i-go-page)
This is method is used to navigate to a page in a browser.

Examples:
> For page transactionLink.json we use<p>
`Given I go to transactionLink`<p>
> For page AIPJournalPage.json we use<p>
`Given I go to AIPJournalPage`

Parameters of a method:
#####<a name="given-i-go-page"></a>$page
Name of a page abstraction (equal to a filename of a page *.json, e.g. for `transactionLink.json` we use `transactionLink`)

Before using this method you have to be sure that page abstraction is created
in `../src/resources/com/copyright/rup/rightslink/tests/pages` directory. See this [**readme**](rightslink-createAccount/README.md#create-page) for more info.

####<a name="3"></a>3. Then I click [$button](#then-i-click-button) on [$page](then-click-on-a-page)
This method is used for clicking buttons on pages. Before using it be sure that you previously were on needed page. See [method](#2) for info about how to go to a page.

Examples:
```
...
Given I go to transactionLink
Then I click search on transactionLink
...
Given I go to myAccountPage
Then I click logout on myAccountPage
...
Given I go to createAccountPage
Then I click 1 Proceed Registration on createAccountPage
...
```

Parameters of a method:
#####<a name="then-i-click-button"></a>$button

Name of a button object in a page abstraction's [buttons collection](rightslink-createAccount/README.md#buttons-collection).

Before using this method you have to be sure that your page abstraction is valid against used schema. See this [**readme**](rightslink-createAccount/README.md#create-page) for more info.
#####<a name="then-click-on-a-page"></a>$page
Analogous to [$page](#given-i-go-page)

####<a name="4"></a>4.Given I log in to system on [$page](#i-login-to-page)
This method provides common logic to perform SSO-login at any page.
To use this method, follow these steps:
1. Initialize a user by [method](#1)
2. Add a button "login" to [buttons collection](rightslink-createAccount/README.md#buttons-collection) of your page abstraction
3. Go to a page using [method](#2)

Examples:
```
...
Given as a CCC_ADMIN user
Then I go to CCCAdminPage

```
Parameters of a method:
#####<a name="i-login-to-page"></a>$page
Analogous to [$page](#given-i-go-page)

####<a name="5"></a>5.Then I check following elements on [$page](#i-check-on-page) : [$table](#i-check-table) 
This method performs assertions of different types on the current page.

To use this method, follow these steps:

1. Initialize a user by [method](#1)
2. Add a button "login" to [buttons collection](../README.md#buttons-collection) of your page abstraction
3. Go to a page using [method](#2)

Examples:
```
...
Given as a CCC_ADMIN user
Then I go to CCCAdminPage

```
Parameters of a method:
#####<a name="i-login-to-page"></a>$page
Analogous to [$page](#given-i-go-page)

