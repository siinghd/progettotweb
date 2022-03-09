<template>
  <component :is="layout">
    <router-view :layout="layout" />
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
