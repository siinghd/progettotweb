<template>
  <div class="Container">
    <Loading v-if="isLoading" />
    <div
      class="relative overflow-x-auto shadow-md sm:rounded-lg w-full m-auto mt-9"
    >
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
            <th scope="col" class="px-6 py-3">ora</th>
            <th scope="col" class="px-6 py-3">Stato</th>
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Azione</span>
            </th>
          </tr>
        </thead>
        <tbody>
          <PrenotazioniEffettuateTableRow
            v-for="item in data.data"
            :key="item.doc.id + item.corso.id + item.date + item.actualTime"
            :idripetizione="item.id"
            :iddocente="item.doc.id"
            :data="item.date"
            :status="item.status"
            :ora="item.time"
            :name="item.doc.nome"
            :lastname="item.doc.cognome"
            :idcorso="item.corso.id"
            :titolo="item.corso.titolo"
            :submitEvent="'handleOnSubmit'"
            @handleOnSubmit="
              (idripetizione, action) =>
                handleOnPrenotaSubmit(idripetizione, action, refetch)
            "
          />
        </tbody>
      </table>
      <Pagination
        v-if="!isLoading"
        :dataLength="data.data.length"
        :currPage="currentPage"
        :limit="limit"
        @nextPage="
          () => {
            currentPage = currentPage + 1;
            refetch();
          }
        "
        @prevPage="
          () => {
            currentPage = currentPage - 1;
            refetch();
          }
        "
      />
    </div>
  </div>
</template>

<script setup>
import { defineEmits, shallowRef, ref } from "vue";
import AdminLayoutVue from "../../layouts/AdminLayout.vue";
import { useQuery } from "vue-query";
import { genericGet, genericUpdate } from "../../utilities/requests";
import { createToast } from "mosha-vue-toastify";
import PrenotazioniEffettuateTableRow from "../../components/common/prenotazioniEffettuate/PrenotazioniEffettuateTableRow.vue";
import Pagination from "../../components/Pagination.vue";
import { useCookies } from "vue3-cookies";
import UserLayoutVue from "../../layouts/UserLayout.vue";
const cookie = useCookies().cookies.get("servletrole");
const emit = defineEmits(["update:layout"]);
emit(
  "update:layout",
  shallowRef(parseInt(cookie, 10) === 2 ? AdminLayoutVue : UserLayoutVue)
);

const currentPage = ref(0);
const limit = 10;

const { isLoading, isError, data, error, refetch } = useQuery(
  ["getPrenotazioniEffettuate"],
  () =>
    genericGet(
      `${process.env.VUE_APP_BACKEND_URL}/api/auth/common/prenotazionieffettuate?currentpage=${currentPage.value}&limit=${limit}`
    )
);

const handleOnPrenotaSubmit = async (idripetizione, action, refetch) => {
  console.log(idripetizione, action);
  const res = await genericUpdate(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/common/prenotazionieffettuate?idripetizione=${idripetizione}&statuschange=${action}`
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
