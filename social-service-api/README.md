#Smoke test automation for RightsLink

This test automation framework provides an easy way to automate UI testing within Copyright projects.

#####To create a new UI automation test you have to do only three simple steps:

1. [Write your own story file](#write-story)
2. [Create page abstractions for tested pages as a JSON file](#create-page)
3. [Run your test using gradle wrapper command line](#run-story)

##<a name="write-story"></a>How to write a story

*.story files are written in a human-readable format. They look like:
```jbehave story
Narrative: As a USA user
I want to perform some actions
To be sure that everything works

Scenario: Make some actions 

Given As a USA user
And I go to kargerJournalPage
Then I click search on kargerJournalPage
```
In our project we have a handful of pre-defined methods that could be reused while writing a new story. To read about these methods visit [Jbehave methods](rightslink-createAccount/src/test/groovy/com/copyright/rup/rightslink/tests/steps/common/README.md).<p>
To use this methods in your scenarios just write them, using your parameters<p>
(**Note:** make sure that you use right starting keywords `Given, When, Then`. If you have two or more subsequent keywords, you can use And keyword)

Besides pre-defined **methods** there are already pre-defined complete **stories**, such as:
- Create a user
- Place an order
- Credit a license

To use any of stories as a pre-requisite to your story, just add **Given stories:** parameter after a Scenario keyword. For example:
```jbehave story
Narrative: {description goes here}

Scenario: {scenario name goes here}

GivenStories: [relative path to stories folder]/create/users/CreateAccount_CAN_Corp.story

Given {step}
When {step}
Then {step}

```

Every new story should have *.story format and should be put to [`../src/resources/com/copyright/rup/rightslink/tests/stories`](src/resources/com/copyright/rup/rightslink/tests/stories) folder

To make your *.story syntax clear, you can also create composite steps. For more information [read this](src/test/groovy/com/copyright/rup/rightslink/tests/steps/specific/README.md).

##<a name="create-page"></a>How to create a page abstraction

In our project we use JSON format for persisting page abstractions. Each page should contain these obligatory elements:
- **"prefix"** - name of a environment-specific property that has http-prefix value (e.g. `"prefix": "web.prefix"` means that we have `web.prefix=http://dev100.copyright.com` in dev1.properties file)
- **"suffix"** - name of a global property that has http-suffix value (e.g. for **myAccountPage**: `"suffix": "myAccount"` means that we have `myAccountPage=MyAccount` in globalTest.properties file) (altogether it means a resulting url: [http://dev100.copyright.com/MyAccount](http://dev100.copyright.com/MyAccount))
- **"elements"** - collection of *element*s on a page. Every *element* must contain:
  - **"by"** - type of selector, available values for now - *"id"*, *"name"*, *"xpath"*
  - **"selector"** - value of a selector, e.g. *"deleteButton"* for *"id"*/*"name"* or *".//\*\[@id='viewOrdersLink']/a"* for *"xpath"*
- <span name="buttons-collection">**"buttons"**</span> - collection of *button*s on a page. Every *button* must contain:
  - **"by"** - analogous to *element*'s "by"
  - **"selector"** - analogous to *element*'s "selector"
- **"forms"** - collection of *form*s on a page. Every *form* must contain:
  - **"by"** - analogous to *element*'s "by"
  - **"selector"** - analogous to *element*'s "selector"
  - **"type"** - type of input. Available values for now: *"check"*, *"radio"*, *"text"*, *"option"*. These names correspond to checkbox, radiobutton, text field or area and select input types accordingly.
  
Put your newly created page abstraction to `../src/resources/com/copyright/rup/rightslink/tests/pages` folder.
  
Before running any test a gradle task verifyPage is performed. This task validates every page abstraction against *schema.json* schema. If found any schema violations build will fail and tests won't be executed. You can run `./gradlew verifyPage` at any time to ensure that you've created correct page abstractions.

For convenience run `./gradlew cleanIdea` task to add a useful edit-time schema validation to your Idea project. Or manually add a schema and scope for it:
`File | Settings | Languages & Frameworks | Schemas and DTDs | JSON Schema`


##<a name="run-story"></a>How to run a UI automated story 
Running an automated UI test is simple enough. Just do not forget all obligatory *qaa* JVM parameters: [properties info](#used-properties) 

Here are some frequently used gradle commands to run tests:

1. To run all scenarios in Story_B-27001.story with all included *GivenStories* in pre1 environment and using firefox:
  ```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
 -Dqaa.story.filter= \
 -Dqaa.environment=pre1 \
 -Dqaa.story.mask.inc=*27001* \
 -Dqaa.story.mask.exc= \
 -Dqaa.selenium.driver=firefox
  ```
2. The same but in dev1 environment and using chrome:
```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
-Dqaa.story.filter= \
-Dqaa.environment=dev1 \
-Dqaa.story.mask.inc=*27001* \
-Dqaa.story.mask.exc= \
-Dqaa.selenium.driver=chrome \
-Dqaa.chrome.driver={path_to_chromedriver}
```

3. To run a particular scenario in Story_B-27001.story with all included *GivenStories* (according to 1st scenario we create USA user and place MR order) for dev1 environment and using firefox:
```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
-Dqaa.story.filter="+createUser USA,+placeOrder MR,+B-27001-1" \
-Dqaa.environment=dev1 \
-Dqaa.story.mask.inc=*27001* \
-Dqaa.story.mask.exc= \
-Dqaa.selenium.driver=firefox
```

4. To only create a DE user in dev1 environment using firefox:
```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
-Dqaa.story.filter="+createUser DE" \
-Dqaa.environment=dev1 \
-Dqaa.story.mask.inc=Create* \
-Dqaa.story.mask.exc= \
-Dqaa.selenium.driver=firefox 
```

5. To only place ePrint order, using existing user and not creating a new one in pre1 environment using firefox:
```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
-Dqaa.story.filter="-createUser,+placeOrder ePrint" \
-Dqaa.environment=pre1 \
-Dqaa.story.mask.inc=Smoke* \
-Dqaa.story.mask.exc= \
-Dqaa.selenium.driver=firefox 
```

6. To run a particular scenario in Story_B-27001.story without all included *GivenStories* (according to 1st scenario we already created USA user and placed MR order) for dev1 environment and using firefox:
```cmd
./gradlew rightslink-webapi:rightslink-createAccount:bddTest \
-Dqaa.story.filter="-createUser,-placeOrder,+B-27001-1" \
-Dqaa.environment=dev1 \
-Dqaa.story.mask.inc=*27001* \
-Dqaa.story.mask.exc= \
-Dqaa.selenium.driver=firefox
```

(**Note:** all created users, placed orders, licenses, invoices or credit memos are saved in `../build/resources/test/com/copyright/rup/rightslink/tests/data/{ENVIRONMENT}` folder as JSON files and can be re-used at any time for testing. Once you created a user or placed an order of the same type, it will overwritten by the newest version)

###<a name="used-properties"></a>Used properties:
#####qaa.environment
'local' by default. Possible values: 'dev1', 'pre1', 'local'. This value determines what properties file is used for getting environment-specific properties such as url-prefixes and datasources. Also it specifies the folder to save to/read from created users, orders etc. 
#####qaa.story.mask.inc
 '*' by default. This is JBehave's mask is used for running scenarios only in particular *.story files
#####qaa.story.mask.exc
 '' by default. This is JBehave's mask used for excluding particular *.story files from running
#####qaa.story.filter
 empty by default. Used for meta-filtering - JBehave's specific feature. In this project is used to include/exclude GivenStories and to run a particular scenario within a story. See examples of usage in [How to run a UI automated story](#run-story) section.
#####qaa.selenium.driver
 'firefox' by default. This option helps to use particular Selenium WebDriver implementation. Valid options: 'firefox', 'chrome', 'phantom'
 For 'chrome' you'll also have to add `qaa.chrome.driver` property
#####qaa.chrome.driver
 Determines an absolute path to your local chrome web driver e.g. `/usr/local/bin/google-chrome`. You can download one at [chrome driver download](https://sites.google.com/a/chromium.org/chromedriver/downloads)