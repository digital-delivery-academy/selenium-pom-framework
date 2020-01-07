# selenium-pom-example

This is an example of using Selenium to test web applications, utilising the Page Object Model design pattern.

This boilerplate is entirely scalable, and could be used quite easily as a "framework" (if that's what you think you need).

It's highly unlikely that you need anything more complicated than this.  If you do, then you probably have the scope of what you're trying to check a little wrong.

Remember that testing through a browser is brittle, slow and should be at the very top of your automation pyramid.

5-10% of all of your projects automated tests (if you're building bespoke software) should be here.  If you end up with 500 Selenium tests
then you took a wrong turn.  A really wrong turn.  Or you're trying to check some COTS product.  Good luck to ya.

If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or submit an issue ticket.

## Project & pre-requesists

You need Git, Java 13 and Maven 3.x.x.  If you're on a Mac, you can install them like this:

### Install Git
`brew install git`

### Install Maven
`brew install maven`

### Install Java 13 (OpenJDK version obvs)
`brew tap AdoptOpenJDK/openjdk`

`brew cask install adoptopenjdk13-jre`

### Getting the project out of git
`git clone https://github.com/digital-delivery-academy/selenium-pom-example.git`

### Building/running the project
Without even having to open the project in an IDE, you can run it from the command line with Maven.

`mvn clean test` 

## High level overview

### Design patterns

The project uses the Page Object Model design pattern.  This sees each page in an application modeled as an individual Java class.
These classes expose operations that could be completed within the application under test via methods in the Page Object classes.

Any method that causes the application under test to change pages, should return a new instance of the Page Object class that the 
application under test will end up on as a result of the action being taken by the method.

Take at the GoogleLandingPage.clickGoogleSearchButton() to see an example.

### Resolving dependencies for the project and WebDriver binaries

#### Maven 
We resolve all of the application libraries from Maven.  Maven takes care of the projects builds.  It downloads
all of the dependencies that we will need and adds them to the classpath so that we can use them.

This means that no matter where we use the tests, we always use the given versions of libraries and we always do it in the same.

This helps too (but doesn't eliminate) the whole "works on my machine" thing.

#### Selenium
Selenium uses drivers that are compiled for specific operating system/browser combinations.  These drivers are issued by the 
browser vendors and must be available to WebDriver to be able to launch and control browsers on a machine.

Frequently people download and store the binaries in repositories like this one.  But, that increases the time for tools like Jenkins
to check out a project (the binaries can be big) and maintaining them over time just makes the git footprint even bigger.

We're using a library built by a spanish fella called Bonie Garcia to resolve the browser binaries and add their paths to the 
the environment variables for WebDriver to work with.  It's nicer all round.

## Components

All of the classes are really well commented (if I do say so myself).

### BasePageObject
 
This is an asbtract page object that takes care of all of the core page object stuff (like initiating annotated WebElements/lcoators).

### WebDriverBuilder

This takes care of setting up consistently configured browsers for tests to use that use the WebDriverListener
implementation.

### WebDriverListener

This implements EventFiringWebDriver that gives us opportunities to add some stablising code to `before` and `after` steps in WebDriver (like `click()` and `findBy` and `onException`).

### AbstractTest

This is responsible for getting a new WebDriver instance and doing the initial navigation to the application under test.  This should be used by
all Test classes.

### Tests
These are in the tests module and are arranged by class.  Each method in the class is a distinct and associated test to the area of functionality that
the class is concerned with (this is a logical concept not a codified one).

The test classes all extend/inherit form the AbstractTest which is responsible for the `BeforeAll` and `AfterAll` steps.

Assertions should be in each `@Test` not abstracted away.  The arguments for this are well known, I'm not googling it for you.  
If you aren't familiar with them though, take a little time to educate yourself about why this is important (from a credible resource like Richard Bradshaw or Mark Winteringham or Alan Richardson or Ministry of Testing etc). 