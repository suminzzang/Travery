<template>
  <div>
    <b-container>
      <b-row class="justify-content-center" style="margin-top: 10px;">
        <b-col cols="12" class='board-top'>
          <b-row class="mb-2 d-flex justify-content-between">
            <b-col cols="auto">
              <b-button class="w-auto"  id="btn-mv-register">
                <router-link v-if="isUser" :to="{ name: 'tripsearch' }" style="color: white;">글쓰기</router-link>
                <div v-else style="color: white;" @click="mvwrite">글쓰기</div>
              </b-button>
            </b-col>
            <b-col cols="auto" class='d-flex'>
              <b-form class="d-flex align-items-center" id="form-search">
                <b-form-select
                  :options="option"
                  v-model="searchParam_list.key"
                  style="width: 100px;"
                ></b-form-select>
                <b-form-input v-model="searchParam_list.word" style="width: 300px; " placeholder="검색어..."></b-form-input>
                <b-button id="btn-search" class="w-auto text-nowrap" @click="search">검색</b-button>
              </b-form>
            </b-col>
          </b-row>
        </b-col>
        <b-col cols="12">
          <b-row>
            <b-col md="4" class="mb-4" v-for="article in articles" :key="article.articleNo" v-bind="article">
              <b-card :title="article.subject" img-top @click="view(article.articleNo)"  style="cursor: pointer;">
                <div style="height: 300px;">
                  <img
                    :src="article.firstImg || require('@/assets/img/noimg.jpg')" 
                    @error="event => event.target.src=require('@/assets/img/noimg.jpg')"
                    alt="Image"
                    class="card-img-top"
                    style="max-height: 100%; object-fit: contain; width: 100%;"
                  >
                </div>
                <b-card-text style="height: 150px; overflow: auto;">
                  {{ article.content.length > 100 ? (article.content.substring(0, 200) + '...') : article.content }}
                </b-card-text>
                <template #footer>
                  <div style="height: 50px;">
                    <b-card-text class="text-muted">관광지: {{ article.title.length > 20 ? article.title.slice(0, 18) + '...' : article.title }}</b-card-text>
                    <b-card-text class="text-muted">작성자: {{ article.userId }}</b-card-text>
                  </div>
                </template>
              </b-card>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-pagination
          v-model="searchParam_list.pg"
          :total-rows="rows"
          :per-page="searchParam.spp"
          aria-controls="my-table"
          align='center'
          @input="search"
        ></b-pagination>
    </b-container>
  </div>
</template>
<script>
import http from "@/api/http";

export default {
  name: "HotPlaceBoardList",
  props: {
    searchParam: {
      type: Object,
    },
    user: {
      type: String,
    }
  },
  data() {
    return {
      articles: [],
      searchParam_list:{
        key: this.searchParam.key,
        word: this.searchParam.word,
        spp: this.searchParam.spp,
        pg: this.searchParam.pg,
        num: this.searchParam.num,
      },
      option: [
        { value: null, text: '검색조건' },
        { value: 'subject', text: '제목' },
        { value: 'article_no', text: '글번호' },
        { value: 'user_id', text: '아이디' },
      ],

    }
  },
  computed: {
    rows() {
        return this.searchParam_list.num
    },
    isUser() {
      return !!this.user;
    }
  },
  created() {
    this.search();
  },
  methods: {
    mvwrite() {
      alert('회원만 글을 작성할 수 있습니다.');
    },
    view(articleNo) {
      this.$router.push({name:'hotplaceboardview' ,params: {articleNo:articleNo }})
    },
    total() {
      http.get("/hotplaceboard/total", {
      params: {
        key: this.searchParam_list.key,
        word: this.searchParam_list.word,
      },
    })
      .then((response) => {
        this.searchParam_list.num = response.data;
        this.$emit('search', this.searchParam_list);
    })
    },

    search() {
      http.get("/hotplaceboard", {
      params: {
        key: this.searchParam_list.key,
        word: this.searchParam_list.word,
        pg: this.searchParam_list.pg,
        spp: this.searchParam_list.spp
      },
    })
    .then((response) => {
      this.articles = response.data;
      console.log(this.articles);
    });
      this.total();
    },
  },  
};
</script>

<style scoped>
.card-group-custom {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between; /* optional: for space between cards */
}

.card-custom {
    flex: 1 0 auto;
    width: 300px; /* specify your preferred width here */
}
