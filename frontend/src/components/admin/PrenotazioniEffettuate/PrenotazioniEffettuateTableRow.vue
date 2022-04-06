<template>
  <tr :class="colors[3]">
    <th
      scope="row"
      class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap"
    >
      {{ props.name }}
    </th>
    <td class="px-6 py-4">{{ props.lastname }}</td>
    <td class="px-6 py-4">{{ props.titolo }}</td>
    <td class="px-6 py-4">{{ props.data }}</td>
    <td class="px-6 py-4">{{ props.ora }}</td>
    <td class="px-6 py-4" v-if="props.status == 1">
      <Button 
        :title="'Disdici'"
        :submitEvent="'DisdiciClicked'"
        @DisdiciClicked="
          $emit(submitDisdiciEvent, idcorso, iddocente, ora, data)
        "
      />
    </td>
    <td class="px-6 py-4" v-if="props.status == 2">EFFETTUATA</td>
    <td class="px-6 py-4" v-if="props.status == 3">DISDETTA</td>
  </tr>
</template>

<script setup>
import { defineProps } from "vue";
import Button from "../../Button.vue";

const colors = {
  1: "bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  2: "bg-yellow border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  3: "bg-red border-b dark:bg-gray-800 dark:border-gray-700 text-center"
}



const props = defineProps({
  idcorso: Number,
  iddocente: Number,
  ora: String,
  data: String,
  name: String,
  status: Number,
  lastname: String,
  titolo: String,
  submitDisdiciEvent: String,
});
</script>
