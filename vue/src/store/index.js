import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
// module import
import memberStore from "@/store/modules/memberStore";
import areaStore from "@/store/modules/areaStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    areaStore,
    memberStore,
  },
  plugins: [
    createPersistedState({
      storage: sessionStorage,
    }),
  ],
});
