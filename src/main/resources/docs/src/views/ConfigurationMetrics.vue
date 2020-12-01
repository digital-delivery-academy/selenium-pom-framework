<template>
  <div class="configuration">
    <div class="row">
      <div class="col-md-12">
        <h1 class="remove-bottom-margin">Configuration</h1>
        <h2>Metrics</h2>
      </div>
      <div class="col-md-8">
        <b-table striped hover :items="items" :fields="fields"></b-table>
        <h3>Sample JSON configuration</h3>
        <pre>
          <code>
{
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
          </code>
        </pre>
      </div>
    </div>
    <div class="row">
        <div class="col-md-8">
            <h3>Graphite settings</h3>
            <b-table striped hover :items="graphiteItems" :fields="fields"></b-table>
        </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'ConfigurationMetrics',
    data() {
      return {
        fields: ['option', 'description', 'type', 'value', 'default', 'required'],
        items: [
          { isActive: true, option: 'jmx', description: 'Enables publication of metrics via JMX', type: 'Boolean', value: 'One of: true, false', default: 'false', required: true },
          { isActive: true, option: 'graphite', description: 'Settings for publication of metrics to Graphite', type: 'Object', value: 'Object', default: 'N/A', required: true },
        ],
        graphiteItems: [
          { isActive: true, option: 'enabled', description: 'Toggles Graphite reporting on and off', type: 'Boolean', value: 'One of: true, false', default: 'false', required: true },
          { isActive: true, option: 'host', description: 'Host for the Graphite instance that you\'d like to push to', type: 'String', value: 'String', default: 'localhost', required: true },
          { isActive: true, option: 'port', description: 'Port for the Graphite instance that you\'d like to push to', type: 'String', value: 'String', default: '2003', required: true },
        ]
      }
    }
  }
</script>
