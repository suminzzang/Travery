<template>
  <div>
    <div class="top_">
      <b-form @submit.prevent="submitForm" id="formSerach" role="search">
        <b-form-row @submit.prevent>
          <b-col cols="auto">
            <b-form-select
              id="sido"
              class="form-select"
              aria-label="Default select example"
              style="border: none; text-align: center; margin-left: 10px"
              v-model="searchParam_list.sido_code"
              :options="sidos"
              @change="gugunList"
            >
            </b-form-select
          ></b-col>
          <b-col cols="auto">
            <b-form-select
              id="gugun"
              class="form-select"
              aria-label="Default select example"
              style="border: none; text-align: center; margin-left: 10px"
              v-model="searchParam_list.gugun_code"
              :options="guguns"
            >
            </b-form-select
          ></b-col>
          <b-col cols="auto">
            <b-form-select
              id="sortType"
              name="sortType"
              class="form-select"
              aria-label="Default select example"
              style="border: none; text-align: center; margin-left: 10px"
              :options="sort"
              v-model="searchParam_list.sortType"
            >
            </b-form-select></b-col
          ><b-col cols="auto">
            <b-form-select
              id="searchContentId"
              name="searchContentId"
              class="form-select"
              aria-label="Default select example"
              style="border: none; text-align: center; margin-left: 10px"
              :options="contentId"
              v-model="searchParam_list.contentTypeId"
            >
            </b-form-select></b-col
          ><b-col class="d-flex">
            <b-form-input
              id="searchKeyword"
              name="searchKeyword"
              class="form-control ms-2"
              type="text"
              placeholder="검색어"
              aria-label="검색어"
              style="display: inline-block; width: 70%; text-align: center"
              v-model="searchParam_list.word"
            />
            <div class="ms-3" style="width: 10px"></div>
            <b-button
              id="btn_search"
              class="btn btn-dark w-auto text-nowrap"
              @click="search"
              @keypress.enter="search"
              >검색</b-button
            >
          </b-col>
        </b-form-row>
      </b-form>
    </div>
    <b-container fluid style="display: flex; position: sticky; z-index: 1000; top: 0">
      <div id="map">
        <Trip-Kakao-map
          :articles="articles"
          :tripPlan="tripPlan"
          :center="center"
          :user="user"
          @add-plan="addPlanToTrip"
        ></Trip-Kakao-map>
      </div>
      <div id="map2">
        <div>
          <div class="mb-3">
            <h2 class="text-nowrap" style="font: bold">여행계획</h2>
            <b-button
              id="btn_search"
              class="btn w-auto text-nowrap"
              style="color: blue; background-color: #d0e3ed"
            >
              <!-- router.push({name: 'tripplanboardwrite', params: {tripPlan: yourTripPlanArray}}) -->
              <router-link
                v-if="isUser"
                :to="{ name: 'tripplanboardwrite', params: { tripPlan: tripPlan } }"
                style="color: blue"
                >작성하러 가기</router-link
              >
              <div v-else style="color: blue" @click="mvwrite">작성하러 가기</div>
            </b-button>
            <b-button
              id="btn_search"
              class="btn w-auto text-nowrap"
              style="color: #cd6666; background-color: #d0e3ed; margin-left: 10px"
              @click="removePlanAll()"
              >초기화</b-button
            >
          </div>
          <draggable v-model="tripPlan">
            <div
              v-for="(plan, index) in tripPlan"
              :key="plan.id"
              style="display: flex; justify-content: space-between; margin: 0 20px"
            >
              <span @click="moveCenter(plan.latitude, plan.longitude, plan.title)"
                >{{ index + 1 }}. {{ plan.title }}</span
              >
              <button
                style="color: red; background-color: #d0e3ed; border: none; border-radius: 50%"
                @click="removePlan(index)"
              >
                x
              </button>
            </div>
          </draggable>
        </div>
      </div>
    </b-container>
    <b-row>
      <b-col cols="12">
        <b-table-simple hover responsive class="table-striped">
          <b-thead>
            <b-tr class="text-center">
              <b-th class="text-center text-nowrap">대표이미지</b-th>
              <b-th class="text-center text-nowrap">관광지명</b-th>
              <b-th class="text-center text-nowrap">주소</b-th>
              <b-th class="text-center text-nowrap">현재 위치와의 거리</b-th>
              <b-th class="text-center text-nowrap">상세 설명</b-th>
              <b-th class="text-center text-nowrap">여행계획</b-th>
              <b-th class="text-center text-nowrap">핫플레이스</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr
              v-for="article in articles"
              :key="article.articleNo"
              v-bind="article"
              @click="moveCenter(article.latitude, article.longitude, article.title)"
            >
              <b-td>
                <b-img
                  :src="article.firstImage || require('@/assets/img/noimg.jpg')"
                  @error="(event) => (event.target.src = require('@/assets/img/noimg.jpg'))"
                  style="object-fit: cover; max-height: 100px"
                  v-b-popover.hover.html="
                    '<img src=' +
                    (article.firstImage || require('@/assets/img/noimg.jpg')) +
                    ' onerror=this.src=' +
                    require('@/assets/img/noimg.jpg') +
                    ' style=\'width: 400px\' />'
                  "
                >
                </b-img>
              </b-td>
              <b-td>{{ article.title }}</b-td>
              <b-td>
                <div style="min-width: 60px">{{ article.addr1 }}</div>
              </b-td>
              <!-- <b-th class="text-center" style="width: 40%;">
                  <router-link :to="{ name: 'adminboardview', params: { articleNo: article.articleNo } }">{{ article.subject }}</router-link>
                </b-th> -->
              <b-td>{{ article.distance }}</b-td>
              <b-td>
                <div class="text-box" :title="article.overview">{{ article.overview }}</div>
              </b-td>
              <b-td>
                <div style="min-width: 30px" class="text-center">
                  <b-button
                    class="btn btn-outline-primary mb-3"
                    style="background-color: white"
                    @click="addTrip(article)"
                    >추가</b-button
                  >
                </div>
              </b-td>
              <b-td>
                <div style="min-width: 30px" class="text-center">
                  <b-button class="btn btn-outline-primary mb-3" style="background-color: white">
                    <router-link
                      v-if="isUser"
                      :to="{ name: 'hotplaceboardwrite', params: { hotplace: article } }"
                      style="color: blue"
                      >작성</router-link
                    >
                    <div v-else style="color: blue" @click="mvwrite">작성</div>
                  </b-button>
                </div>
              </b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>
      </b-col>
    </b-row>
    <b-pagination
      v-if="searchParam_list.sido_code"
      v-model="searchParam_list.pg"
      :total-rows="searchParam_list.num"
      :per-page="searchParam_list.spp"
      aria-controls="my-table"
      :limit="8"
      @input="search"
      align="center"
    ></b-pagination>
  </div>
