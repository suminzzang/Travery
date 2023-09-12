<template>
  <div id="app">
    <navigation-bar></navigation-bar>
    <router-view :user="user" />
  </div>
</template>
<script>
import { mapState } from "vuex";
import NavigationBar from "@/components/common/NavigationBar.vue";

const memberStore = "memberStore";

export default {
  components: {
    NavigationBar,
  },
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    user() {
      return this.userInfo === null ? "" : this.userInfo.userId;
    },
  },
  methods: {},
};
</script>
<style>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");
#app {
  font-family: "Jua", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin: 0px;
  padding: 0px;
}

nav {
  padding: 30px;
}

nav a {
  font-size: 1.2rem;
  color: #2c3e50;
  margin-inline-start: 10px;
}

nav a.router-link-exact-active {
  color: #42b983;
}

.top_ {
  width: 100%;
  height: 400px;
  margin-bottom: 15px;
  background-image: url("@/assets/img/hotp5.jpg");
  background-position: center center;
  background-repeat: no-repeat;
  background-origin: content-box;
  background-size: cover;
  margin: 0px;
  padding: 0px;
  margin-top: 0px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.top_ .subject {
  text-align: center;
  width: 50%;
  position: relative;
  text-align: center;
  font-family: sans-serif;
  font-weight: bold;
  border: 3px solid #f1f1f1;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.65);
  color: white;
}

.board-top .btn {
  font-size: 20px;
  border-color: black;
}

.blue-button {
  color: blue;
}

.red-button {
  color: red;
  /* 추가적인 스타일링 규칙을 여기에 작성할 수 있습니다. */
}

.validation-message {
  font-size: small;
}

.invalid-msg {
  color: red;
}

.valid-msg {
  color: green;
}
</style>
