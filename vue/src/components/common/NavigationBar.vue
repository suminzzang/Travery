<template>
  <div style="margin-top: 74px">
    <b-navbar toggleable="lg" type="dark" variant="info" fixed="top">
      <b-navbar-brand href="/" style="font-size: 2rem">Travery</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/adminboard">공지사항</b-nav-item>
          <b-nav-item-dropdown text="커뮤니티">
            <b-dropdown-item href="/hotplaceboard">핫플레이스</b-dropdown-item>
            <b-dropdown-item href="/userboard">자유게시판</b-dropdown-item>
            <b-dropdown-item href="/tripplanboard">여행계획 게시판</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item href="/tripsearch">관광지 검색</b-nav-item>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto" v-if="!userInfo">
          <!-- todo: 로그인이 안되있는 경우 -->
          <b-nav-item-dropdown text="고객센터">
            <b-dropdown-item href="/qnaboard">QNA게시판</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item-dropdown text="로그인/회원가입">
            <b-dropdown-item v-b-modal.login-modal>로그인</b-dropdown-item>
            <b-dropdown-item v-b-modal.signup-modal>회원가입</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
        <b-navbar-nav class="ml-auto" v-else>
          <!-- todo: 로그인이 되어있는 경우 -->
          <b-nav-item-dropdown text="고객센터">
            <b-dropdown-item href="/qnaboard">QNA게시판</b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item :to="{ name: 'users', params: { userId: `${userInfo.userId}` } }">
            마이페이지
          </b-nav-item>
          <b-nav-item @click="logout()">로그아웃</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <sign-modal ref="sign"></sign-modal>
  </div>
</template>

<script>
import SignModal from "./SignModal.vue";
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "NavigationBar",
  components: {
    SignModal,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),

    logout() {
      this.userLogout(this.userInfo.userId);
      if (this.$route.name != "main") this.$router.push({ path: "/" });
    },
  },
};
</script>

<style scoped>
.bg-info {
  background-color: black !important;
}
</style>
