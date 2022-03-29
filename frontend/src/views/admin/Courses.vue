<template>
  <div class="container">
    <div class="container rounded-md shadow-2xl p-5 max-w-md m-auto">
      <Loading v-if="isLoadingDocente" />
      <h3 class="text-center" v-if="isErrorDocente">{{ errorDocente }}</h3>
      <Loading v-if="isLoadingCorso" />
      <h3 class="text-center" v-if="isErrorCorso">{{ errorCorso }}</h3>
      <form class="w-full max-w-sm" v-if="dataCorso && dataDocente">
        <div class="md:flex md:items-center mb-6">
          <div class="md:w-1/3">
            <label
              class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
              for="inline-full-name"
            >
              Docenti
            </label>
          </div>
          <div class="md:w-2/3">
            <select
              class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
              id="inline-full-name"
              v-model="iddocente"
            >
              <option value="-1">Seleziona il docente</option>
              <option
                v-for="singleitem in dataDocente.data"
                :key="singleitem.id"
                :value="singleitem.id"
              >
                {{ `${singleitem.nome} ${singleitem.cognome}` }}
              </option>
            </select>
          </div>
        </div>
        <div class="md:flex md:items-center mb-6">
          <div class="md:w-1/3">
            <label
              class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
              for="inline-last-name"
            >
              Corso
            </label>
          </div>
          <div class="md:w-2/3">
            <select
              class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
              id="inline-full-name"
              v-model="idcorso"
            >
              <option value="-1">Seleziona il corso</option>
              <option
                v-for="singleitem in dataCorso.data"
                :key="singleitem.id"
                :value="singleitem.id"
              >
                {{ `${singleitem.titolo}` }}
              </option>
            </select>
          </div>
        </div>
        <div class="md:flex md:items-center">
          <div class="md:w-1/3"></div>
          <div class="md:w-2/3">
            <Button
              :title="'Aggiungi Corso'"
              :submitEvent="'handleButtonSubmit'"
              @handleButtonSubmit="handleButtonSubmit(refetch)"
            />
          </div>
        </div>
      </form>
    </div>
    <div
      class="relative overflow-x-auto shadow-md sm:rounded-lg w-4/6 m-auto mt-9"
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
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Elimina</span>
            </th>
          </tr>
        </thead>
        <tbody>
          <TableRow
            v-for="item in data.data"
            :key="item.docente.id + item.corso.id"
            :iddocente="item.docente.id"
            :name="item.docente.nome"
            :lastname="item.docente.cognome"
            :idcorso="item.corso.id"
            :titolo="item.corso.titolo"
            :deleteEvent="'handleOnDeleteSubmit'"
            @handleOnDeleteSubmit="
              (idcorso, iddocente) =>
                handleOnDeleteSubmit(idcorso, iddocente, refetch)
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
import {
  genericGet,
  genericPost,
  genericDelete,
} from "../../utilities/requests";
import Loading from "../../components/Loading.vue";
import Button from "../../components/Button.vue";
import TableRow from "../../components/admin/corsodocente/CorsoDocenteTableRow.vue";
import { createToast } from "mosha-vue-toastify";

// starting
const emit = defineEmits(["update:layout"]);
emit("update:layout", shallowRef(AdminLayoutVue));

//variables
const idcorso = ref("-1");
const iddocente = ref("-1");

//functions

//prefecth
const { isLoading, isError, data, error, refetch } = useQuery(
  ["getCourseProfessor"],
  () =>
    genericGet(
      `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/courseprofessor`
    )
);
//prefecth
const {
  isLoading: isLoadingDocente,
  isError: isErrorDocente,
  data: dataDocente,
  error: errorDocente,
} = useQuery(["getProfessors"], () =>
  genericGet(`${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/docente`)
);
//prefecth
const {
  isLoading: isLoadingCorso,
  isError: isErrorCorso,
  data: dataCorso,
  error: errorCorso,
} = useQuery(["getSubjects"], () =>
  genericGet(`${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/subject`)
);

//handlesubmit
const handleButtonSubmit = async (refetch) => {
  const res = await genericPost(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/courseprofessor`,
    `idcorso=${idcorso.value}&iddocente=${iddocente.value}`
  );
  if (res.status === "success") {
    createToast(res.message, { type: "success" });
    refetch();
  } else {
    createToast(res.message, { type: "danger" });
  }
};

//handledelete
const handleOnDeleteSubmit = async (idcorso, iddocente, refetchf) => {
  const res = await genericDelete(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/courseprofessor?idcorso=${idcorso}&iddocente=${iddocente}`
  );
  if (res.status === "success") {
    createToast(res.message, { type: "success" });
    refetchf();
  } else {
    createToast(res.message, { type: "danger" });
  }
};

// lifecycle hooks
</script>

<style scoped></style>
