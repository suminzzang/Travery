import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/users/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function join(user, success, fail) {
  await api.post(`/users/join`, JSON.stringify(user)).then(success).catch(fail);
}

async function idCheck(userid, success, fail) {
  await api.get(`/users/idcheck/${userid}`).then(success).catch(fail);
}

async function changepw(user, success, fail) {
  await api.put(`/users/changepassword`, JSON.stringify(user)).then(success).catch(fail);
}

async function verify(user, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.post(`/users/password/verify`, JSON.stringify(user)).then(success).catch(fail);
}

async function modify(user, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.put(`/users/modify`, JSON.stringify(user)).then(success).catch(fail);
}

async function remove(user, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.post(`/users/delete`, JSON.stringify(user)).then(success).catch(fail);
}

async function mypage(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/users/mypage/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(userid, success, fail) {
  api.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
  await api.post(`/users/refresh`, userid).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await api.get(`/users/logout/${userid}`).then(success).catch(fail);
}

async function send(mail, success, fail) {
  await api.post(`/mail/send`, JSON.stringify(mail)).then(success).catch(fail);
}

async function auth(key, success, fail) {
  api.defaults.headers["mail-token"] = sessionStorage.getItem("mail-token");
  await api.post(`/mail/auth`, JSON.stringify(key)).then(success).catch(fail);
}

async function sendid(mail, success, fail) {
  await api.post(`/mail/sendid`, mail).then(success).catch(fail);
}

async function sendpw(mail, success, fail) {
  await api.post(`/mail/sendpw`, mail).then(success).catch(fail);
}
export {
  login,
  join,
  idCheck,
  changepw,
  verify,
  modify,
  remove,
  mypage,
  tokenRegeneration,
  logout,
  send,
  auth,
  sendid,
  sendpw,
};
