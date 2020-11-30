<template>
  <div id="app">
    <BootstrapSidebar :initial-show="initialShow" :links="links" :header="header" :fa="true" @sidebarChanged="onSidebarChanged">
      <template v-slot:navbar>
        <b-navbar id="mainNavbar" toggleable="lg" type="light" variant="light" fixed="top">
          <b-navbar-brand href="#" style="color: #0fa0ce; font-weight:300;">evoco</b-navbar-brand>
          <b-navbar-nav class="ml-auto">
            <b-nav-item>
              Selenium POM Framework Docs v1.0.13
            </b-nav-item>
          </b-navbar-nav>
        </b-navbar>
      </template>
      <template v-slot:content>
        <div class="container-fluid" style="margin-top: 56px; margin-left: 36px;">
          <router-view />
        </div>
      </template>
    </BootstrapSidebar>
  </div>
</template>

<script>
  import Vue from 'vue';
  import BootstrapSidebar from 'vue-bootstrap-sidebar';
  export default Vue.extend({
    name: 'App',
    components: {
      BootstrapSidebar
    },
    data () {
      return {
        initialShow: true,
        header: '<h1 class="remove-bottom-margin" style="line-height:36px;">DOCS</h1>',
        links: [
          { name: 'Introduction', href: { name: 'Introduction' }, faIcon: ['fas', 'home'] },
          { name: 'Maven', href: { name: 'Maven' }},
          { name: 'Javadocs', href: { name: 'Javadocs' }},
          { name: 'Getting Started', href: { name: 'GettingStarted' }},
          {
            name: 'Configuration',
            faIcon: ['fas', 'tint'],
            children: [
              {
                name: 'Getting started',
                href: {
                  name: 'ConfigurationGettingStarted'
                },
              },
              {
                name: 'Full Reference',
                href: {
                  name: 'ConfigurationFullReference'
                },
              },
              {
                name: 'Selenium Grid',
                href: {
                  name: 'ConfigurationSeleniumGrid'
                },
              },
              {
                name: 'SauceLabs',
                href: {
                  name: 'ConfigurationSauceLabs'
                },
              },
              {
                name: 'BrowserStack',
                href: {
                  name: 'ConfigurationBrowserStack'
                },
              },
              {
                name: 'Browser Preferences',
                href: {
                  name: 'ConfigurationBrowserPreferences'
                },
              },
              {
                name: 'Metrics',
                href: {
                  name: 'ConfigurationMetrics'
                },
              }
            ]
          },
          { name: 'About', href: { name: 'About' } },
          { name: 'Contact', href: { name: 'Contact' } },
        ]
      }
    },
    methods: {
      onSidebarChanged () {
      }
    }
  })
</script>

<style lang="scss">
@import '~bootstrap';
@import '~bootstrap-vue';
@import '~vue-bootstrap-sidebar/src/scss/default-theme';
body {
  font-size: 16px;
  line-height: 1.6;
  font-weight: 400;
  font-family: Muli,Roboto,sans-serif!important;
  color: #222;
}
h1 {
  font-size: 20px;
  line-height: 1.2;
  font-weight: 900!important;
  text-transform: uppercase;
  color: #16427f!important;
}
h2 {
  font-size:40px;
  font-weight: 700;
  color: #5a5a5a;
}
h3 {
  font-size:28px;
  font-weight: 700;
  color: #5a5a5a;
}
a > div {
  font-weight: 700;
  color: #5a5a5a;
  text-decoration: none;
  font-size:15px;
}
a {
  font-weight: 700;
  color: #0fa0ce;
  text-decoration: none;
  font-size:15px;
}
a:hover, a > div:hover {
  color: #0fa0ce;
}
a:visited, a > div:visited {
  color: #0fa0ce;
}
a:active, a > div:active {
  color: #0fa0ce;
}
.remove-bottom-margin {
  margin-bottom: 0!important;
}
  pre {
    padding: 16px;
    overflow: auto;
    font-size: 85%;
    line-height: 1.45;
    background-color: #f6f8fa;
    border-radius: 3px;
  }
</style>
