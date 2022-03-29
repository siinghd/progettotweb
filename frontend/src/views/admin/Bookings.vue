<template>
  <div>
    <h1>BOOKING</h1>
  </div>
</template>

<script setup>
import { defineEmits, shallowRef } from "vue";
import AdminLayoutVue from "../../layouts/AdminLayout.vue";

const emit = defineEmits(["update:layout"]);
emit("update:layout", shallowRef(AdminLayoutVue));
</script>

<style scoped></style>
