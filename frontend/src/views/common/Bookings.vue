<template>
  <div class="Container">
    <div
      class="relative overflow-x-auto shadow-md sm:rounded-lg w-full m-auto mt-9"
    >
      <Loading v-if="isLoading" />
      <h3 class="text-center" v-if="isError">{{ error }}</h3>
      <table
        class="w-full text-sm text-left text-gray-500 dark:text-gray-400"
        v-if="data"
      >
        <thead
          class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400 text-center"
        >
          <tr>
            <th scope="col" class="px-6 py-3">Nome docente</th>
            <th scope="col" class="px-6 py-3">Cognome docente</th>
            <th scope="col" class="px-6 py-3">Titolo Corso</th>
            <th scope="col" class="px-6 py-3">Data</th>
            <th scope="col" class="px-6 py-3">Ora</th>
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Prenota</span>
            </th>
          </tr>
        </thead>
        <tbody>
          <TableRow
            v-for="item in data.data"
            :key="item.doc.id + item.corso.id + item.date + item.actualTime"
            :iddocente="item.doc.id"
            :data="item.date"
            :ora="item.actualTime"
            :name="item.doc.nome"
            :lastname="item.doc.cognome"
            :idcorso="item.corso.id"
            :titolo="item.corso.titolo"
            :submitPrenotaEvent="'handleOnSubmit'"
            @handleOnSubmit="
              (idcorso, iddocente, ora, data) =>
                handleOnPrenotaSubmit(idcorso, iddocente, ora, data, refetch)
            "
          />
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, shallowRef, ref } from "vue";
import AdminLayoutVue from "../../layouts/AdminLayout.vue";
import { useQuery } from "vue-query";
import { genericGet, genericPost } from "../../utilities/requests";
import TableRow from "../../components/common/prenotazioni/PrenotazioniTableRow";
import { createToast } from "mosha-vue-toastify";
import { useCookies } from "vue3-cookies";
import UserLayoutVue from "../../layouts/UserLayout.vue";

const cookie = useCookies().cookies.get("servletrole");

const emit = defineEmits(["update:layout"]);
emit(
  "update:layout",
  shallowRef(parseInt(cookie, 10) === 2 ? AdminLayoutVue : UserLayoutVue)
);

const currentPage = ref(1);
const limit = 10;

const { isLoading, isError, data, error, refetch } = useQuery(
  ["getPrenotazioniDisponibili"],
  () =>
    genericGet(
      `${process.env.VUE_APP_BACKEND_URL}/api/auth/common/prenotazionidisponibili?currentpage=${currentPage.value}&limit=${limit}`
    )
);

const handleOnPrenotaSubmit = async (
  idcorso,
  iddocente,
  ora,
  data,
  refetch
) => {
  const res = await genericPost(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/common/prenotazionidisponibili`,
    `idcorso=${idcorso}&iddocente=${iddocente}&status=${1}&data=${data}&ora=${ora}`
  );
  if (res.status === "success") {
    createToast(res.message, { type: "success" });
    refetch();
  } else {
    createToast(res.message, { type: "danger" });
  }
};
</script>

<style scoped></style>
