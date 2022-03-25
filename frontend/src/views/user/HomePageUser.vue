<template>
  <div>
    <h1 class="title text-center">hello world</h1>
  </div>
</template>

<script>
import UserLayout from "../../layouts/UserLayout.vue";

export default {
  created() {
    this.$emit("update:layout", UserLayout);
  },
};
</script>
