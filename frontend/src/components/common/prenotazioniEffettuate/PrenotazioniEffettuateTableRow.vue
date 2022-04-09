<template>
  <tr :class="colors[props.status]">
    <th
      scope="row"
      class="px-6 py-4 font-medium text-gray-900 dark:text-white whitespace-nowrap"
    >
      {{ props.name }}
    </th>
    <td class="px-6 py-4">{{ props.lastname }}</td>
    <td class="px-6 py-4">{{ props.titolo }}</td>
    <td class="px-6 py-4">{{ dayjs(props.data).format("DD-MM-YYYY") }}</td>
    <td class="px-6 py-4">{{ props.ora }}</td>
    <td class="px-6 py-4" v-if="props.status === 1">Attiva</td>
    <td class="px-6 py-4" v-if="props.status === 2">Effettuata</td>
    <td class="px-6 py-4" v-if="props.status === 3">Disdetta</td>
    <td class="px-6 py-4">
      <select
        v-if="props.status !== 2"
        class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
        id="inline-full-name"
        :value="props.status"
        @change="($e) => actionOnChange($e)"
      >
        <option value="1">Attiva</option>
        <option value="2" v-if="parseInt(cookie, 10) === 1">Effettuata</option>
        <option value="3">Disdici</option>
      </select>
    </td>
  </tr>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";
import { useCookies } from "vue3-cookies";
import dayjs from "dayjs";
const colors = {
  1: "bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  2: "bg-yellow-300 border-b dark:bg-gray-800 dark:border-gray-700 text-center",
  3: "bg-red-300 border-b dark:bg-gray-800 dark:border-gray-700 text-center",
};
const cookie = useCookies().cookies.get("servletrole");
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
  submitEvent: String,
});
const emit = defineEmits(["handleOnSubmit"]);
function actionOnChange(evt) {
  emit("handleOnSubmit", props.idripetizione, evt.target.value);
}
</script>
