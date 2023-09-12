import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/areacode/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/areacode/gugun`, { params: params }).then(success).catch(fail);
}

function areaName(params, success, fail) {
  api.get(`/areacode/name`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, areaName };
