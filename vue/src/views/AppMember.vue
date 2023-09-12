<template>
  <div>
    <div class='top_'>
      <h3 class='subject'>
        <router-link :to="{ name: 'mypage'}" style="color:white" >마이 페이지</router-link>
      </h3>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "AppMember",
  components: {},
};
</script>

<style>
.top_{
  background-image: url("@/assets/img/hotp6.jpg");
}
</style>
