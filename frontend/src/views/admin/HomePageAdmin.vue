<template>
  <div>Hello world</div>
  <div>Hello world</div>
  <div>Hello world</div>
  <div>Hello world</div>
  <div>Hello wosdgsdgrld</div>
  <div>Hello world</div>
</template>
<script setup>
import { defineEmits, shallowRef } from "vue";
import AdminLayoutVue from "../../layouts/AdminLayout.vue";

const emit = defineEmits(["update:layout"]);
emit("update:layout", shallowRef(AdminLayoutVue));
</script>
