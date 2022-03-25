<template>
  <tr
    class="bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-center"
  >
    <th
      scope="row"
      class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap"
    >
      {{ props.name }}
    </th>
    <td class="px-6 py-4">{{ props.lastname }}</td>
    <td class="px-6 py-4">
      <Button
        :title="'Elimina'"
        :submitEvent="'deleteClicked'"
        @deleteClicked="$emit(deleteEvent, id)"
      />
    </td>
  </tr>
</template>

<script setup>
import { defineProps } from 'vue';
import Button from '../../Button.vue';

const props = defineProps({
  id: Number,
  name: String,
  lastname: String,
  deleteEvent: String,
});
</script>
