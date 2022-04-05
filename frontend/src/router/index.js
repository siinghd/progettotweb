import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import HomePageAdmin from "../views/admin/HomePageAdmin.vue";
import Courses from "../views/admin/Courses.vue";
import Bookings from "../views/admin/Bookings.vue";
import Professors from "../views/admin/Professors.vue";
import Subjects from "../views/admin/Subjects.vue";
import PrenotazioniEffettuate from "../views/admin/PrenotazioniEffettuate.vue";

import HomePageUser from "../views/user/HomePageUser.vue";
import { useCookies } from "vue3-cookies";
const routes = [
  {
    path: "/",
    name: "Login",
    component: Login,
    meta: {
      auth: false,
    },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: {
      auth: false,
    },
  },
  {
    path: "/auth/admin",
    name: "HomePageAdmin",
    component: HomePageAdmin,
    meta: {
      auth: true,
      role: 2,
    },
  },
  {
    path: "/auth/admin/courses",
    name: "Courses",
    component: Courses,
    meta: {
      auth: true,
      role: 2,
    },
  },
  {
    path: "/auth/admin/bookings",
    name: "Bookings",
    component: Bookings,
    meta: {
      auth: true,
      role: 2,
    },
  },
  {
    path: "/auth/admin/PrenotazioniEffettuate",
    name: "PrenotazioniDisponibili",
    component: PrenotazioniEffettuate,
    meta: {
      auth: true,
      role: 2,
    },
  },
  {
    path: "/auth/admin/subjects",
    name: "Subjects",
    component: Subjects,
    meta: {
      auth: true,
      role: 2,
    },
  },

  {
    path: "/auth/admin/professor",
    name: "Professors",
    component: Professors,
    meta: {
      auth: true,
      role: 2,
    },
  },
  {
    path: "/auth/user",
    name: "HomePageUser",
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
  const cookie = useCookies().cookies.get("servletrole");
  if (cookie) {
    if (to.meta.auth) {
      if (to.meta.role === parseInt(cookie, 10) && to.path.includes("admin")) {
        next();
      } else if (
        to.meta.role === parseInt(cookie, 10) &&
        to.path.includes("user")
      ) {
        next();
      } else {
        if (parseInt(cookie, 10) === 2) {
          next("/auth/admin");
        } else {
          next("/auth/user");
        }
      }
    } else {
      if (parseInt(cookie, 10) === 2) {
        next("/auth/admin");
      } else {
        next("/auth/user");
      }
    }
  } else {
    if (to.path !== "/" && to.path !== "/register") {
      next("/");
    } else {
      next();
    }
  }
});
export default router;
