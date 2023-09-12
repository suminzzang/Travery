<template>
  <div>
    <b-modal
      id="remove-modal"
      title="회원 탈퇴"
      @show="openRemoveModal"
      @hidden="closeRemoveModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form>
        <b-form-group label="비밀번호 확인" label-for="delete-password">
          <b-form-row>
            <b-form-input
              id="delete-password"
              v-model="deletePassword"
              type="password"
              :autofocus="deletePasswordInput"
            ></b-form-input>
          </b-form-row>
        </b-form-group>
      </b-form>
      <template #modal-footer="{ cancel }">
        <div>
          <b-button class="mx-1" variant="danger" @click="remove()">탈퇴</b-button>
          <b-button class="mx-1" variant="primary" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>

    <b-modal
      id="verify-modal"
      title="내 정보 변경"
      @show="openVerifyModal"
      @hidden="closeVerifyModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form>
        <b-form-group label="비밀번호 확인" label-for="verify-password">
          <b-form-row>
            <b-form-input
              id="verify-password"
              v-model="verifyPassword"
              type="password"
              :autofocus="verifyPasswordInput"
            ></b-form-input>
          </b-form-row>
        </b-form-group>
      </b-form>
      <template #modal-footer="{ cancel }">
        <div>
          <b-button class="mx-1" variant="primary" @click="verifyUser()">확인</b-button>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>

    <b-modal
      id="modify-modal"
      title="내 정보 변경"
      @show="openModifyModal"
      @hidden="closeModifyModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="modify">
        <b-form-group label="아이디" label-for="modify-id">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="modify-id"
                v-model="modifyUser.userId"
                type="text"
                readonly
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="새 비밀번호" label-for="modify-pwd">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="modify-pwd"
                v-model="modifyUser.userPwd"
                type="password"
                placeholder="비밀번호 입력"
                @input="pwValidation"
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="새 비밀번호 확인" label-for="modify-pwd-chk">
          <b-form-row class="mb-1">
            <b-col>
              <b-form-input
                id="modify-pwd-chk"
                v-model="modifyUser.pwdCheck"
                type="password"
                placeholder="비밀번호 확인"
                @input="pwValidation"
              ></b-form-input>
            </b-col>
          </b-form-row>
          <b-form-row class="validation-message">
            <b-col>
              <span class="invalid-msg" v-if="passwordValidationMessage === 'invalid-length'">
                비밀번호는 5자 이상 32자 이하여야 합니다.
              </span>
              <span
                class="invalid-msg"
                v-else-if="passwordValidationMessage === 'invalid-different'"
              >
                비밀번호가 일치하지 않습니다.
              </span>
              <span class="valid-msg" v-else-if="passwordValidationMessage === 'valid-password'">
                비밀번호가 일치합니다.
              </span>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="이름" label-for="modify-name">
          <b-form-row class="mb-3">
            <b-form-input
              id="modify-name"
              v-model="modifyUser.userName"
              type="text"
              placeholder="이름 입력"
            ></b-form-input>
          </b-form-row>
        </b-form-group>
        <b-form-group label="전화번호" label-for="modify-phone">
          <b-form-row class="mb-3">
            <b-form-input
              id="modify-phone"
              v-model="modifyUser.phoneNumber"
              type="text"
              placeholder="전화번호 입력"
              maxlength="11"
            ></b-form-input>
          </b-form-row>
        </b-form-group>
        <b-form-group label="이메일" label-for="modify-name">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="modify-emailid"
                v-model="modifyUser.emailId"
                type="text"
                placeholder="이메일 아이디 입력"
              ></b-form-input> </b-col
            ><input
              type="text"
              class="form-control"
              style="font-size: 16px; width: 40px"
              value="@"
              readonly
            /><b-col>
              <b-form-input
                id="modify-emaildomain"
                v-model="modifyUser.emailDomain"
                placeholder="이메일 서버 입력"
                type="text"
              ></b-form-input
            ></b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="지역" label-for="signup-area">
          <b-form-row class="mb-1">
            <b-col>
              <b-form-select v-model="modifyUser.userSido" :options="sidos" @change="gugunList">
                시/도 선택
              </b-form-select>
            </b-col>
            <b-col>
              <b-form-select v-model="modifyUser.userGugun" :options="guguns">
                시/군/구 선택
              </b-form-select>
            </b-col>
          </b-form-row>
        </b-form-group>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div>
          <b-button class="mx-1" variant="primary" @click="modify()">변경</b-button>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import { verify, modify } from "@/api/member";

const memberStore = "memberStore";
const areaStore = "areaStore";

