<template>
  <div>
    <b-container>
      <b-row class="justify-content-center" style="margin-top: 10px;">
        <b-col cols="12">
          <h2 class="my-0 shadow-sm bg-light text-center">
            <mark class="sky">조회</mark>
          </h2>
        </b-col>
        <b-col cols="12">
          <b-form>
            <div class="mb-3 d-flex flex-column align-items-start">
              <label for="subject" class="form-label" style="font-size: 1.5rem;">제목 : </label>
              <b-form-input v-model="subject" type="text" style="border-color: black;" readonly></b-form-input>
              <label for="subject" class="form-label" style="font-size: 1.5rem;">내용 : </label>
              <b-form-textarea v-model="content" rows="12" style="border-color: black;" readonly></b-form-textarea>
            </div>
            <b-col cols='auto' class="text-center">
              <b-button v-if="isAdmin" class="btn btn-outline-primary mb-3" style="background-color: white;">
                <router-link v-if="isAdmin" :to="{ name: 'adminboardmodify',params: { articleNo:`${this.articleNo}` }}">글수정</router-link>
              </b-button>
              <span v-if="isAdmin" style="margin: 0 10px;"></span> <!-- 빈 칸 -->
              <b-button v-if="isAdmin" class="btn btn-outline-danger mb-3" style="background-color: white;" @click='deleteBoard'>
                글삭제
              </b-button>
              <span v-if="isAdmin" style="margin: 0 10px;"></span> <!-- 빈 칸 -->
              <b-button class="btn btn-outline-primary mb-3" style="background-color: white;" @click='list'>
                목록으로이동...
              </b-button>              
            </b-col>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  props: {
    user: {
      type:String,
    }
  },
  name: "AdminBoardView",
  data() {
    return {
      subject: "",
      content: "",
      articleNo:this.$route.params.articleNo,
    }
  },
  computed: {
    userId() {
      return this.user;
    },
    isAdmin() {
      return this.user == "admin";
    }
  },
  methods: {
    deleteBoard() {
      http.delete(`/adminboard/${this.articleNo}`)
      .then(() => {
        this.$router.push({ name: "adminboardlist" });
      });
    },
    list() {
      this.$router.push({ name: 'adminboardlist' });
    }
  },
  created() {
    http.get(`/adminboard/${this.articleNo}`)
      .then((response) => {
      this.subject = response.data.subject;
      this.content = response.data.content;
      });
  },
};
</script>

<style></style>
