<template>
  <div>
    <b-container>
      <b-row class="justify-content-center" style="margin-top: 10px">
        <b-col cols="12" class="board-top">
          <b-row class="mb-2 d-flex justify-content-between">
            <b-col cols="auto">
              <b-button class="w-auto" id="btn-mv-register">
                <router-link v-if="isUser" :to="{ name: 'qnaboardwrite' }" style="color: white"
                  >글쓰기</router-link
                >
                <div v-else style="color: white" @click="mvwrite">글쓰기</div>
              </b-button>
            </b-col>
            <b-col cols="auto" class="d-flex">
              <b-form class="d-flex align-items-center" id="form-search">
                <b-form-select
                  :options="option"
                  v-model="searchParam_list.key"
                  style="width: 100px"
                ></b-form-select>
                <b-form-input
                  v-model="searchParam_list.word"
                  style="width: 300px"
                  placeholder="검색어..."
                ></b-form-input>
                <b-button id="btn-search" class="w-auto text-nowrap" @click="search">검색</b-button>
              </b-form>
            </b-col>
          </b-row>
        </b-col>
        <b-table-simple hover responsive>
          <b-thead>
            <b-tr class="text-center">
              <b-th class="text-center">글번호</b-th>
              <b-th class="text-center">제목</b-th>
              <b-th class="text-center">작성자</b-th>
              <b-th class="text-center">조회수</b-th>
              <b-th class="text-center">작성일</b-th>
            </b-tr>
          </b-thead>
          <b-tbody v-for="article in articlesWithReplies" :key="`a-${article.articleNo}`">
            <b-tr :key="`article-${article.articleNo}`">
              <b-td>{{ article.articleNo }}</b-td>
              <b-th class="text-center" style="width: 40%">
                <router-link
                  :to="{ name: 'qnaboardview', params: { articleNo: article.articleNo } }"
                  >{{ article.subject }}</router-link
                >
              </b-th>
              <b-td>{{ article.userId }}</b-td>
              <b-td>{{ article.hit }}</b-td>
              <b-td>{{ article.registerTime }}</b-td>
            </b-tr>

            <b-tr v-for="reply in article.replies" :key="`reply-${reply.articleNo}`">
              <b-td></b-td>
              <b-th style="width: 40%; left: 30%">
                <router-link
                  :to="{ name: 'qnaboardreplyview', params: { articleNo: reply.articleNo } }"
                  >ㄴ답글 {{ reply.subject }}</router-link
                >
              </b-th>
              <b-td>{{ reply.userId }}</b-td>
              <b-td>{{ reply.hit }}</b-td>
              <b-td>{{ reply.registerTime }}</b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
        <b-pagination
          v-model="searchParam_list.pg"
          :total-rows="rows"
          :per-page="searchParam_list.spp"
          aria-controls="my-table"
          @input="search"
          align="center"
        ></b-pagination>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  props: {
    searchParam: {
      type: Object,
    },
    user: {
      type: String,
    },
  },
  name: "QnABoardList",
  data() {
    return {
      articles: [],
      replys: [],
      searchParam_list: {
        key: this.searchParam.key,
        word: this.searchParam.word,
        spp: 10,
        pg: this.searchParam.pg,
        num: this.searchParam.num,
        type: this.searchParam.type,
      },
      option: [
        { value: null, text: "검색조건" },
        { value: "subject", text: "제목" },
        { value: "article_no", text: "글번호" },
        { value: "user_id", text: "아이디" },
      ],
    };
  },
  computed: {
    rows() {
      return this.searchParam_list.num;
    },
    isUser() {
      return !!this.user;
    },
    articlesWithReplies() {
      return this.articles.map((article) => ({
        ...article,
        replies: this.replys.filter((reply) => reply.parentNo === article.articleNo),
      }));
    },
  },
  methods: {
    mvwrite() {
      alert("회원만 글을 작성할 수 있습니다.");
      console.log(this.searchParam_list.num);
    },
    total() {
      http
        .get("/qnaboard/total", {
          params: {
            key: this.searchParam_list.key,
            word: this.searchParam_list.word,
          },
        })
        .then((response) => {
          this.searchParam_list.num = response.data;
          this.$emit("search", this.searchParam_list);
        });
    },
    search() {
      http
        .get("/qnaboard", {
          params: {
            key: this.searchParam_list.key,
            word: this.searchParam_list.word,
            type: this.searchParam_list.type,
            spp: 10,
            pg: this.searchParam_list.pg,
          },
        })
        .then((response) => {
          this.articles = response.data;
        });
      http
        .get("/qnaboardreply", {
          params: {
            key: this.searchParam_list.key,
            word: this.searchParam_list.word,
            type: this.searchParam_list.type,
          },
        })
        .then((response) => {
          this.replys = response.data;
        });
      this.total();
    },
  },
  created() {
    this.search();
  },
};
</script>

<style></style>
