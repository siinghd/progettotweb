<template>
  <div class="flex items-center justify-center min-h-screen bg-gray-100">
    <div class="px-8 py-6 mt-4 text-left bg-white shadow-lg">
      <div class="flex justify-center">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-20 h-20 text-blue-600"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path d="M12 14l9-5-9-5-9 5 9 5z" />
          <path
            d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z"
          />
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222"
          />
        </svg>
      </div>
      <h3 class="text-2xl font-bold text-center">Registrati al portale</h3>
      <p class="bg-indigo-500" disabled v-if="loading">
        <svg class="animate-spin h-5 w-5 mr-3 ..." viewBox="0 0 24 24"></svg>
        loading...
      </p>
      <form @submit.prevent="handleSubmit" v-if="!loading">
        <div class="mt-4">
          <div>
            <label class="block" for="email">Email</label>
            <input
              type="email"
              placeholder="Email"
              required
              class="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600"
              v-model="email"
            />
            <span class="text-xs tracking-wide text-red-600"
              >Email e' obbligatoria
            </span>
          </div>
          <div class="mt-4">
            <label class="block">Password</label>
            <input
              type="password"
              placeholder="Password"
              required
              v-model="password"
              class="w-full px-4 py-2 mt-2 border rounded-md focus:outline-none focus:ring-1 focus:ring-blue-600"
            />
          </div>
          <div class="flex items-baseline justify-between">
            <button
              class="px-6 py-2 mt-4 text-white bg-blue-600 rounded-lg hover:bg-blue-900"
            >
              Registrati
            </button>
            <router-link
              class="px-6 py-2 mt-4 text-white bg-blue-600 rounded-lg hover:bg-blue-900"
              to="/"
              >Accedi</router-link
            >
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import AuthLayoutVue from '../layouts/AuthLayout.vue';
import { genericPost } from '../utilities/requests';
export default {
  data() {
    return {
      email: '',
      password: '',
      loading: false,
    };
  },
  created() {
    this.$emit('update:layout', AuthLayoutVue);
  },
  methods: {
    handleSubmit() {
      this.loading = true;
      genericPost(
        `${process.env.VUE_APP_BACKEND_URL}/api/register`,
        `email=${this.email}&password=${this.password}`
      ).then((res) => {
        this.loading = false;
        if (res.status === 'fail') {
          this.$moshaToast(res.message, { type: 'danger' });
        } else {
          this.$moshaToast(res.message, { type: 'success' });
          setTimeout(() => {
            this.$router.push('/');
          }, 2000);
        }
      });
    },
  },
};
</script>
