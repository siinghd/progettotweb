import { createRouter, createWebHistory } from "vue-router";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import HomePageAdmin from "../views/admin/HomePageAdmin.vue";
import AllBookingsFromUsers from "../views/admin/AllBookingsFromUsers.vue";
import Courses from "../views/admin/Courses.vue";
import Bookings from "../views/common/Bookings.vue";
import Professors from "../views/admin/Professors.vue";
import Subjects from "../views/admin/Subjects.vue";
import PrenotazioniEffettuate from "../views/common/PrenotazioniEffettuate.vue";

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
      role: [2],
    },
  },
  {
    path: "/auth/admin/courses",
    name: "Courses",
    component: Courses,
    meta: {
      auth: true,
      role: [2],
    },
  },
  {
    path: "/auth/common/bookings",
    name: "Bookings",
    component: Bookings,
    meta: {
      auth: true,
      role: [1, 2],
    },
  },
  {
    path: "/auth/common/prenotazionieffettuate",
    name: "PrenotazioniDisponibili",
    component: PrenotazioniEffettuate,
    meta: {
      auth: true,
      role: [1, 2],
    },
  },
  {
    path: "/auth/admin/allbookings",
    name: "AllBookings",
    component: AllBookingsFromUsers,
    meta: {
      auth: true,
      role: [2],
    },
  },
  {
    path: "/auth/admin/subjects",
    name: "Subjects",
    component: Subjects,
    meta: {
      auth: true,
      role: [2],
    },
  },

  {
    path: "/auth/admin/professor",
    name: "Professors",
    component: Professors,
    meta: {
      auth: true,
      role: [2],
    },
  },
  {
    path: "/auth/user",
    name: "HomePageUser",
    component: HomePageUser,
    meta: {
      auth: true,
      role: [1],
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
      if (
        (to.meta.role.includes(parseInt(cookie, 10)) &&
          to.path.includes("admin")) ||
        to.path.includes("common")
      ) {
        next();
      } else if (
        (to.meta.role.includes(parseInt(cookie, 10)) &&
          to.path.includes("user")) ||
        to.path.includes("common")
      ) {
        next();
      } else {
        next("/auth/common/bookings");
        /* if (parseInt(cookie, 10) === 2) {
          next('/auth/common/bookings');
        } else {
          next('/auth/user');
        } */
      }
    } else {
      next("/auth/common/bookings");
      /* if (parseInt(cookie, 10) === 2) {
        next('/auth/common/bookings');
      } else {
        next('/auth/user');
      } */
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
