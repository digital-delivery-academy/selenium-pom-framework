<template>
  <div class="configuration">
    <div class="row">
      <div class="col-md-12">
        <h1 class="remove-bottom-margin">Configuration</h1>
        <h2>Full Reference</h2>
      </div>
      <div class="col-md-8">
        <b-table striped hover :items="items" :fields="fields"></b-table>
        <h3>Sample JSON configuration</h3>
        <pre>
          <code>
{
  "browser": "chrome",
  "baseUrl": "https://www.google.com",
  "timeout": "30",
  "headless": true,
  "runType": "LOCAL",
  "testConfig": {
    "sample": "sample text"
  },
  "gridConfig": {
    "url": "http://localhost:4444/wd/hub"
  },
  "browserPreferences": {
    "firefox": {
      "browser.download.defaultFolder": "downloads/reports",
      "pdfjs.disabled": true,
      "browser.download.folderList": 2
    }
  },
  "tolerantActionExceptions": {
    "waitTimeoutInSeconds": 5,
    "exceptionsToHandle": [
      "StaleElementReferenceException",
      "ElementClickInterceptedException",
      "ElementNotInteractableException"
    ]
  },
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
}
          </code>
        </pre>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ConfigurationFullReference',
    data() {
      return {
        fields: ['option', 'description', 'type', 'value', 'default', 'required'],
        items: [
          { isActive: true, option: 'browser', description: 'Target which browser you would like tests to run in', type: 'String', value: 'One of: chrome, firefox, safari, edge', default: 'N/A', required: true },
          { isActive: true, option: 'baseUrl', description: 'The starting URL for all tests', type: 'String (URL)', value: 'Valid URL e.g. https://www.google.com', default: 'N/A', required: true },
          { isActive: true, option: 'timeout', description: 'Default test timeout', type: 'String (integer)', value: 'Number of seconds as an integer e.g. 30', default: 'Suggested to be 30', required: true },
          { isActive: true, option: 'headless', description: 'Use browsers headless mode', type: 'Boolean', value: 'true or false', default: 'Suggested to be true', required: true },
          { isActive: true, option: 'runType', description: 'Toggle between execution types', type: 'Enum', value: 'One of: LOCAL or GRID', default: 'N/A', required: true },
          { isActive: true, option: 'testConfig', description: 'Object of user defined variables', type: 'Object', value: 'Any key value pairs that are defined by the user', default: 'N/A', required: 'true, but empty if unused' },
          { isActive: true, option: 'gridConfig', description: 'Object of values to configure a Selenium Grid', type: 'Object', value: 'key value pair for url', default: 'N/A', required: false },
          { isActive: true, option: 'browserPreferences', description: 'Object of values to configure a Browser specific preferences', type: 'Object', value: 'Object', default: 'N/A', required: false },
          { isActive: true, option: 'tolerantActionExceptions', description: 'Object of values and objects to configure tolerance of exceptions', type: 'Object', value: 'Object', default: 'N/A', required: false },
          { isActive: true, option: 'metrics', description: 'Object of values and objects to configure instrumentation, metrics and reporting', type: 'Object', value: 'Object', default: 'N/A', required: true },
        ]
      }
    }
  }
</script>
