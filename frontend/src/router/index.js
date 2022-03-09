import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import HomePageAdmin from '../views/admin/HomePageAdmin.vue';
import HomePageUser from '../views/user/HomePageUser.vue';
import { useCookies } from 'vue3-cookies';
const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      auth: false,
    },
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: {
      auth: false,
    },
  },
  {
    path: '/auth/admin',
    name: 'HomePageAdmin',
    component: HomePageAdmin,
    meta: {
      auth: true,
      role: 3,
    },
  },
  {
    path: '/auth/user',
    name: 'HomePageUser',
    component: HomePageUser,
    meta: {
      auth: true,
      role: 1,
    },
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});
router.beforeEach((to, from, next) => {
  const cookie = useCookies().cookies.get('servletrole');
  if (cookie) {
    if (to.meta.auth) {
      if (to.meta.role === parseInt(cookie, 10) && to.path.includes('admin')) {
        next();
      } else if (
        to.meta.role === parseInt(cookie, 10) &&
        to.path.includes('user')
      ) {
        next();
      } else {
        if (parseInt(cookie, 10) === 3) {
          next('/auth/admin');
        } else {
          next('/auth/user');
        }
      }
    } else {
      if (parseInt(cookie, 10) === 3) {
        next('/auth/admin');
      } else {
        next('/auth/user');
      }
    }
  } else {
    if(to.path !== '/' && to.path !== '/register'){
      next("/");
    }
    next();
  }
});
export default router;
