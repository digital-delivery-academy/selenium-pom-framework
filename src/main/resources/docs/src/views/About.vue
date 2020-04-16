<template>
    <div class="row">
        <div class="col-md-12">
            <h1 class="remove-bottom-margin">About</h1>
            <h2>Selenium POM Framework</h2>
        </div>
        <div class="col-md-6">
            <b-table striped hover :items="items" :fields="fields"></b-table>
        </div>
    </div>
</template>

<script>
export default {
  name: 'About',
    data() {
        return {
            fields: ['attribute', 'value', 'link'],
            items: [
                { isActive: true, attribute: 'License', value: 'MIT', link: 'https://github.com/digital-delivery-academy/selenium-pom-framework/blob/master/LICENSE' },
                { isActive: true, attribute: 'Issues', value: 'GitHub Issues', link: 'https://github.com/digital-delivery-academy/selenium-pom-framework/issues' },
                { isActive: true, attribute: 'Latest Version', value: '1.0.9', link: 'https://github.com/digital-delivery-academy/selenium-pom-framework/releases' },
            ]
        }
    }
}
</script>