export default {
  name: "MemberRemoveModifyModal",
  data() {
    return {
      deletePassword: "",
      verifyPassword: "",
      verified: false,
      modifyUser: {
        userId: "",
        userPwd: "",
        pwdCheck: "",
        userName: "",
        phoneNumber: "",
        emailId: "",
        emailDomain: "",
        userSido: null,
        userGugun: null,
      },
      passwordValidationMessage: null,
      deletePasswordInput: false,
      verifyPasswordInput: false,
    };
  },
  computed: {
    ...mapState(areaStore, ["sidos", "guguns"]),
    ...mapState(memberStore, ["userInfo", "isLogin"]),
  },
  // mounted() {
  //   this.resetModifyModal();
  // },
  methods: {
    ...mapActions(areaStore, ["getSido", "getGugun"]),
    ...mapMutations(areaStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    ...mapActions(memberStore, [
      "deleteAccount",
      "modifyAccount",
      "getUserInfo",
      "tokenRegeneration",
    ]),
    ...mapMutations(memberStore, ["SET_USER_INFO"]),
    resetVerifyModal() {
      this.verified = false;
      this.verifyPassword = "";
    },
    openVerifyModal() {
      this.resetVerifyModal();
      window.addEventListener("keydown", this.handleVerifyKeyDown);
      this.verifyPasswordInput = true;
    },
    closeVerifyModal() {
      this.resetVerifyModal();
      window.removeEventListener("keydown", this.handleVerifyKeyDown);
      this.verifyPasswordInput = false;
    },
    handleVerifyKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.verifyUser();
      }
    },
    resetRemoveModal() {
      this.deletePassword = "";
    },
    openRemoveModal() {
      this.resetRemoveModal();
      window.addEventListener("keydown", this.handleRemoveKeyDown);
      this.deletePasswordInput = true;
    },
    closeRemoveModal() {
      this.resetRemoveModal();
      window.removeEventListener("keydown", this.handleRemoveKeyDown);
      this.deletePasswordInput = false;
    },
    handleRemoveKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.remove();
      }
    },
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.modifyUser.userGugun = null;
      if (this.modifyUser.userSido) this.getGugun(this.modifyUser.userSido);
    },
    resetModifyModal() {
      this.getSido();
      this.modifyUser.userId = this.userInfo.userId;
      this.modifyUser.userPwd = "";
      this.modifyUser.pwdCheck = "";
      this.modifyUser.userName = this.userInfo.userName;
      this.modifyUser.phoneNumber = this.userInfo.phoneNumber;
      this.modifyUser.emailId = this.userInfo.emailId;
      this.modifyUser.emailDomain = this.userInfo.emailDomain;
      this.modifyUser.userSido = this.userInfo.userSido;
      this.gugunList();
      this.modifyUser.userGugun = this.userInfo.userGugun;
      this.passwordValidationMessage = null;
    },
    openModifyModal() {
      this.resetModifyModal();
      window.addEventListener("keydown", this.handleModifyKeyDown);
    },
    closeModifyModal() {
      this.resetModifyModal();
      window.removeEventListener("keydown", this.handleModifyKeyDown);
    },
    handleModifyKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.modify();
      }
    },
    pwValidation() {
      if (this.modifyUser.userPwd.length < 5 || this.modifyUser.userPwd.length > 32) {
        this.passwordValidationMessage = "invalid-length";
      } else if (this.modifyUser.userPwd != this.modifyUser.pwdCheck) {
        this.passwordValidationMessage = "invalid-different";
      } else {
        this.passwordValidationMessage = "valid-password";
      }
    },
    async remove() {
      const user = {
        userId: this.userInfo.userId,
        userPwd: this.deletePassword,
      };
      if (!confirm("정말로 탈퇴하시겠습니까?")) {
        this.$nextTick(() => {
          this.$bvModal.hide("remove-modal");
        });
        return;
      }
      await this.deleteAccount(user);
      if (!this.isLogin) {
        alert("탈퇴 성공!");
        this.$nextTick(() => {
          this.$bvModal.hide("remove-modal");
        });
        if (this.$route.name != "main") this.$router.push({ path: "/" });
        this.SET_USER_INFO(null);
      } else if (!this.deletePassword) {
        alert("비밀번호를 입력하세요.");
      } else {
        alert("비밀번호가 다릅니다.");
      }
    },
    async verifyUser() {
      if (!this.verifyPassword) {
        alert("비밀번호를 입력하세요.");
        return;
      }
      const user = {
        userId: this.userInfo.userId,
        userPwd: this.verifyPassword,
      };
      await verify(
        user,
        ({ data }) => {
          if (data.message === "success") {
            this.verified = true;
          } else {
            this.verified = false;
          }
        },
        (error) => {
          alert("에러 발생!");
          console.log(error);
        }
      );
      if (this.verified) {
        this.$nextTick(() => {
          this.$bvModal.hide("verify-modal");
          this.$bvModal.show("modify-modal");
        });
      } else {
        alert("비밀번호가 다릅니다.");
      }
    },
    async modify() {
      if (
        !this.modifyUser.userId ||
        !this.modifyUser.userPwd ||
        !this.modifyUser.pwdCheck ||
        !this.modifyUser.userName ||
        !this.modifyUser.phoneNumber ||
        !this.modifyUser.emailId ||
        !this.modifyUser.emailDomain ||
        this.modifyUser.userSido == null ||
        this.modifyUser.userGugun == null
      ) {
        alert("입력하지 않은 항목이 있습니다.");
        return;
      } else if (this.passwordValidationMessage != "valid-password") {
        if (this.passwordValidationMessage === "invalid-length") {
          alert("비밀번호는 5자 이상 32자 이하여야 합니다.");
        } else if (this.passwordValidationMessage === "invalid-different") {
          alert("비밀번호가 일치하지 않습니다.");
        }
        return;
      } else {
        await modify(
          this.modifyUser,
          async ({ data }) => {
            if (data.message === "success") {
              let token = sessionStorage.getItem("access-token");
              await this.getUserInfo(token);
              alert("회원 정보 변경 성공!");
              this.$emit("changeArea");
            } else {
              alert("회원 정보 변경 실패!");
            }
            this.$nextTick(() => {
              this.$bvModal.hide("modify-modal");
            });
          },
          async (error) => {
            if (error.response.status == 401) {
              console.log(
                "modify() error code [토큰 만료되어 사용 불가능.] ::: ",
                error.response.status
              );
              this.SET_IS_VALID_TOKEN(false);
              await this.tokenRegeneration();
            } else {
              alert("알 수 없는 에러 발생");
            }
          }
        );
      }
    },
  },
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Jua&display=swap");

* {
  font-family: "Jua", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
