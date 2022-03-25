<template>
  <component :is="layout">
    <router-view v-model:layout="layout" />
  </component>
</template>

<script>
export default {
  data() {
    return {
      layout: `div`,
    };
  },
};
</script>
