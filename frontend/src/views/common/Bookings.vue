<template>
  <div class="Container">
    <div
      class="relative overflow-x-auto shadow-md sm:rounded-lg w-full m-auto mt-9"
    >
      <Loading v-if="isLoading" />
      <h3 class="text-center" v-if="isError">{{ error }}</h3>
      Filtra:
      <select
        v-if="!isLoadingSubject"
        class="bg-gray-200 w-3/4 appearance-none border-2 border-gray-200 rounded py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
        id="inline-full-name"
        v-model="selectedFilter"
      >
        <option value="ND">Seleziona una materia</option>
        <option v-for="v in dataSubject.data" :value="v.titolo" :key="v">
          {{ v.titolo }}
        </option>
      </select>

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

        <tbody v-if="selectedFilter === 'ND'">
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
        <tbody v-if="selectedFilter !== 'ND'">
          <TableRow
            v-for="item in filteredElmts"
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
import { defineEmits, shallowRef, ref, watch } from "vue";
import AdminLayoutVue from "../../layouts/AdminLayout.vue";
import { useQuery } from "vue-query";
import { genericGet, genericPost } from "../../utilities/requests";
import TableRow from "../../components/common/prenotazioni/PrenotazioniTableRow";
import { createToast } from "mosha-vue-toastify";
import { useCookies } from "vue3-cookies";
import UserLayoutVue from "../../layouts/UserLayout.vue";
import OspiteLayoutVue from "../../layouts/OspiteLayout.vue";
import { useRouter } from "vue-router";

const router = useRouter();
const cookie = useCookies().cookies.get("servletrole");
const emit = defineEmits(["update:layout"]);
emit(
  "update:layout",
  shallowRef(
    parseInt(cookie, 10) === 2
      ? AdminLayoutVue
      : parseInt(cookie, 10) === 1
      ? UserLayoutVue
      : OspiteLayoutVue
  )
);

const currentPage = ref(1);
const limit = 10;
const selectedFilter = ref("ND");

const filteredElmts = ref([]);
const { isLoading, isError, data, error, refetch } = useQuery(
  ["getPrenotazioniDisponibili"],
  () =>
    genericGet(
      `${process.env.VUE_APP_BACKEND_URL}/api/getprenotazionidisponibili?currentpage=${currentPage.value}&limit=${limit}`
    )
);
const { isLoading: isLoadingSubject, data: dataSubject } = useQuery(
  ["getSubjectsCommon"],
  () =>
    genericGet(`${process.env.VUE_APP_BACKEND_URL}/api/auth/common/getsubjects`)
);

watch(
  () => selectedFilter.value,
  (selectedFilterv) => {
    filteredElmts.value = data.value.data.filter((v) => {
      return v.corso.titolo === selectedFilterv;
    });
  }
);
const handleOnPrenotaSubmit = async (
  idcorso,
  iddocente,
  ora,
  data,
  refetch
) => {
  if (!cookie) {
    router.push("/");
    return;
  }
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
