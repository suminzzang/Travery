<template>
  <div>
    <router-view :user="user"></router-view>
  </div>
</template>

<script>
export default {
  name: "AppTripSearch",
  props: {
    user: {
      type:String,
    }
  },
};
</script>

<style scoped>
</style>
