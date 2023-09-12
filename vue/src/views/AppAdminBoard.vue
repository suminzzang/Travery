<template>
  <div>
    <div class='top_'>
      <h3 class='subject'>
        <router-link :to="{ name: 'adminboard'}" style="color:white" >공지 사항</router-link>
      </h3>
    </div>
    <router-view @search="search" :searchParam="searchParam" :user="user"></router-view>
  </div>
</template>

<script>
export default {
  name: "AppTripPlanBoard",
  props: {
    user: {
      type:String,
    }
  },
  data() {
    return {
      searchParam: {
        key: null,
        word: null,
        spp:20,
        pg: 1,
        num:null,
      },
    }
  },
  methods: {
    search(searchParam) {
      this.searchParam.key = searchParam.key;
      this.searchParam.word = searchParam.word;
      this.searchParam.spp = searchParam.spp;
      this.searchParam.pg = searchParam.pg;
      this.searchParam.num = searchParam.num;
    }
  }
};
</script>

<style scoped>
.top_{
  background-image: url("@/assets/img/hotp8.jpg");
}
</style>
