<template>
  <div>
    <div class='top_'>
      <h3 class='subject'>
        <router-link :to="{ name: 'tripplanboard'}" style="color:white" >여행계획</router-link>
      </h3>
    </div>
    <router-view @search="search" :searchParam="searchParam" :user="user"></router-view>
  </div>
</template>

<script>
export default {
  name: "AppAdminBoard",
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
  background-image: url("@/assets/img/hotp2.jpg");
}
</style>
