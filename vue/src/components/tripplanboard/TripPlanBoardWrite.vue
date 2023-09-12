<template>
  <div>
    <b-container>
      <b-row style="margin-top: 10px;">
        <b-col cols="12 mb-2">
          <h2 class="my-0 shadow-sm bg-light text-center">
            <mark class="sky">글쓰기</mark>
          </h2>
        </b-col>
        <b-col cols="12" class="text-left">
          <b-form>
            <div class="mb-3 mt-3">
              <b-row>
                <span style="font-size: 1.5rem; margin-left: 10px;">제목: </span>
                <b-col  md="5">
                  <b-form-input v-model="subject" type="text" style="border-color: black;"></b-form-input>
                </b-col>
                <b-col cols="auto" class="d-flex align-items-center">
                  <span style="font-size: 1.5rem;" class="text-nowrap">전체 날짜: </span>
                  <date-picker v-model="startDate" type="date" style="border-color: black; margin-left:10px; margin-right: 10px;" placeholder="시작" open-on-focus :disabled-date="notBeforeToday"></date-picker>
                  <span style="font-size: 1.5rem;">-</span>
                  <date-picker v-model="endDate" type="date" style="border-color: black; margin-left:10px; margin-right: 10px;" placeholder="종료" open-on-focus :disabled-date="notBeforeToday"></date-picker>
                </b-col>
              </b-row>
              <div v-for="(plan,index) in tripPlan" :key="index" v-bind="plan" style="width: 100%; margin-top: 10px;">
                <b-row>
                  <b-col>
                    <hr class="border-top">
                  </b-col>
                </b-row>
                <b-row>
                  <b-col cols="auto" style="margin-right:0px; padding-right:3px;">
                    <h2>
                      {{index+1}}. {{plan.title}}
                    </h2>
                  </b-col>
                  <b-col cols="auto" style="margin-top: 1%; margin-left: 0px; padding-left: 0px;">
                    {{plan.addr1}}
                  </b-col>
                  <b-col cols="auto" style="margin-top: 10px;">
                    <div class="text-box" :title="plan.overview">{{ plan.overview }}</div>
                  </b-col>
                </b-row>
                <b-row class="mb-2" style="margin-left: 5px;">
                  <b-col cols="auto" class="d-flex align-items-center">
                    <span style="font-size: 1.5rem;" class="text-nowrap">날짜: </span>
                    <date-picker v-model="detailPlan[index].detailStartDate" type="date" style="border-color: black; margin-left:10px; margin-right: 10px;" placeholder="시작" :disabled-date="notBeforeTrip"></date-picker>
                    <span style="font-size: 1.5rem;">-</span>
                    <date-picker v-model="detailPlan[index].detailEndDate" type="date" style="border-color: black; margin-left:10px; margin-right: 10px;" placeholder="종료" :disabled-date="notBeforeTrip" @input="newMinDate"></date-picker>
                  </b-col>
                </b-row>
                <b-row>
                  <b-col cols="12">
                    <b-form-textarea v-model="detailPlan[index].content" rows="12" style="border-color: black; width: 100%;" :placeholder="`${plan.title}`+` 여행계획`"></b-form-textarea>
                  </b-col>
                </b-row>
              </div>
            </div>
            <b-col cols='auto' class="text-center">
              <b-button class="btn btn-outline-primary mb-3" style="background-color: white;" @click="write">
                글작성
              </b-button>
              <span style="margin: 0 10px;"></span> <!-- 빈 칸 -->
              <b-button class="btn btn-outline-danger mb-3" style="background-color: white;" @click='list'>
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
import DatePicker from 'vue2-datepicker';
import 'vue2-datepicker/index.css';

export default {
  props: {
    user: {
      type:String,
    },
    tripPlan: {
      type: Array,
    },
  },
  components: {
    DatePicker,
  },
  name: "TripPlanBoardWrite",
  data() {
    return {
      minDate: "",
      maxDate: "",
      subject: "",
      startDate: "",
      endDate: "",
      detailPlan: [],
      searchParam_list:{
        key: null,
        word: null,
        spp: 20,
        pg: 1,
        num: null,
      },
    }
  },
  watch:{
    startDate() {
      this.minDate = this.startDate;
    },
    endDate() {
      this.maxDate = this.endDate;
    },
  },
  computed: {
    userId() {
      return this.user;
    },
  },
  created() {
  this.tripPlan.forEach((trip) => {
    this.detailPlan.push({
      title: trip.title,
      contentId: trip.contentId,
      addr1: trip.addr1,
      overview: trip.overview,
      content: "",
      detailStartDate: "",
      detailEndDate: "",
    });
  });
  console.log(this.tripPlan);
  },
  methods: {
    write() {
      http.post("/tripplanboard/write", {
        subject: this.subject,
        userId: this.userId,
        startDate: this.startDate,
        endDate: this.endDate,
        detailPlan:this.detailPlan,
    })
      .then(() => {
        this.$emit('search', this.searchParam_list);
        this.$router.push({ name: 'tripplanboardlist' });
    })
    },
    list() {
      this.$router.push({ name: 'tripplanboardlist' });
    },
    notBeforeToday(date) {
      return date < new Date();
    },
    notBeforeTrip(date) {
      return date < this.minDate || date>this.maxDate;
      
    },
    newMinDate(date) {
        //this.minDate = date;
        console.log(date)
    }
  }
};
</script>

<style scoped>
.text-box {
  max-height: 100px;  /* 원하는 높이로 설정 */
  overflow-y: auto;   /* 스크롤바 추가 */
}
</style>
