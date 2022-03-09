import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './assets/tailwind.css';
import moshaToast from 'mosha-vue-toastify';
import 'mosha-vue-toastify/dist/style.css';
import VueCookies from 'vue3-cookies';
createApp(App)
  .use(store)
  .use(router)
  .use(moshaToast)
  .use(VueCookies, { expireTimes: 1800 })
  .mount('#app');
