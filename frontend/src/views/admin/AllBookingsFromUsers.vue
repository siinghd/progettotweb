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
            <th scope="col" class="px-6 py-3">Utente</th>
            <th scope="col" class="px-6 py-3">Nome docente</th>
            <th scope="col" class="px-6 py-3">Cognome docente</th>
            <th scope="col" class="px-6 py-3">Titolo Corso</th>
            <th scope="col" class="px-6 py-3">Data</th>
            <th scope="col" class="px-6 py-3">ora</th>
            <th scope="col" class="px-6 py-3">Stato</th>
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
            :accountname="item.utente.accountname"
          />
        </tbody>
      </table>
      <Pagination
        v-if="!isLoading && !isError"
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
import { genericGet } from "../../utilities/requests";
import PrenotazioniEffettuateTableRow from "../../components/admin/prenotazioniEffettuateAll/PrenotazioniEffettuateAllTableRow.vue";
import Pagination from "../../components/Pagination.vue";

const emit = defineEmits(["update:layout"]);
emit("update:layout", shallowRef(AdminLayoutVue));

const currentPage = ref(0);
const limit = 10;

const { isLoading, isError, data, error, refetch } = useQuery(
  ["getPrenotazioniEffettuateAll"],
  () =>
    genericGet(
      `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/prenotazioniall?currentpage=${currentPage.value}&limit=${limit}`
    )
);
</script>

<style scoped></style>
