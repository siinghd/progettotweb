<template>
  <div class="container">
    <div class="container rounded-md shadow-2xl p-5 max-w-md m-auto">
      <form class="w-full max-w-sm">
        <div class="md:flex md:items-center mb-6">
          <div class="md:w-1/3">
            <label
              class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
              for="inline-full-name"
            >
              Nome
            </label>
          </div>
          <div class="md:w-2/3">
            <input
              class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
              id="inline-full-name"
              type="text"
              placeholder="Mario"
              v-model="name"
            />
          </div>
        </div>
        <div class="md:flex md:items-center mb-6">
          <div class="md:w-1/3">
            <label
              class="block text-gray-500 font-bold md:text-right mb-1 md:mb-0 pr-4"
              for="inline-last-name"
            >
              Cognome
            </label>
          </div>
          <div class="md:w-2/3">
            <input
              class="bg-gray-200 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
              id="inline-last-name"
              type="text"
              placeholder="Rossi"
              v-model="lastname"
            />
          </div>
        </div>
        <div class="md:flex md:items-center">
          <div class="md:w-1/3"></div>
          <div class="md:w-2/3">
            <Button
              :title="'Aggiungi docente'"
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
            <th scope="col" class="px-6 py-3">Nome</th>
            <th scope="col" class="px-6 py-3">Cognome</th>
            <th scope="col" class="px-6 py-3">
              <span class="sr-only">Elimina</span>
            </th>
          </tr>
        </thead>
        <tbody>
          <TableRow
            v-for="item in data.data"
            :key="item.id"
            :id="item.id"
            :name="item.nome"
            :lastname="item.cognome"
            :deleteEvent="'handleOnDeleteSubmit'"
            @handleOnDeleteSubmit="(id) => handleOnDeleteSubmit(id, refetch)"
          />
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, shallowRef, ref } from 'vue';
import AdminLayoutVue from '../../layouts/AdminLayout.vue';
import { useQuery } from 'vue-query';
import {
  genericGet,
  genericPost,
  genericDelete,
} from '../../utilities/requests';
import Loading from '../../components/Loading.vue';
import Button from '../../components/Button.vue';
import TableRow from '../../components/admin/docente/TableRow.vue';
import { createToast } from 'mosha-vue-toastify';

// starting
const emit = defineEmits(['update:layout']);
emit('update:layout', shallowRef(AdminLayoutVue));

//variables
const name = ref('');
const lastname = ref('');

//functions

//prefecth
const { isLoading, isError, data, error, refetch } = useQuery(
  ['getProfessors'],
  () => genericGet(`${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/docente`)
);

//handlesubmit
const handleButtonSubmit = async (refetchf) => {
  const res = await genericPost(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/docente`,
    `name=${name.value}&lastname=${lastname.value}`
  );
  if (res.status === 'success') {
    createToast(res.message, { type: 'success' });
    refetchf();
  } else {
    createToast(res.message, { type: 'danger' });
  }
};

//handledelete
const handleOnDeleteSubmit = async (id, refetchf) => {
  const res = await genericDelete(
    `${process.env.VUE_APP_BACKEND_URL}/api/auth/admin/docente?id=${id}`
  );
  if (res.status === 'success') {
    createToast(res.message, { type: 'success' });
    refetchf();
  } else {
    createToast(res.message, { type: 'danger' });
  }
};

// lifecycle hooks
</script>

<style scoped></style>
