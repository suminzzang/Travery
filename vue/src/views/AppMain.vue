<template>
  <div>
    <div class="top_">
      <h1 class="subject">TRAVERY</h1>
    </div>
    <b-container fluid>
      <b-row class="mb-4 mt-5">
        <b-col class="left-col" style="background-color: #fffdfd">
          <b-row>
            <span class="board-name">공지사항</span>
          </b-row>
          <b-table-simple :hover="notices.length !== 0" responsive>
            <b-thead>
              <b-tr>
                <b-th>글번호</b-th>
                <b-th>제목</b-th>
                <b-th>조회수</b-th>
                <b-th>작성일</b-th>
              </b-tr>
            </b-thead>
            <b-tbody v-if="notices.length === 0">
              <b-tr>
                <b-td class="empty-article" colspan="4"> 작성된 공지사항이 없습니다. </b-td>
              </b-tr>
            </b-tbody>
            <b-tbody v-else>
              <b-tr v-for="notice in notices" :key="notice.articleNo" v-bind="notice">
                <b-td class="vertical-align-middle article-number p-8">{{ notice.articleNo }}</b-td>
                <b-th>
                  <router-link
                    :to="{ name: 'adminboardview', params: { articleNo: notice.articleNo } }"
                    >{{ notice.subject }}</router-link
                  >
                </b-th>
                <b-td class="vertical-align-middle article-hit p-8">{{ notice.hit }}</b-td>
                <b-td class="vertical-align-middle article-regist-date p-8">{{
                  notice.registerTime | fullDateFormat
                }}</b-td>
              </b-tr>
            </b-tbody>
          </b-table-simple>
        </b-col>
        <b-col class="right-col" style="background-color: #fffdfd">
          <b-row>
            <span class="board-name">이달의 핫플</span>
          </b-row>
          <b-row>
            <b-carousel
              id="hotplace-carousel"
              v-model="slide"
              :interval="4000"
              :controls="hotplaces.length !== 0"
              :indicators="hotplaces.length !== 0"
              img-width="1600"
              img-height="900"
              background="#ababab"
              @sliding-start="onSlideStart"
              @sliding-end="onSlideEnd"
            >
              <!-- Slides with custom text -->

              <b-carousel-slide v-if="hotplaces.length === 0" img-blank img-alt="Blank image">
                <p>이번 달에 작성된 핫플레이스 게시글이 없습니다.</p>
              </b-carousel-slide>
              <b-carousel-slide
                v-else
                v-for="hotplace in hotplaces"
                :key="hotplace.articleNo"
                v-bind="hotplace"
                :img-src="hotplace.firstImg || require('@/assets/img/noimg.jpg')"
                @error="(event) => (event.target.src = require('@/assets/img/noimg.jpg'))"
              >
                <b-card no-body class="overflow-hidden hotplace-card" border-varient="secondary">
                  <b-row no-gutters>
                    <b-col>
                      <div class="card-img-container">
                        <b-card-img
                          :src="hotplace.firstImg || require('@/assets/img/noimg.jpg')"
                          @error="(event) => (event.target.src = require('@/assets/img/noimg.jpg'))"
                          alt="Image"
                          class="rounded-0 h-100 img-object-fit"
                        ></b-card-img>
                      </div>
                    </b-col>
                    <b-col>
                      <b-card-body :title="hotplace.title.length > 13 ? hotplace.title.slice(0, 13) + '...' : hotplace.title" class="h-100">
                        <b-card-sub-title class="mb-2">
                          {{ hotplace.userId }}ㆍ{{ hotplace.hit }}회 조회
                        </b-card-sub-title>
                        <b-card-text>
                          {{ hotplace.subject.length > 7 ? hotplace.subject.slice(0, 9) + '...' : hotplace.subject }}
                        </b-card-text>
                      </b-card-body>
                    </b-col>
                  </b-row>

                  <template #footer>
                    <b-row>
                      <b-col>
                        <span class="hotplace-regist-date"
                          >작성일: {{ hotplace.registerTime | fullDateFormat }}
                        </span>
                      </b-col>
                      <b-col>
                        <b-button varient="outline-secondary" size="sm">
                          <router-link
                            :to="{
                              name: 'hotplaceboardview',
                              params: { articleNo: hotplace.articleNo },
                            }"
                            style="color: white"
                            >자세히 보기</router-link
                          >
                        </b-button>
                      </b-col>
                    </b-row>
                  </template>
                </b-card>
              </b-carousel-slide>
            </b-carousel>
          </b-row>
        </b-col>
      </b-row>
      <b-row class="mb-2">
        <b-col class="center-col" style="background-color: #fffdfd">
          <b-row>
            <span class="board-name">이달의 여행계획</span>
          </b-row>
          <b-table-simple :hover="tripplans.length !== 0" responsive>
            <b-thead>
              <b-tr>
                <b-th>글번호</b-th>
                <b-th>여행일정</b-th>
                <b-th>여행코스</b-th>
                <b-th>작성자</b-th>
                <b-th>조회수</b-th>
                <b-th>작성일</b-th>
              </b-tr>
            </b-thead>
            <b-tbody v-if="tripplans.length === 0">
              <b-tr>
                <b-td class="empty-article" colspan="6"
                  >이번 달에 작성된 여행계획이 없습니다.
                </b-td>
              </b-tr>
            </b-tbody>
            <b-tbody v-else>
              <b-tr
                v-for="(tripplan, index) in tripplans"
                :key="tripplan.articleNo"
                v-bind="tripplan"
              >
                <b-td class="vertical-align-middle article-number p-8">{{
                  tripplan.articleNo
                }}</b-td>
                <b-td class="vertical-align-middle trip-plan-date">
                  {{ tripplan.startDate | monthlyDateFormat }} -
                  {{ tripplan.endDate | monthlyDateFormat }}
                </b-td>
                <b-td class="monthly-plan-title">
                  <b-row class="ml-1">
                    <router-link
                      :to="{
                        name: 'tripplanboardview',
                        params: { articleNo: tripplan.articleNo },
                      }"
                      class="plan-subject"
                      >{{ tripplan.subject }}</router-link
                    >
                  </b-row>
                  <b-row class="ml-1 plan-title">{{ plandetailTitles[index] }}</b-row>
                </b-td>
                <b-td class="vertical-align-middle article-author p-8">{{ tripplan.userId }}</b-td>
                <b-td class="vertical-align-middle article-hit p-8">{{ tripplan.hit }}</b-td>
                <b-td class="vertical-align-middle article-regist-date p-8">
                  {{ tripplan.registerTime | fullDateFormat }}
                </b-td>
              </b-tr>
            </b-tbody>
          </b-table-simple>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import http from "@/api/http";

