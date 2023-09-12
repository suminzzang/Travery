<template>
  <div>
    <!-- 로그인 모달 시작 -->
    <b-modal
      id="login-modal"
      title="로그인"
      @show="openLoginModal"
      @hidden="closeLoginModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="login">
        <b-form-row class="mb-3">
          <b-form-input
            id="login-id"
            v-model="loginUser.userId"
            type="text"
            placeholder="아이디"
            :autofocus="loginIdInput"
          ></b-form-input>
        </b-form-row>
        <b-form-row class="mb-3">
          <b-form-input
            id="login-pwd"
            v-model="loginUser.userPwd"
            type="password"
            placeholder="비밀번호"
          ></b-form-input
        ></b-form-row>
        <b-form-row class="validation-message">
          <b-col>
            <span
              class="invalid-msg"
              v-if="loginValidationMessage === 'invalid-login-id' && !loginUser.userId"
            >
              아이디를 입력하세요
            </span>
            <span
              class="invalid-msg"
              v-else-if="loginValidationMessage === 'invalid-login-pw' && !loginUser.userPwd"
            >
              비밀번호를 입력하세요
            </span>
            <span class="invalid-msg" v-else-if="loginValidationMessage === 'invalid-login-input'">
              아이디 또는 비밀번호가 틀립니다.
            </span>
          </b-col>
        </b-form-row>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div>
          <b-button size="sm" class="mx-1" variant="outline-info" @click="findId()">
            아이디 찾기
          </b-button>
          <b-button size="sm" class="mx-1" variant="outline-info" @click="findPw()">
            비밀번호 찾기
          </b-button>
        </div>
        <div class="ml-auto">
          <b-button class="mx-1" variant="primary" @click="login()">로그인</b-button>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
    <!-- 로그인 모달 끝 -->

    <!-- 회원가입 모달 시작 -->
    <b-modal
      id="signup-modal"
      @show="openSignupModal"
      @hidden="closeSignupModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="signup">
        <b-form-group label="아이디" label-for="signup-id">
          <b-form-row class="mb-1">
            <div class="col">
              <b-form-input
                id="signup-id"
                v-model="signupUser.userId"
                type="text"
                placeholder="아이디 입력"
                @input="idValidation"
                :readonly="usableId"
                :autofocus="signupIdInput"
                ref="signupIdInput"
              ></b-form-input>
            </div>
            <div class="col-auto">
              <b-button
                @click="duplicateCheck"
                :disabled="
                  this.signupUser.userId.length < 4 ||
                  this.signupUser.userId.length > 24 ||
                  !signupUser.userId
                "
                right
                >{{ idCheckButtonText }}</b-button
              >
            </div>
          </b-form-row>
          <b-form-row class="validation-message">
            <b-col>
              <span class="invalid-msg" v-if="idValidationMessage === 'invalid-length'">
                아이디는 4자 이상 16자 이하여야 합니다.
              </span>
              <span class="invalid-msg" v-else-if="idValidationMessage === 'duplicated-id'">
                이미 존재하는 아이디입니다.
              </span>
              <span class="valid-msg" v-else-if="idValidationMessage === 'valid-id'">
                사용 가능한 아이디입니다.
              </span>
              <span class="invalid-msg" v-else-if="idValidationMessage === 'need-duplicate-check'">
                중복 검사가 필요합니다.
              </span>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="비밀번호" label-for="signup-pwd">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="signup-pwd"
                v-model="signupUser.userPwd"
                type="password"
                placeholder="비밀번호 입력"
                @input="pwValidation"
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="비밀번호 확인" label-for="signup-pwd-chk">
          <b-form-row class="mb-1">
            <b-col>
              <b-form-input
                id="signup-pwd-chk"
                v-model="signupUser.pwdCheck"
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
        <b-form-group label="이름" label-for="signup-name">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="signup-name"
                v-model="signupUser.userName"
                type="text"
                placeholder="이름 입력"
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="전화번호" label-for="signup-phone">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="signup-phone"
                v-model="signupUser.phoneNumber"
                type="text"
                placeholder="전화번호 입력"
                maxlength="11"
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="이메일" label-for="signup-name">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="signup-emailid"
                v-model="signupUser.emailId"
                type="text"
                placeholder="이메일 아이디 입력"
                :readonly="usableEmail || mailSending"
                @input="resetEmailMessage"
              ></b-form-input>
            </b-col>
            <input
              type="text"
              class="form-control"
              style="font-size: 16px; width: 40px"
              value="@"
              readonly
            />
            <b-col>
              <b-form-input
                id="signup-emaildomain"
                v-model="signupUser.emailDomain"
                placeholder="이메일 서버 입력"
                type="text"
                :readonly="usableEmail || mailSending"
                @input="resetEmailMessage"
              >
              </b-form-input>
            </b-col>
            <div class="col-auto">
              <b-button
                variant="outline-secondary"
                @click="emailSend"
                :disabled="usableEmail || mailSending"
              >
                인증하기
              </b-button>
            </div>
          </b-form-row>
          <b-form-row class="validation-message">
            <b-col>
              <span
                class="invalid-msg"
                v-if="
                  emailValidationMessage === 'need-signup-email' &&
                  (!signupUser.emailId || !signupUser.emailDomain)
                "
              >
                이메일을 입력하세요.
              </span>
              <span
                class="invalid-msg"
                v-else-if="emailValidationMessage === 'duplicated-signup-email'"
              >
                이미 가입된 이메일입니다.
              </span>
              <span
                class="invalid-msg"
                v-else-if="emailValidationMessage === 'need-email-duplicate-check'"
              >
                이메일 인증이 필요합니다.
              </span>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="지역" label-for="signup-area">
          <b-form-row class="mb-1" id="signup-area">
            <b-col>
              <b-form-select v-model="signupUser.userSido" :options="sidos" @change="gugunList">
                시/도 선택
              </b-form-select>
            </b-col>
            <b-col>
              <b-form-select v-model="signupUser.userGugun" :options="guguns">
                시/군/구 선택
              </b-form-select>
            </b-col>
          </b-form-row>
        </b-form-group>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div>
          <b-button class="mx-1" variant="primary" @click="signup()">회원가입</b-button>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
    <!-- 회원가입 모달 끝-->

    <!-- 메일 인증 모달 시작 -->
    <b-modal
      id="email-verify-modal"
      title="이메일 인증"
      @show="openVerifyModal"
      @hidden="closeVerifyModal"
      no-close-on-backdrop
      hide-header-close
      size="sm"
    >
      <b-form>
        <b-form-group label="인증번호 입력" label-for="verify-password">
          <b-form-row>
            <b-col>
              <b-form-input
                id="verify-authkey"
                v-model="verifyAuthkey"
                type="text"
                :autofocus="verifyAuthkeyInput"
                @input="resetKeyValidationMessage"
              ></b-form-input>
            </b-col>
            <div class="col-auto">
              <b-button variant="outline-primary" @click="emailVerify">확인</b-button>
            </div>
          </b-form-row>
          <b-form-row class="validation-message">
            <b-col>
              <span class="invalid-msg" v-if="keyValidationMessage === 'invalid-authkey'">
                인증 번호가 올바르지 않습니다.
              </span>
            </b-col>
          </b-form-row>
        </b-form-group>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
    <!-- 메일 인증 모달 끝-->

    <!-- 아이디 찾기 모달 시작 -->
    <b-modal
      id="find-id-modal"
      title="아이디 찾기"
      @show="openFindIdModal"
      @hidden="closeFindIdModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="findId">
        <b-form-row class="mb-3">
          <b-form-input
            id="find-id-email"
            v-model="findIdUserEmail"
            type="email"
            placeholder="이메일"
            :autofocus="loginIdInput"
            :readonly="mailSending"
          ></b-form-input>
        </b-form-row>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div class="ml-auto">
          <b-button class="mx-1" variant="primary" :disabled="mailSending" @click="sendFindId()"
            >메일전송</b-button
          >
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
    <!-- 아이디 찾기 모달 끝 -->

    <!-- 비밀번호 찾기 모달 시작 -->
    <b-modal
      id="find-pw-modal"
      title="비밀번호 찾기"
      @show="openFindPwModal"
      @hidden="closeFindPwModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="findPw">
        <b-form-row class="mb-3">
          <b-col>
            <b-form-input
              id="find-pw-id"
              v-model="findPwUser.userId"
              type="text"
              placeholder="아이디"
              :autofocus="loginIdInput"
            ></b-form-input>
          </b-col>
        </b-form-row>
        <b-form-row class="mb-3">
          <b-col>
            <b-form-input
              id="find-pw-emailid"
              v-model="findPwUser.emailId"
              type="text"
              placeholder="이메일 아이디 입력"
              :readonly="mailSending"
            ></b-form-input></b-col
          ><input
            type="text"
            class="form-control"
            style="font-size: 16px; width: 40px"
            value="@"
            readonly
          /><b-col>
            <b-form-input
              id="find-pw-emaildomain"
              v-model="findPwUser.emailDomain"
              placeholder="이메일 서버 입력"
              type="text"
              :readonly="mailSending"
            ></b-form-input
          ></b-col>
        </b-form-row>
      </b-form>

      <template #modal-footer="{ cancel }">
        <div class="ml-auto">
          <b-button class="mx-1" variant="primary" @click="sendFindPw()" :disabled="mailSending"
            >메일전송</b-button
          >
          <b-button class="mx-1" variant="danger" @click="cancel()" :disabled="mailSending"
            >취소</b-button
          >
        </div>
      </template>
    </b-modal>
    <!-- 비밀번호 찾기 모달 끝 -->

    <!-- 비밀번호 변경 모달 시작 -->
    <b-modal
      id="change-pw-modal"
      title="비밀번호 변경"
      @show="openChangePwModal"
      @hidden="closeChangePwModal"
      no-close-on-backdrop
      hide-header-close
    >
      <b-form @submit="changePassword">
        <b-form-group label="새 비밀번호" label-for="change-pwd">
          <b-form-row class="mb-3">
            <b-col>
              <b-form-input
                id="change-pwd"
                v-model="findPwUser.userPwd"
                type="password"
                placeholder="비밀번호 입력"
                @input="pwChangeValidation"
                :autofocus="loginIdInput"
              ></b-form-input>
            </b-col>
          </b-form-row>
        </b-form-group>
        <b-form-group label="새 비밀번호 확인" label-for="change-pwd-chk">
          <b-form-row class="mb-1">
            <b-col>
              <b-form-input
                id="change-pwd-chk"
                v-model="findPwUser.pwdCheck"
                type="password"
                placeholder="비밀번호 확인"
                @input="pwChangeValidation"
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
      </b-form>

      <template #modal-footer="{ cancel }">
        <div class="ml-auto">
          <b-button class="mx-1" variant="primary" @click="changePassword()">변경</b-button>
          <b-button class="mx-1" variant="danger" @click="cancel()">취소</b-button>
        </div>
      </template>
    </b-modal>
    <!-- 비밀번호 찾기 모달 끝 -->
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { idCheck, changepw, send, auth, sendid, sendpw } from "@/api/member";