</template>
<script>
import http from "@/api/http";
import TripKakaoMap from "@/components/tripsearch/TripKakaoMap.vue";
import { mapState, mapActions, mapMutations } from "vuex";
import draggable from "vuedraggable";

const areaStore = "areaStore";
const memberStore = "memberStore";

export default {
  name: "TripSearchList",
  props: {
    user: {
      type: String,
    },
  },

  data() {
    return {
      articles: [],
      tripPlan: [],
      searchParam_list: {
        pg: null,
        sido_code: null,
        gugun_code: null,
        sortType: 1,
        contentTypeId: null,
        word: null,
        latitude: null,
        longitude: null,
        num: null,
      },
      center: {
        lat: null,
        lng: null,
        title: null,
      },
      sort: [
        { value: 1, text: "제목순 정렬" },
        { value: 2, text: "거리순 정렬" },
        { value: 3, text: "인기순 정렬" },
      ],
      contentId: [
        { value: null, text: "전체 선택" },
        { value: 12, text: "관광지" },
        { value: 14, text: "문화시설" },
        { value: 15, text: "축제공연행사" },
        { value: 25, text: "여행코스" },
        { value: 28, text: "레포츠" },
        { value: 32, text: "숙박" },
        { value: 38, text: "쇼핑" },
        { value: 39, text: "음식점" },
      ],
    };
  },
  components: {
    TripKakaoMap,
    draggable,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(areaStore, ["sidos", "guguns"]),
    rows() {
      return this.searchParam_list.num;
    },
    isUser() {
      return !!this.user;
    },
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
    this.getUserLocation(); // 사용자의 위치 정보를 가져옵니다.
    //this.search();
  },
  methods: {
    ...mapActions(areaStore, ["getSido", "getGugun"]),
    ...mapMutations(areaStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    moveCenter(lat, lng, title) {
      this.center = {
        lat: lat,
        lng: lng,
        title: title,
      };
    },
    mvwrite() {
      alert("회원만 글을 작성할 수 있습니다.");
    },
    addPlanToTrip(plan) {
      this.tripPlan.push(plan);
    },
    addTrip(article) {
      this.tripPlan.push(article);
    },
    removePlan(index) {
      this.tripPlan.splice(index, 1);
    },
    removePlanAll() {
      this.tripPlan = [];
    },
    getUserLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          (position) => {
            this.searchParam_list.latitude = position.coords.latitude;
            this.searchParam_list.longitude = position.coords.longitude;
          },
          (error) => {
            console.error(error);
            // 위치 정보를 가져오는데 실패했을 때의 처리 로직
          },
          {
            enableHighAccuracy: true, // 높은 정확도 사용
            timeout: 5000, // 타임아웃 5초
            maximumAge: 0, // 캐시 위치 정보의 최대 수명
          }
        );
      } else {
        console.log("Geolocation is not supported by this browser.");
        // Geolocation이 지원되지 않는 브라우저일 때의 처리 로직
      }
    },
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.searchParam_list.gugun_code = null;
      if (this.searchParam_list.sido_code) this.getGugun(this.searchParam_list.sido_code);
    },
    total() {
      http
        .get("/tripsearch/total", {
          params: this.searchParam_list,
        })
        .then((response) => {
          this.searchParam_list.num = response.data;
        });
    },
    search() {
      if (!this.searchParam_list.sido_code) {
        alert("시/도는 필수적으로 입력해야 합니다!!");
      } else {
        http
          .get("/tripsearch", {
            params: this.searchParam_list,
          })
          .then((response) => {
            this.articles = response.data;
          });
        this.total();
      }
    },
  },
};
</script>

<style scoped>
#formSerach {
  margin-left: 0%;
  margin-top: 0%;
  width: 55%;
  position: absolute;
  text-align: center;
  padding: 20px 0px;
  border-radius: 20px;
  background-color: white;
}

.text-box {
  max-height: 100px; /* 원하는 높이로 설정 */
  overflow-y: auto; /* 스크롤바 추가 */
}

#map {
  width: 80%;
  height: 50vh;
  margin-left: 0%;
}

#map2 {
  width: 20%;
  height: 50vh;
  background-color: #d0e3ed;
  overflow-y: auto;
}

.top_ {
  background-image: url("@/assets/img/hotpl02.jpg");
}
</style>
