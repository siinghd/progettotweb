<template>
  <div>
    <nav class="bg-gray-800 border-b-2">
      <div class="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
        <div class="relative flex items-center justify-between h-16">
          <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
            <!-- Mobile menu button-->
            <button
              type="button"
              class="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
              aria-controls="mobile-menu"
              aria-expanded="false"
              @click="toggleMobileNavBar"
            >
              <span class="sr-only">Apri il menu</span>
              <svg
                class="block h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                aria-hidden="true"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
              <svg
                class="hidden h-6 w-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                aria-hidden="true"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>
          <div
            class="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start"
          >
            <div class="flex-shrink-0 flex items-center">
              <img
                class="block lg:hidden h-8 w-auto"
                src="../assets/logos/logo.png"
                alt="Workflow"
              />
              <img
                class="hidden lg:block h-8 w-auto"
                src="../assets/logos/logo.png"
                alt="Workflow"
              />
            </div>
            <div class="hidden sm:block sm:ml-6">
              <div class="flex space-x-4">
                <router-link
                  to="/common/bookings"
                  class="text-white px-3 py-2 rounded-md text-sm font-medium"
                  aria-current="page"
                  >Prenotazioni Disponibili</router-link
                >
              </div>
            </div>
          </div>
          <div
            class="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0"
          >
            <button
              @click="this.$router.push('/')"
              class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium"
            >
              Torna al login
            </button>
          </div>
        </div>
      </div>

      <div
        :class="{ hidden: isMobileOpen, 'sm:hidden': true }"
        id="mobile-menu"
      >
        <div class="px-2 pt-2 pb-3 space-y-1">
          <router-link
            to="/common/bookings"
            class="text-white block px-3 py-2 rounded-md text-base font-medium"
            aria-current="page"
            >Prenotazioni Disponibili</router-link
          >
        </div>
      </div>
    </nav>
    <div class="mt-6 flex justify-center mb-6">
      <slot v-if="!loading" /> <Loading v-if="loading" />
    </div>
  </div>
</template>

<script>
import Loading from "../components/Loading.vue";
import { genericGet } from "../utilities/requests";
export default {
  components: {
    Loading,
  },
  data() {
    return {
      isMobileOpen: true,
      isActive: 0,
      loading: false,
    };
  },

  methods: {
    toggleMobileNavBar: function () {
      this.isMobileOpen = !this.isMobileOpen;
    },
    signout: function () {
      this.loading = true;
      genericGet(`${process.env.VUE_APP_BACKEND_URL}/api/signout`).then(
        (res) => {
          this.loading = false;
          if (res.status === "fail") {
            this.$moshaToast(res.message, { type: "danger" });
          } else {
            this.$cookies.remove("servletrole");
            this.$moshaToast(res.message, { type: "success" });
            this.$router.push("/");
          }
        }
      );
    },
  },
};
</script>
