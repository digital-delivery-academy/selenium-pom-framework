<template>
  <div class="maven">
    <div class="row">
      <div class="col-md-12">
        <h1 class="remove-bottom-margin">Maven</h1>
        <h2>Getting started</h2>
      </div>
      <div class="col-md-6">
        <p>The project is published to Maven Central, so there's no special configuration required to use the framework with your tests.  Simply add the below to your POM.</p>
        <pre><code>&lt;dependency>
  &lt;groupId&gt;uk.co.evoco&lt;/groupId&gt;
  &lt;artifactId&gt;selenium-pom-framework&lt;/artifactId&gt;
  &lt;version&gt;1.0.9&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
        <p>By pulling in this dependency you will get:</p>
        <ul>
            <li>Selenium (latest)</li>
            <li>WebDriver Manager (latest)</li>
            <li>Junit (version 5)</li>
            <li>Hamcrest matchers</li>
            <li>Mockneat random/sample test data generator (latest)</li>
            <li>NINO format validation (latest from DWP)</li>
        </ul>
      </div>
    </div>
  </div>
</template>
