<template>
  <button
    @click="$emit(props.submitEvent)"
    class="shadow bg-gray-800 hover:bg-gray-700 focus:shadow-outline focus:outline-none text-white font-bold py-2 px-4 rounded"
    type="button"
  >
    {{ props.title }}
  </button>
</template>
<script setup>
import { defineProps } from "vue";
const props = defineProps({
  title: String,
  submitEvent: String,
});
</script>
