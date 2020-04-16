<template>
    <div class="row">
        <div class="col-md-12">
            <h1 class="remove-bottom-margin">Introduction</h1>
            <h2>A little background</h2>
        </div>
        <div class="col-md-6">
            <p>This toolkit is really a culmination of 10 years of working with teams building automated UI checks with Selenium. We've found time and again that we refactor or rewrite "frameworks" that wrap the entirity of the Selenium API (to little benefit). On top of this, we see so much brittle code that we wanted to put something out there that people could use and benefit from our experience. As a result, this toolkit provides an simple, lightweight (and well structured) way to launch, control and configure checks for Selenium/WebDriver in Java. It's a curation of all of the little libraries and fixes we've used over the years. So if you're at the start of your automation journey or you're just bored of writing the same "framework" over and over again, you're in the right place.</p>
            <p>You'll find no junk (hopefully) here, but it's a fairly opinionated approach:</p>
            <ul>
                <li>There's no mass "wrapper" on Selenium - that's dumb</li>
                <li>There's no Cucumber - Acceptance Test Driven Development is overkill, and we won't help you do it, it's not solving the problem you think it is ;)</li>
                <li>We suggest using the Page Object Model, it's generally a good idea and we provide a starting point for this</li>
                <li>We resolve Driver binaries from WebDriverManager (https://github.com/bonigarcia/webdrivermanager)</li>
                <li>We provide random test data generators from Mockneat (https://github.com/nomemory/mockneat) and UK Gov (https://github.com/dwp/nino-format-validation) as well as some of our own (for dates and stuff)</li>
                <li>There's an externalised (and overridable) configuration system, based on JSON</li>
                <li>We handle providing JUnit (we use version 5), Selenium and Hamcrest (for writing good assertions)</li>
                <li>The framework is instrumented to measure execution time of internals, and these metrics are available via JMX or Graphite.</li>
                <li>Our framework is unit and integration checked as well as monitored for code quality; we treat this as a production code base, not a second class citizen</li>
                <li>We have a (developing) open road map in GitHub Issues (click the Issues tab to see what we have so far)</li>
            </ul>
            <p>Coming soon:</p>
            <ul>
                <li>SauceLabs and BrowserStack configuration (issues #2 and #3)</li>
                <li>Expand documentation and tutorials (issue #45)</li>
                <li>And lots more marked in the issues list: https://github.com/digital-delivery-academy/selenium-pom-framework/issues</li>
            </ul>
            <p>It really is a one stop shop for getting up and running with a robust toolkit for building Selenium checks, without the massive investment that many business make in hand rolling their own.</p>
            <p>All of our code is unit or integration checked.</p>
            <p>It's highly unlikely that you need anything more complicated than this. If you do, then you probably have the scope of what you're trying to check a little wrong.</p>
            <p>Remember that checking through a browser is brittle, slow and should be at the very top of your automation pyramid.</p>
            <p>5-10% of all of your projects automated checks (if you're building bespoke software) should be here.</p>
            <p>If you end up with 500 Selenium checks then it's quite possible that you took a wrong turn. We'd be happy to talk about getting you back on the right path (get in touch: steve.walton@evoco.co.uk).</p>
            <p>If you have questions please feel free to contact me (steve.walton@evoco.co.uk) or if you find issues raise a PR or submit an issue ticket.</p>
        </div>
    </div>
</template>

<script>
export default {
  name: 'Introduction',
  components: {

  }
}
</script>