const areaStore = "areaStore";
const memberStore = "memberStore";

export default {
  name: "SignModal",
  data() {
    return {
      loginUser: {
        userId: "",
        userPwd: "",
      },
      signupUser: {
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
      findPwUser: {
        userId: "",
        emailId: "",
        emailDomain: "",
        userPwd: "",
        pwdCheck: "",
      },
      findIdUserEmail: "",
      usableId: false,
      findingPassword: false,
      loginValidationMessage: null,
      idValidationMessage: null,
      emailValidationMessage: null,
      passwordValidationMessage: null,
      keyValidationMessage: null,
      idCheckButtonText: "중복검사",
      verifyAuthkey: "",
      usableEmail: false,
      loginIdInput: false,
      signupIdInput: false,
      verifyAuthkeyInput: false,
      mailSending: false,
    };
  },
  computed: {
    ...mapState(areaStore, ["sidos", "guguns"]),
    ...mapState(memberStore, ["isLogin", "isSignSuccess"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(areaStore, ["getSido", "getGugun"]),
    ...mapMutations(areaStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    ...mapActions(memberStore, ["userConfirm", "userSignUp", "getUserInfo"]),
    ...mapMutations(memberStore, ["SET_SIGN_UP_SUCCESS"]),
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.signupUser.userGugun = null;
      if (this.signupUser.userSido) this.getGugun(this.signupUser.userSido);
    },
    resetLoginModal() {
      this.loginUser = {
        userId: "",
        userPwd: "",
      };
      this.loginValidationMessage = null;
    },
    openLoginModal() {
      this.resetLoginModal();
      window.addEventListener("keydown", this.handleLoginKeyDown);
      this.loginIdInput = true;
    },
    closeLoginModal() {
      this.resetLoginModal();
      window.removeEventListener("keydown", this.handleLoginKeyDown);
      this.loginIdInput = false;
    },
    handleLoginKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.login();
      }
    },
    async login() {
      if (!this.loginUser.userId) {
        this.loginValidationMessage = "invalid-login-id";
        return;
      } else if (!this.loginUser.userPwd) {
        this.loginValidationMessage = "invalid-login-pw";
        return;
      } else {
        await this.userConfirm(this.loginUser);
        let token = sessionStorage.getItem("access-token");
        if (this.isLogin) {
          await this.getUserInfo(token);
          this.$nextTick(() => {
            this.$bvModal.hide("login-modal");
          });
        } else {
          this.loginValidationMessage = "invalid-login-input";
        }
      }
    },
    resetSignupModal() {
      this.CLEAR_GUGUN_LIST();
      this.signupUser = {
        userId: "",
        userPwd: "",
        pwdCheck: "",
        userName: "",
        phoneNumber: "",
        emailId: "",
        emailDomain: "",
        userSido: null,
        userGugun: null,
      };
      this.passwordValidationMessage = null;
      this.idValidationMessage = null;
      this.emailValidationMessage = null;
      this.idCheckButtonText = "중복검사";
      this.usableId = false;
      this.usableEmail = false;
    },
    openSignupModal() {
      this.resetSignupModal();
      window.addEventListener("keydown", this.handleSignupKeyDown);
      this.signupIdInput = true;
    },
    closeSignupModal() {
      this.resetSignupModal();
      window.removeEventListener("keydown", this.handleSignupKeyDown);
      this.signupIdInput = false;
    },
    handleSignupKeyDown(event) {
      if (event.key === "Enter") {
        this.signup();
      }
    },
    resetEmailMessage() {
      this.emailValidationMessage = "";
    },
    idValidation() {
      if (this.signupUser.userId.length < 4 || this.signupUser.userId.length > 24) {
        this.idValidationMessage = "invalid-length";
      } else {
        this.idValidationMessage = null;
      }
    },
    async duplicateCheck() {
      if (this.usableId) {
        this.usableId = false;
        this.idCheckButtonText = "중복검사";
        this.idValidationMessage = "";
        return;
      }
      await idCheck(
        this.signupUser.userId,
        ({ data }) => {
          if (data.message === "success") {
            this.idValidationMessage = "valid-id";
            this.usableId = true;
            this.idCheckButtonText = "아이디 변경";
          } else {
            this.idValidationMessage = "duplicated-id";
            this.usableId = false;
          }
        },
        (error) => {
          console.log(error);
          alert("알 수 없는 에러 발생");
        }
      );
    },
    pwValidation() {
      if (this.signupUser.userPwd.length < 5 || this.signupUser.userPwd.length > 32) {
        this.passwordValidationMessage = "invalid-length";
      } else if (this.signupUser.userPwd != this.signupUser.pwdCheck) {
        this.passwordValidationMessage = "invalid-different";
      } else {
        this.passwordValidationMessage = "valid-password";
      }
    },
    pwChangeValidation() {
      if (this.findPwUser.userPwd.length < 5 || this.findPwUser.userPwd.length > 32) {
        this.passwordValidationMessage = "invalid-length";
      } else if (this.findPwUser.userPwd != this.findPwUser.pwdCheck) {
        this.passwordValidationMessage = "invalid-different";
      } else {
        this.passwordValidationMessage = "valid-password";
      }
    },
    resetVerifyModal() {
      this.verifyAuthkey = "";
      this.mailSending = false;
      this.keyValidationMessage = "";
      this.emailValidationMessage = "";
    },
    resetKeyValidationMessage() {
      this.keyValidationMessage = "";
    },
    openVerifyModal() {
      this.resetVerifyModal();
      window.removeEventListener("keydown", this.handleSignupKeyDown);
      window.addEventListener("keydown", this.handleVerifyKeyDown);
      this.verifyPasswordInput = true;
    },
    closeVerifyModal() {
      this.resetVerifyModal();
      window.removeEventListener("keydown", this.handleVerifyKeyDown);
      window.addEventListener("keydown", this.handleSignupKeyDown);
      this.verifyPasswordInput = false;
    },
    handleVerifyKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.emailVerify();
      }
    },
    verifyValidation() {
      if (this.verifyAuthkey.length != 6) {
        this.keyValidationMessage = "invalid-authkey";
      }
    },
    async emailSend() {
      if (!this.signupUser.emailId || !this.signupUser.emailDomain) {
        this.emailValidationMessage = "need-signup-email";
        return;
      } else {
        const email = {
          emailId: this.signupUser.emailId,
          emailDomain: this.signupUser.emailDomain,
        };
        this.mailSending = true;
        await send(
          email,
          ({ data }) => {
            if (data.message === "success") {
              let token = data["mail-token"];
              sessionStorage.setItem("mail-token", token);
              alert("이메일이 전송되었습니다.");
              this.$nextTick(() => {
                this.$bvModal.show("email-verify-modal");
              });
              this.mailSending = false;
            } else {
              this.emailValidationMessage = "duplicated-signup-email";
              this.mailSending = false;
              return;
            }
          },
          (error) => {
            console.log(error);
            this.mailSending = false;
          }
        );
      }
    },
    async emailVerify() {
      if (this.verifyAuthkey.length != 6) {
        this.keyValidationMessage = "invalid-authkey";
        return;
      } else {
        const key = { mailKey: this.verifyAuthkey };
        await auth(
          key,
          ({ data }) => {
            if (data.message === "success") {
              alert("인증에 성공하였습니다.");
              this.usableEmail = true;
              this.$nextTick(() => {
                this.$bvModal.hide("email-verify-modal");
                if (this.findingPassword) {
                  this.findingPassword = false;
                  this.$bvModal.show("change-pw-modal");
                }
              });
            } else {
              this.keyValidationMessage = "invalid-authkey";
            }
          },
          (error) => {
            if (error.response.status == 401) {
              alert("인증 유효 시간이 지났습니다.");
            } else {
              alert("알 수 없는 에러 발생");
            }
            this.$nextTick(() => {
              this.$bvModal.hide("email-verify-modal");
            });
          }
        );
      }
    },
    async signup() {
      if (
        !this.signupUser.userId ||
        !this.signupUser.userPwd ||
        !this.signupUser.pwdCheck ||
        !this.signupUser.userName ||
        !this.signupUser.phoneNumber ||
        !this.signupUser.emailId ||
        !this.signupUser.emailDomain ||
        this.signupUser.userSido == null ||
        this.signupUser.userGugun == null
      ) {
        alert("입력하지 않은 항목이 있습니다.");
        return;
      } else if (this.signupUser.userId.length < 4 || this.signupUser.userId.length > 24) {
        this.idValidationMessage = "invalid-length";
        this.$refs.signupIdInput.focus();
        return;
      } else if (!this.usableId) {
        this.idValidationMessage = "need-duplicate-check";
        this.$refs.signupIdInput.focus();
        return;
      } else if (!this.usableEmail) {
        this.emailValidationMessage = "need-email-duplicate-check";
        return;
      } else if (this.passwordValidationMessage != "valid-password") {
        if (this.passwordValidationMessage === "invalid-length") {
          alert("비밀번호는 5자 이상 32자 이하여야 합니다.");
        } else if (this.passwordValidationMessage === "invalid-different") {
          alert("비밀번호가 일치하지 않습니다.");
        }
        return;
      } else {
        await this.userSignUp(this.signupUser);
        if (this.isSignSuccess) {
          alert("회원가입 성공!");
          this.$nextTick(() => {
            this.$bvModal.hide("signup-modal");
          });
          this.SET_SIGN_UP_SUCCESS(false);
        } else {
          alert("회원가입 실패!");
        }
      }
    },
    resetFindIdModal() {
      this.findIdUserEmail = "";
      this.mailSending = false;
    },
    openFindIdModal() {
      this.resetFindIdModal();
      window.addEventListener("keydown", this.handleFindIdKeyDown);
      this.loginIdInput = true;
    },
    closeFindIdModal() {
      this.resetFindIdModal();
      window.removeEventListener("keydown", this.handleFindIdKeyDown);
      this.loginIdInput = false;
    },
    handleFindIdKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.sendFindId();
      }
    },
    findId() {
      this.$bvModal.hide("login-modal");
      this.$bvModal.show("find-id-modal");
    },
    async sendFindId() {
      let regex = new RegExp("[a-z0-9]+@[a-z]+.[a-z]{2,3}");
      const mail = this.findIdUserEmail;
      if (mail === "") {
        alert("이메일을 입력하세요");
        return;
      } else if (!regex.test(mail)) {
        alert("이메일 형식이 올바르지 않습니다.");
        return;
      } else {
        this.mailSending = true;
        await sendid(
          mail,
          ({ data }) => {
            this.mailSending = false;
            if (data.message === "success") {
              alert("메일을 전송하였습니다.");
              this.$nextTick(() => {
                this.$bvModal.hide("find-id-modal");
              });
            } else {
              alert("존재하지 않는 이메일입니다.");
            }
          },
          (error) => {
            this.mailSending = false;
            console.log(error);
            alert("알 수 없는 에러 발생");
            this.$nextTick(() => {
              this.$bvModal.hide("email-verify-modal");
            });
          }
        );
      }
    },
    resetFindPwModal() {
      this.findPwUser = {
        userId: "",
        emailId: "",
        emailDomain: "",
        userPwd: "",
        pwdCheck: "",
      };
      this.mailSending = false;
    },
    openFindPwModal() {
      this.resetFindPwModal();
      window.addEventListener("keydown", this.handleFindPwKeyDown);
      this.loginIdInput = true;
    },
    closeFindPwModal() {
      window.removeEventListener("keydown", this.handleFindPwKeyDown);
      this.loginIdInput = false;
    },
    handleFindPwKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.sendFindPw();
      }
    },
    findPw() {
      this.$bvModal.hide("login-modal");
      this.$bvModal.show("find-pw-modal");
    },
    async sendFindPw() {
      this.findingPassword = true;
      const user = this.findPwUser;
      this.mailSending = true;
      await sendpw(
        user,
        ({ data }) => {
          this.mailSending = false;
          if (data.message === "success") {
            let token = data["mail-token"];
            sessionStorage.setItem("mail-token", token);
            alert("메일을 전송하였습니다.");
            this.$nextTick(() => {
              this.$bvModal.hide("find-pw-modal");
              this.$bvModal.show("email-verify-modal");
            });
          } else {
            alert("회원 정보가 존재하지 않습니다.");
          }
        },
        (error) => {
          this.mailSending = false;
          console.log(error);
          alert("알 수 없는 에러 발생");
          this.$nextTick(() => {
            this.$bvModal.hide("email-verify-modal");
            this.findingPassword = false;
          });
        }
      );
    },
    resetChangePwModal() {
      this.findPwUser.userPwd = "";
      this.findPwUser.pwdCheck = "";
    },
    openChangePwModal() {
      this.resetChangePwModal();
      window.addEventListener("keydown", this.handleChangePwKeyDown);
      this.loginIdInput = true;
    },
    closeChangePwModal() {
      this.resetChangePwModal();
      window.removeEventListener("keydown", this.handleChangePwKeyDown);
      this.loginIdInput = false;
    },
    handleChangePwKeyDown(event) {
      if (event.key === "Enter") {
        event.preventDefault();
        event.stopPropagation();
        this.changePassword();
      }
    },
    async changePassword() {
      if (!this.findPwUser.userPwd || !this.findPwUser.pwdCheck) {
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
        console.log(this.findPwUser);
        await changepw(
          this.findPwUser,
          ({ data }) => {
            if (data.message === "success") {
              alert("비밀번호 변경 성공!");
              this.findingPassword = false;
              this.$nextTick(() => {
                this.$bvModal.hide("change-pw-modal");
              });
            } else {
              alert("비밀번호 변경 실패!");
            }
          },
          (error) => {
            console.log(error);
            alert("알 수 없는 에러 발생");
            this.$nextTick(() => {
              this.$bvModal.hide("change-pw-modal");
              this.findingPassword = false;
            });
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