export default {
  name: "AppMain",
  components: {},
  data() {
    return {
      notices: [],
      hotplaces: [],
      tripplans: [],
      plandetails: [],
      plandetailTitles: [],
      plandetailAreas: [],
      slide: 0,
      sliding: null,
    };
  },
  methods: {
    async getAdminBoard() {
      await http
        .get("/adminboard", {
          params: {
            spp: 5,
          },
        })
        .then(({ data }) => {
          this.notices = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getHotPlace() {
      const currentDate = new Date();
      await http
        .get("/hotplaceboard/monthly", {
          params: {
            year: currentDate.getFullYear(),
            month: currentDate.getMonth() + 1,
          },
        })
        .then(({ data }) => {
          this.hotplaces = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
    async getTripPlan() {
      const currentDate = new Date();
      await http
        .get("/tripplanboard/monthly", {
          params: {
            year: currentDate.getFullYear(),
            month: currentDate.getMonth() + 1,
          },
        })
        .then(({ data }) => {
          this.tripplans = data;
          this.getPlanDetail();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    async getPlanDetail() {
      for (const plan of this.tripplans) {
        const { data } = await http.get(`/tripplanboard/${plan.articleNo}`, {
          params: {
            location: "main",
          },
        });
        this.plandetails.push(data.detailPlan);
      }
      this.getPlanDetailTitles();
    },
    getPlanDetailTitles() {
      for (const value of this.plandetails) {
        let titles = value[0].title;
        for (var i = 1; i < value.length; i++) {
          titles += " -> ";
          titles += value[i].title;
        }
        this.plandetailTitles.push(titles);
      }
    },
    hotplaceView(articleNo) {
      alert(articleNo);
    },
  },
  created() {
    this.getAdminBoard();
    this.getHotPlace();
    this.getTripPlan();
  },
  filters: {
    fullDateFormat(value) {
      return value.toString().substring(2, 10);
    },
    monthlyDateFormat(value) {
      return value.toString().substring(5, 10).replaceAll("-", ".");
    },
  },
};
</script>

<style>
.board-name {
  margin-left: 3%;
  font-size: 32px;
}

.empty-article {
  color: gray;
  opacity: 80%;
}

.tripplan-date {
  font-size: small;
}

.vertical-align-middle {
  vertical-align: middle !important;
}

.p-8 {
  padding: 8px !important;
}

.plan-subject {
  font-size: larger;
}

.plan-title {
  color: gray;
}

.hotplace-card {
  color: black;
  width: 322px;
  height: 218px;
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  color: black;
}

.card-footer {
  padding: 8px;
}

.hotplace-regist-date {
  color: gray;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.img-fluid {
  max-height: 300px;
  filter: blur(8px);
  -webkit-filter: blur(8px);
}

#hotplace-carousel {
  min-width: 460px;
  max-width: 460px;
}

@media (max-width: 992px) {
  .left-col {
    margin-left: 2%;
  }

  .right-col {
    margin-right: 2%;
  }

  .center-col {
    margin-left: 2%;
    margin-right: 2%;
  }
}

@media (min-width: 992px) and (max-width: 1200px) {
  .left-col {
    margin-left: 6%;
    margin-right: 1%;
  }

  .right-col {
    margin-right: 6%;
    margin-left: 1%;
  }

  .center-col {
    margin-left: 6%;
    margin-right: 6%;
  }

  .trip-plan-date {
    width: 140px;
  }

  .article-number {
    width: 80px;
  }

  .article-hit {
    width: 80px;
  }

  .article-author {
    width: 130px;
  }

  .article-regist-date {
    width: 120px;
  }
}

@media (min-width: 1200px) {
  .left-col {
    margin-left: 10%;
    margin-right: 2%;
  }

  .right-col {
    margin-right: 10%;
    margin-left: 2%;
  }

  .center-col {
    margin-left: 10%;
    margin-right: 10%;
  }

  .trip-plan-date {
    width: 140px;
  }

  .article-number {
    width: 80px;
  }

  .article-hit {
    width: 80px;
  }

  .article-author {
    width: 130px;
  }

  .article-regist-date {
    width: 120px;
  }

  .subject {
    font-size: 50px;
  }
  .card-img-container {
    width: 100%;
    padding-top: 100%; /* 대부분의 비디오 비율이 16:9이므로 56.25%(9/16)를 사용합니다. */
    position: relative;
  }

  .img-object-fit {
    position: absolute;
    top: 0;
    left: 0;
    width: 10px;
    height: 10px;
    object-fit: cover;
    object-position: center;
  }
}
</style>
