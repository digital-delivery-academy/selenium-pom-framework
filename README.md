# selenium-pom-example

[![Build Status](https://travis-ci.com/digital-delivery-academy/selenium-pom-example.svg?branch=master)](https://travis-ci.com/digital-delivery-academy/selenium-pom-example)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/122f56e1b6284b319b8c23a58ab2c664)](https://www.codacy.com/gh/digital-delivery-academy/selenium-pom-example?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=digital-delivery-academy/selenium-pom-example&amp;utm_campaign=Badge_Grade)

This is an example of using Selenium to test web applications, utilising the Page Object Model design pattern.

This boilerplate is entirely scalable, and could be used quite easily as a "framework" (if that's what you think you need).

It's highly unlikely that you need anything more complicated than this.  If you do, then you probably have the scope of what you're trying to check a little wrong.

Remember that testing through a browser is brittle, slow and should be at the very top of your automation pyramid.

5-10% of all of your projects automated tests (if you're building bespoke software) should be here.  If you end up with 500 Selenium tests
then you took a wrong turn.  A really wrong turn.  Or you're trying to check some COTS product.  Good luck to ya.

If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or submit an issue ticket.

## Project & prerequisite

You need Git, Java 13 and Maven 3.x.x.  If you're on a Mac, you can install them like this:

### Install Git
`brew install git`

### Install Maven
`brew install maven`

### Install Java 13 (OpenJDK version)
`brew tap AdoptOpenJDK/openjdk`

`brew cask install adoptopenjdk13-jre`

### Getting the project out of git
`git clone https://github.com/digital-delivery-academy/selenium-pom-example.git`

### Building/running the project
Without even having to open the project in an IDE, you can run it from the command line with Maven.

`mvn clean test` 

There are two options that can be passed on the command line.  They are `baseUrl` and `configFile`.  Both of these
values will override what is set else where, and neither are required (they are both optional).

The `configFile` value allows users to set an external configuration file (that is one that is not bundled or on the 
classpath).  

The `baseUrl` parameter is used to override the baseUrl property that is required in all configuration files.

They can be used like this:

`mvn clean test -DbaseUrl=https://www.google.com -Dconfig=/path/to/config/file/config.json`

Note: Configuration files still need to be in a `json` format.

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

#### Test Data

There's a utility library included as standard here that can help with generating common test data.  The full documentation 
for that repository is here: https://www.mockneat.com/

A few basic examples though:

```
// Get random person details (email address, first and last names)
emails().get();
names().first().get();
names().last().get();

// Get random Strings/Numbers of a given length
strings().size(5).type(NUMBERS).get();
```

There are a few things that mockneat doesn't deal with, and we've added a few helpers around that:

```
// futureDate and pastDate can be staticlly imported for use
// Date formats for arguments and returns are dd/MM/yyyy

futureDate("01/06/2020", 5); // will return 06/06/2020

pastDate("06/06/2020", 5); // will return 01/06/2020
```

### Utilities

There are a number of utility classes that hide a tiny bit of complexity from you (if you need it that is).  It's anticipated that 
a fair few newcomers might use this repo across Evoco's clients, so there's a tiny little wrapper/problems solved in the areas 
listed below to get things going.

If I was working heavily in Selenium, I'd usually rely more on the WebDriverListener class for adding stablity and only really
wrap things up in helper classes/methods if I found I had to repeat a more complicated action frequently.

We're not trying to be all things to all people with this repo, so I'm not writing a full set of things *might*
help people, when the likelihood is that it would just be superfluous.

The helpers are organised here (and are all statically accessed:

- `FindByUtils` 
- `JavaScriptUtils`
- `JsonUtils`
- `SelectBoxUtils`