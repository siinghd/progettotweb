import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./assets/tailwind.css";
import moshaToast from "mosha-vue-toastify";
import "mosha-vue-toastify/dist/style.css";
import VueCookies from "vue3-cookies";
import { VueQueryPlugin } from "vue-query";
createApp(App)
  .use(store)
  .use(router)
  .use(moshaToast)
  .use(VueQueryPlugin)
  .use(VueCookies, { expireTimes: 60 * 60 * 23 })
  .mount("#app");
