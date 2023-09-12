<template>
  <b-container class="mt-5 mxw-8">
    <h3 class="f-big" style="text-align: start;"></h3>
    <b-row>
      <b-table stacked :fields="fields" :items="items()">
        <template #cell(email)="data">
          {{ data.item.emailId }}@{{ data.item.emailDomain }}
        </template>
        <template #cell(phone)="data">
          {{ data.item.phoneNumber | formatPhoneNumber }}
        </template>
        <template #cell(area)>
          {{ area }}
        </template>
      </b-table>
    </b-row>
    <b-row>
      <b-button class="mx-1" v-b-modal.verify-modal>정보 변경</b-button>
      <b-button class="mx-1" v-b-modal.remove-modal>회원 탈퇴</b-button>
    </b-row>
    <member-remove-modify-modal @changeArea="getArea"></member-remove-modify-modal>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
import { areaName } from "@/api/area";
import MemberRemoveModifyModal from "./MemberRemoveModifyModal.vue";

const memberStore = "memberStore";

export default {
  name: "MemberMyPage",
  components: {
    MemberRemoveModifyModal,
  },
  data() {
    return {
      fields: [
        { key: "userId", label: "아이디" },
        { key: "userName", label: "이름" },
        { key: "email", label: "이메일" },
        { key: "phone", label: "전화번호" },
        { key: "area", label: "지역" },
        { key: "joinDate", label: "가입일" },
      ],
      area: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  mounted() {
    if (this.userInfo == null) {
      this.$router.push({ path: "/" });
      return;
    }
    this.getArea();
  },
  methods: {
    items() {
      return [this.userInfo];
    },
    getArea() {
      let code = {
        sido: this.userInfo.userSido,
        gugun: this.userInfo.userGugun,
      };
      areaName(
        code,
        ({ data }) => {
          const name = data.sidoName.concat(" ").concat(data.gugunName);
          this.area = name;
        },
        (error) => console.log(error)
      );
    },
  },
  filters: {
    formatPhoneNumber(value) {
      if (value.length === 11) {
        return value.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
      } else {
        return value;
      }
    },
  },
};
</script>

<style>
.mxw-8 {
  max-width: 700px;
}
.f-big {
  font-size: 40px;
}
.table.b-table.b-table-stacked {
  font-size: 20px;
}
.table.b-table.b-table-stacked > tbody > tr > [data-label]::before {
  width: 20%;
}
</style>
