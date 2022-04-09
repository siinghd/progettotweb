<template>
  <div class="container flex justify-center mx-auto p-2">
    <ul class="flex">
      <li>
        <button
          v-if="props.currPage > 0"
          @click="$emit('prevPage')"
          class="h-10 px-5 text-gray-600 bg-white border border-r-0 border-gray-600 hover:bg-gray-100"
        >
          {{ Math.max(0, props.currPage - 1) }}
        </button>
      </li>
      <li>
        <button
          class="h-10 px-5 text-white bg-gray-600 border border-r-0 border-gray-600"
        >
          {{ props.currPage }}
        </button>
      </li>
      <li>
        <button
          @click="$emit('nextPage')"
          v-if="limit === props.dataLength"
          class="h-10 px-5 text-gray-600 bg-white border border-r-0 border-gray-600 hover:bg-gray-100"
        >
          {{ props.currPage + 1 }}
        </button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  currPage: Number,
  dataLength: Number,
  limit: Number,
});
</script>
