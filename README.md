# selenium-pom-framework
[![Build Status](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework.svg?branch=master)](https://travis-ci.com/digital-delivery-academy/selenium-pom-framework)
[![GitHub Issues badge](https://img.shields.io/github/issues-raw/digital-delivery-academy/selenium-pom-framework?color=green)](https://github.com/digital-delivery-academy/selenium-pom-framework/issues)
[![All Contributors](https://img.shields.io/badge/all_contributors-3-orange.svg?style=flat-square)](#contributors)

[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=digital-delivery-academy_selenium-pom-example&metric=coverage)](https://sonarcloud.io/dashboard?id=digital-delivery-academy_selenium-pom-example)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=digital-delivery-academy_selenium-pom-example&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=digital-delivery-academy_selenium-pom-example)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=digital-delivery-academy_selenium-pom-example&metric=security_rating)](https://sonarcloud.io/dashboard?id=digital-delivery-academy_selenium-pom-example)
[![Sauce Test Status](https://saucelabs.com/buildstatus/stevewalton)](https://app.saucelabs.com/u/stevewalton)

[![Latest version badge](https://img.shields.io/github/v/release/digital-delivery-academy/selenium-pom-framework)](https://github.com/digital-delivery-academy/selenium-pom-framework/releases)
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<!-- ALL-CONTRIBUTORS-BADGE:END -->

**We've released version 1.0.0! And we've moved to Maven Central hosting and added instrumentation**

This toolkit is really a culmination of 10 years of working with teams building automated UI checks with Selenium.  We've found time and again that we refactor or rewrite "frameworks" that wrap the entirety of the Selenium API (to little benefit).  On top of this, we see so much brittle code that we wanted to put something out there that people could use and benefit from our experience.  As a result, this toolkit provides an simple, lightweight (and well structured) way to launch, control and configure checks for Selenium/WebDriver in Java.  It's a curation of all of the little libraries and fixes we've used over the years.  So if you're at the start of your automation journey or you're just bored of writing the same "framework" over and over again, you're in the right place.

You'll find no junk (hopefully) here, but it's a fairly opinionated approach:
- There's no mass "wrapper" on Selenium - that's dumb
- We suggest using the Page Object Model, it's generally a good idea, and we provide a starting point for this
- We resolve Driver binaries from WebDriverManager (https://github.com/bonigarcia/webdrivermanager)
- We provide random test data generators from Mockneat (https://github.com/nomemory/mockneat) and UK Gov (https://github.com/dwp/nino-format-validation) as well as some of our own (for dates and stuff)
- There's an externalised (and overridable) configuration system, based on JSON
- We handle providing JUnit (we use version 5), Selenium and Hamcrest (for writing good assertions)
- The framework is instrumented to measure execution time of internals, and these metrics are available via JMX or Graphite.
- Our framework is unit and integration checked as well as monitored for code quality; we treat this as a production code base, not a second class citizen.
- We have a (developing) open road map in GitHub Issues (click the Issues tab to see what we have so far)
- We have a fuller set of documentation here: https://digital-delivery-academy.github.io/selenium-pom-framework/index.html

Coming soon:
- SauceLabs and BrowserStack configuration (issues #2 and #3).
- And lots more marked in the issues list: https://github.com/digital-delivery-academy/selenium-pom-framework/issues

It really is a one stop shop for getting up and running with a robust toolkit for building Selenium checks, without the massive investment that many business make in hand rolling their own.

All of our code is unit or integration checked.

It's highly unlikely that you need anything more complicated than this.  If you do, then you probably have the scope of what you're trying to check a little wrong.

Remember that checking through a browser is brittle, slow and should be at the very top of your automation pyramid.

5-10% of all of your projects automated checks (if you're building bespoke software) should be here.  
If you end up with 500 Selenium checks then it's quite possible that you took a wrong turn.  We'd be happy to talk about getting you back on the right path (get in touch: steve.walton@evoco.co.uk).

If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or 
submit an issue ticket.

## Documentation

- A full reference guide is here: https://digital-delivery-academy.github.io/selenium-pom-framework/index.html
- Technical reference documentation (javadocs) are here: https://digital-delivery-academy.github.io/selenium-pom-framework/javadoc-1.0.13

## Example checks/project

See https://github.com/digital-delivery-academy/selenium-pom-example repository for an example of how to write Selenium checks using the Page Object Model.  This repository uses this framework.

## Usage

This project is published to Maven Central, so you just need to put this in your `pom.xml`.

```
<dependency>
  <groupId>uk.co.evoco</groupId>
  <artifactId>selenium-pom-framework</artifactId>
  <version>1.0.13</version>
</dependency>
```

**Note:** we used to publish releases to GitHub packages (which you can see if you click Packages above).  This was restrictive because people HAD to have a GitHub account to use it, and had to mess around with their M2 settings.  Maven Central was the right answer to this problem.
- You can see historic releases in the Packages tab above
- You can see all new releases on Maven Central here: https://search.maven.org/artifact/uk.co.evoco/selenium-pom-framework (we'll keep the Releases tab updated on this page, and we'll keep the latest version details in the README)

We skipped through a few minor versions setting up the Maven Central release, so there is no effective gap between 1.0.2 and 1.0.7.

## Instrumentation and metrics
The framework is instrumented with Dropwizard Metrics which has two reporters; JMX and Graphite.

To view the JMX reports, the JVM still needs to be running.  You can access by running from a command line `jconsole` and then connecting to the JVM that is running the tests.
From `jconsole` you will need to open `MBeans` and then `metrics` to see the metrics that are being produced.

**Note:** We're probably going to remove JMX in coming releases because the nature of the framework is that it runs and then exits.  When the container closes, the metrics go with it (unless we add something like Graphite to back it).  We'll see.  It'll be fine during debugging though.  We'd recommend looking at the next bit about Graphite and Grafana.

Graphite is a much more powerful way to see performance of the framework over time, and to see where your tests are spending the most time.  Here's some instructions about how to get started using Docker.

**Note:** Obviously you have to have `docker` installed.  I'm sure you can Google that :)

We're going to setup `docker` containers for `graphite` which is essentially a cool way to track metrics over time and `grafana` which is a sexy way to visualise things and create some dashboards.  We need to link these two containers so that they can access each other
and we're going to use the official containers from Graphite and Grafana themselves to get the latest versions.  First up, we need a `docker` network:

`docker network create graphite_grafana`

Then we need to grab `graphite`:

```
docker run -d \
 --name graphite \
 --restart=always \
 --net graphite_grafana \
 -p 80:80 \
 -p 2003-2004:2003-2004 \
 -p 2023-2024:2023-2024 \
 -p 8125:8125/udp \
 -p 8126:8126 \
 graphiteapp/graphite-statsd
```

And finally we need to grab `grafana`:

`docker run -d --name=grafana -p 3000:3000 --net graphite_grafana grafana/grafana`

Once all of these commands have finished if you run `docker ps` you should end up with something like the following:

```
CONTAINER ID        IMAGE                         COMMAND             CREATED             STATUS              PORTS                                                                                                                                                                       NAMES
acbce542bd1f        graphiteapp/graphite-statsd   "/entrypoint"       4 seconds ago       Up 4 seconds        0.0.0.0:80->80/tcp, 0.0.0.0:2003-2004->2003-2004/tcp, 2013-2014/tcp, 8080/tcp, 0.0.0.0:2023-2024->2023-2024/tcp, 0.0.0.0:8126->8126/tcp, 8125/tcp, 0.0.0.0:8125->8125/udp   graphite
ed8a7fcdbfc3        grafana/grafana               "/run.sh"           2 minutes ago       Up 2 minutes        0.0.0.0:3000->3000/tcp                                                                                                                                                      grafana
```

Now open up a browser window and get the two web interfaces loaded up:

Graphite: http://localhost:80

Grafana: http://localhost:3000

Grafana is where we're going to do most of our work now.  Let's set up the data source first.  From the home page in Grafana click "Add data source".
Then select "Graphite" from the list of sources.

Give an appropriate name (selenium-pom-framework for example).  The following fields and values follow:

```
URL: http://graphite:80
```

Click `Save & Test`.  Everything should go green, and Graphite and Grafana should now be connected.  Now you can run tests and all of the metrics will be put out to Graphite and you can create dashboards to see
where your tests are going slowly.

There's a sample dashboard in this repo in `grafana-dashboard-examples/sample-dashboard.json` that you can import to get started.

If you're deploying this else where (i.e. not locally) you can configure connection details in the configuration object (see below).

```
"metrics": {
    "jmx": {
        "enabled": false
    },
    "graphite": {
        "enabled": true,
        "host": "localhost",
        "port": 2003
    }
}
```

## References/thanks
[![Testing Powered By SauceLabs](https://opensource.saucelabs.com/images/opensauce/powered-by-saucelabs-badge-gray.png?sanitize=true "Testing Powered By SauceLabs")](https://saucelabs.com)

We're using repositories from the guys below to provide some of our features:
- https://github.com/bonigarcia/webdrivermanager from Boni Garcia (for getting WebDriver binaries in a lovely way)
- https://github.com/nomemory/mockneat from nomemory (for generating random test data easily)
- https://github.com/dwp/nino-format-validation from UK Gov. Department for Work and Pensions (for checking Ninos)

## Contributors
<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->
<table>
  <tr>
    <td align="center"><a href="https://www.evoco.co.uk"><img src="https://avatars2.githubusercontent.com/u/8415117?v=4" width="100px;" alt=""/><br /><sub><b>Steve Walton</b></sub></a><br /><a href="https://github.com/digital-delivery-academy/selenium-pom-framework/commits?author=stevewalton28" title="Code">💻</a></td>
<td align="center"><a href="https://github.com/akwanashie"><img src="https://avatars2.githubusercontent.com/u/4432756?v=4" width="100px;" alt=""/><br /><sub><b>Augustine K</b></sub></a><br /><a href="https://github.com/digital-delivery-academy/selenium-pom-framework/commits?author=akwanashie" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/balu836"><img src="https://avatars2.githubusercontent.com/u/33576087?v=4" width="100px;" alt=""/><br /><sub><b>balu836</b></sub></a><br /><a href="https://github.com/digital-delivery-academy/selenium-pom-framework/commits?author=balu836" title="Code">💻</a></td>
  </tr>
</table>
<!-- markdownlint-enable -->
<!-- prettier-ignore-end -->
<!-- ALL-CONTRIBUTORS-LIST:END -->
