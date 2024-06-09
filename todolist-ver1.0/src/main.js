import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import { createPinia } from 'pinia'
import router from './router/index'

const app = createApp(App)

loadFonts()

app.use(vuetify)
app.use(createPinia())
app.use(router)
app.mount('#app')


