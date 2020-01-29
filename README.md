# selenium-pom-framework

[![Build Status](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework.svg?branch=master)](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ce6169f2151d43daaa0db3aab560e1f0)](https://www.codacy.com/gh/digital-delivery-academy/selenium-pom-framework?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=digital-delivery-academy/selenium-pom-framework&amp;utm_campaign=Badge_Grade)

This toolkit is really a culmination in 10 years of working with teams building automated UI checks with Selenium.  We've found time and again that we refactor or rewrite "frameworks" that wrap the entirity of the Selenium API (to little benefit).  On top of this, we see so much brittle code that we wanted to put something out there that people could use and benefit from our experience.  As a result, this toolkit provides an simple, lightweight (and well structured) way to launch, control and configure tests for Selenium/WebDriver in Java.  It's a curation of all of the little libraries and fixes we've used over the years.  So if you're at the start of our automation journey or you're just bored of writing the same "framework" over and over again, you're in the right place.

You'll find no junk (hopefully) here, but it's a fairly opinionated approach:
- There's no mass "wrapper" on Selenium - that's dumb
- There's no Cucumber - Acceptance Test Driven Development is overkill, and we won't help you do it, it's not solving the problem you think it is ;)
- We suggest using the Page Object Model, it's generally a good idea and we provide a starting point for this
- We resolve Driver binaries from WebDriverManager (https://github.com/bonigarcia/webdrivermanager)
- We provide random test data generators from Mockneat (https://github.com/nomemory/mockneat) and UK Gov (https://github.com/dwp/nino-format-validation) as well as some of our own (for dates and stuff)
- There's an externalised (and overridable) configuration system, based on JSON
- We handle providing JUnit (we use version 5), Selenium and Hamcrest (for writing good assertions)

Coming soon:
- SauceLabs and BrowserStack configuration.
- Published artefacts to Maven Central (so you can get the framework without a GitHub membership).
- Tutorials for writing tests using our toolkit.
- Builds for Java 13 and older versions of Java.
- And lots more marked in the issues list: https://github.com/digital-delivery-academy/selenium-pom-framework/issues

It really is a one stop shop for getting up and running with a robust toolkit for building Selenium tests, without the massive investment that many business make in hand rolling their own.

All of our code is unit or integration tested.

It's highly unlikely that you need anything more complicated than this.  If you do, then you probably have the scope of what you're trying to check a little wrong.

Remember that testing through a browser is brittle, slow and should be at the very top of your automation pyramid.

5-10% of all of your projects automated tests (if you're building bespoke software) should be here.  
If you end up with 500 Selenium tests then it's quite possible that you took a wrong turn.  We'd be happy to talk about getting you back on the right path (get in touch: steve.walton@evoco.co.uk).

If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or 
submit an issue ticket.

## Documentation

- A full reference guide is here: https://github.com/digital-delivery-academy/selenium-pom-framework/wiki
- Technical reference documentation (javadocs) are here: https://digital-delivery-academy.github.io/selenium-pom-framework/javadoc-0.0.8

## Example tests

See https://github.com/digital-delivery-academy/selenium-pom-example repository for an example of how to write Selenium tests using the Page Object Model.  This repository uses this framework.

## Usage

Put this in your POM.xml.

```
<dependency>
  <groupId>uk.co.evoco</groupId>
  <artifactId>selenium-pom-framework</artifactId>
  <version>0.0.8</version>
</dependency>
```

You need to tell Maven how to access GitHub packages.  So you need to edit your `settings.xml`.

Typically you can do this by looking in `~/.m2/settings.xml`.  You will need a GitHub Personal Access Token, which
you can do here (once you're logged in): https://github.com/settings/tokens

An example configuration (`settings.xml`) would be:

```
<settings xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <activeProfiles>
        <activeProfile>github</activeProfile>
    </activeProfiles>

    <profiles>
        <profile>
            <id>github</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>https://repo1.maven.org/maven2</url>
                    <releases>
                        <enabled>true</enabled>
                    </releases>
                    <snapshots>
                        <enabled>false</enabled>
                    </snapshots>
                </repository>
                <repository>
                    <id>github</id>
                    <name>GitHub OWNER Apache Maven Packages</name>
                    <url>https://maven.pkg.github.com/digital-delivery-academy/selenium-pom-framework</url>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <servers>
        <server>
            <id>github</id>
            <username>GITHUB_USERNAME</username>
            <password>GITHUB_PERSONAL_ACCESS_TOKEN_FOR_PACKAGES</password>
        </server>
    </servers>
</settings>
```
