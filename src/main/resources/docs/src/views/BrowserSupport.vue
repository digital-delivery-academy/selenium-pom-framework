<template>
  <div class="configuration">
    <div class="row">
      <div class="col-md-12">
        <h1 class="remove-bottom-margin">Browser Support</h1>
        <h2>SauceLabs, Local and working with Safari on MacOS</h2>
      </div>
      <div class="col-md-6">
        <p>We support SauceLabs, running against your own Grid or running "locally" on your machine or in a CI environment.</p>
        <p>Driver binaries are supplied by the WebDriverManager which is internal to the framework, so you never need to deal with downloading your own driver binaries and keeping them up to date.</p>
        <p>The WebDriverManager detects the version of the browsers that you have installed and brings down the binaries to fit the versions you're running.  There's currently no way to override this and provide your own.</p>
        <p>If you want to use Safari on MacOs, then you need to enable Safaris Automation Control mode.  See Apples documentation here: <a href="https://developer.apple.com/documentation/webkit/testing_with_webdriver_in_safari">Running WebDriver with Safari on macOS</a></p>
        <p>Our project is supported by SauceLabs.  We support all of the browsers and platforms that SauceLabs does.  Please see the SauceLabs tab for more details of how to configure your tests to run with SauceLabs.</p>
      </div>
    </div>
  </div>
</template>
