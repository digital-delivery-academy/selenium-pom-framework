import Vue from 'vue'
import VueRouter from 'vue-router'
import Introduction from '../views/Introduction.vue'
import Configuration from '../views/Configuration.vue'
import ConfigurationFullReference from '../views/ConfigurationFullReference.vue'
import ConfigurationMetrics from '../views/ConfigurationMetrics.vue'
import ConfigurationBrowserPreferences from '../views/ConfigurationBrowserPreferences.vue'
import ConfigurationBrowserStack from '../views/ConfigurationBrowserStack.vue'
import ConfigurationSauceLabs from '../views/ConfigurationSauceLabs.vue'
import ConfigurationSeleniumGrid from '../views/ConfigurationSeleniumGrid.vue'
import ConfigurationGettingStarted from '../views/ConfigurationGettingStarted.vue'
import Maven from '../views/Maven.vue'
import Contact from '../views/Contact.vue'
import About from '../views/About.vue'
import Javadocs from '../views/Javadocs.vue'
import GettingStarted from '../views/GettingStarted.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Introduction',
    component: Introduction
  },
  {
    path: '/configuration',
    name: 'Configuration',
    component: Configuration
  },
  {
    path: '/configuration/full-reference',
    name: 'ConfigurationFullReference',
    component: ConfigurationFullReference
  },
  {
    path: '/configuration/getting-started',
    name: 'ConfigurationGettingStarted',
    component: ConfigurationGettingStarted
  },
  {
    path: '/configuration/selenium-grid',
    name: 'ConfigurationSeleniumGrid',
    component: ConfigurationSeleniumGrid
  },
  {
    path: '/configuration/saucelabs',
    name: 'ConfigurationSauceLabs',
    component: ConfigurationSauceLabs
  },
  {
    path: '/configuration/browserstack',
    name: 'ConfigurationBrowserStack',
    component: ConfigurationBrowserStack
  },
  {
    path: '/configuration/browser-preferences',
    name: 'ConfigurationBrowserPreferences',
    component: ConfigurationBrowserPreferences
  },
  {
    path: '/configuration/metrics',
    name: 'ConfigurationMetrics',
    component: ConfigurationMetrics
  },
  {
    path: '/maven',
    name: 'Maven',
    component: Maven
  },
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/contact',
    name: 'Contact',
    component: Contact
  },
  {
    path: '/javadocs',
    name: 'Javadocs',
    component: Javadocs
  },
  {
    path: '/getting-started',
    name: 'GettingStarted',
    component: GettingStarted
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
