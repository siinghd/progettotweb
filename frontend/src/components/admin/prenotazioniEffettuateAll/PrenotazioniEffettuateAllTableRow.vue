<template>
  <tr :class="colors[props.status]">
    <th
      scope="row"
      class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap"
    >
      {{ props.accountname }}
    </th>

    <td class="px-6 py-4">{{ props.name }}</td>
    <td class="px-6 py-4">{{ props.lastname }}</td>
    <td class="px-6 py-4">{{ props.titolo }}</td>
    <td class="px-6 py-4">{{ dayjs(props.data).format("DD-MM-YYYY") }}</td>
    <td class="px-6 py-4">{{ props.ora }}</td>
    <td class="px-6 py-4" v-if="props.status === 1">Attiva</td>
    <td class="px-6 py-4" v-if="props.status === 2">Effettuata</td>
    <td class="px-6 py-4" v-if="props.status === 3">Disdetta</td>
  </tr>
</template>

<script setup>
import { defineProps } from "vue";
import dayjs from "dayjs";
const colors = {
  1: "bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  2: "bg-yellow-300 border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  3: "bg-red-300 border-b dark:bg-gray-800 dark:border-gray-700 text-center",
};
const props = defineProps({
  idripetizione: Number,
  idcorso: Number,
  iddocente: Number,
  ora: String,
  data: String,
  name: String,
  status: Number,
  lastname: String,
  titolo: String,
  accountname: String,
});
</script>
