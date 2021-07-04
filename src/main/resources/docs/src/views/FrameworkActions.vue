<template>
  <div class="configuration">
    <div class="row">
      <div class="col-md-12">
        <h1>Framework Actions</h1>
      </div>
      <div class="col-md-6">
        <p>Framework Actions</p>
        <p>The framework actions that we've created are a robust collection of extended, fault/exception tolerant interactions that are based on the core WebDriver interactions.</p>
        <p><strong>Note: there is absolutely NO NEED to use these actions UNLESS you're encountering instability in your automation.  If you're not, use the base WebDriver implementation.</strong></p>
        <p>The framework is designed to tolerate a list of exceptions that is provided in the configuration file.  If any of these exceptions are encountered during an interaction they will be handled
        and the interaction reattempted periodically until the action is either understood to have been successful by WebDriver or the given or configured timeout is reached.</p>
        <p>This provides tolerant interactions that allow automation of stubborn systems, particularly those which have performance problems, are built on legacy platforms or are incredibly complex (where the
        user interface is constantly updated).  We've found that these interactions are extremely useful when automating COTS products like; PEGA and Seibel.</p>
        <ul>
            <li><router-link to="/framework-actions/clear">Clear</router-link></li>
            <li><router-link to="/framework-actions/click">Click</router-link></li>
            <li><router-link to="/framework-actions/find-by">Find By</router-link></li>
            <li><router-link to="/framework-actions/get-attribute">Get Attribute</router-link></li>
            <li><router-link to="/framework-actions/get-text">Get Text</router-link></li>
            <li><router-link to="/framework-actions/javascript-executor">JavaScript Executor</router-link></li>
            <li><router-link to="/framework-actions/radio-buttons">Radio Buttons</router-link></li>
            <li><router-link to="/framework-actions/select-boxes">Select Boxes</router-link></li>
            <li><router-link to="/framework-actions/send-keys">Send Keys</router-link></li>
            <li><router-link to="/framework-actions/window-utilities">Window Utilities</router-link></li>
            <li><router-link to="/framework-actions/tolerant-exception-handling">Tolerant Exception Handling</router-link></li>
            <li><router-link to="/framework-actions/json-utilities">JSON Utilities</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>
