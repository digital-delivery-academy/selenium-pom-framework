# selenium-pom-framework

[![Build Status](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework.svg?branch=master)](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/122f56e1b6284b319b8c23a58ab2c664)](https://www.codacy.com/gh/digital-delivery-academy/selenium-pom-example?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=digital-delivery-academy/selenium-pom-example&amp;utm_campaign=Badge_Grade)

This "framework" (don't really like that word) provides an simple, lightweight (and well structured) way to launch,
control and extend (loosely) tests for Selenium/WebDriver in Java.

It is also a collection of useful libraries to get things going (like getting hold of random data for tests) and a few
utility helpers (NOT wrappers) that can support some more complicated tasks that we find are fairly common.

You'll find no junk (hopefully) here, and it's a fairly opinionated approach:
- There's no "wrappers" on Selenium - that's dumb
- There's no Cucumber - Acceptance Test Driven Development is overkill
- We expose BasePageObjects - Page Object Model is generally a good idea
- We resolve Driver binaries from WebDriverManager (because Boni Garcias library is cool)

This boilerplate is entirely scalable.  Use it as your base and build what you need.

It's highly unlikely that you need anything more complicated than this.  
If you do, then you probably have the scope of what you're trying to check a little wrong.

Remember that testing through a browser is brittle, slow and should be at the very top of your automation pyramid.

5-10% of all of your projects automated tests (if you're building bespoke software) should be here.  
If you end up with 500 Selenium tests
then you took a wrong turn.  A really wrong turn.  Or you're trying to check some COTS product.  Good luck to ya.

If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or 
submit an issue ticket.

## Usage

Put this in your POM.xml.

```
<dependency>
  <groupId>uk.co.evoco</groupId>
  <artifactId>selenium-pom-framework</artifactId>
  <version>0.0.1</version>
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

See the Documentation in the Wiki to see how to write a test.

## Documentation

Documentation for committers and users is here: https://github.com/digital-delivery-academy/selenium-pom-framework/wiki
