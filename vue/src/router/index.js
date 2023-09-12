import Vue from "vue";
import VueRouter from "vue-router";

// views
import AppMain from "@/views/AppMain";
// import AdminBoardView from "@/views/AdminBoardView";
// import UserBoardView from "@/views/UserBoardView";
// import HotPlaceBoardView from "@/views/HotPlaceBoardView";
// import TripSearchView from "@/views/TripSearchView";

// import store from "@/store"; // onlyAuthUser 용

Vue.use(VueRouter);

// *TODO*: UserController에서 jwt인증 하나로 묶기
// const onlyAuthUser = async (to, from, next) => {
//   const checkUserInfo = store.getters["memberStore/checkUserInfo"];
//   const checkToken = store.getters["memberStore/checkToken"];
//   let token = sessionStorage.getItem("access-token");
//   console.log("로그인 처리 전", checkUserInfo, token);

//   if (checkUserInfo != null && token) {
//     console.log("토큰 유효성 체크하러 가자!!!!");
//     await store.dispatch("memberStore/getUserInfo", token);
//   }
//   if (!checkToken || checkUserInfo === null) {
//     alert("로그인이 필요한 페이지입니다..");
//     // next({ name: "login" });
//     router.push({ name: "login" });
//   } else {
//     console.log("로그인 했다!!!!!!!!!!!!!.");
//     next();
//   }
// };

const routes = [
  {
    path: "/",
    name: "main",
    component: AppMain,
  },
  {
    path: "/users",
    name: "users",
    component: () => import(/* webpackChunkName: "users" */ "@/views/AppMember.vue"),
    redirect: "/users/mypage",
    children: [
      {
        path: "mypage",
        name: "mypage",
        component: () => import(/* webpackChunkName: "users" */ "@/components/member/MemberMyPage"),
      },
    ],
  },
  {
    path: "/adminboard",
    name: "adminboard",
    component: () => import(/* webpackChunkName: "adminboard" */ "@/views/AppAdminBoard.vue"),
    redirect: "/adminboard/list",
    children: [
      {
        path: "list",
        name: "adminboardlist",
        component: () =>
          import(/* webpackChunkName: "adminboard" */ "@/components/adminboard/AdminBoardList"),
      },
      {
        path: "write",
        name: "adminboardwrite",
        component: () =>
          import(/* webpackChunkName: "adminboard" */ "@/components/adminboard/AdminBoardWrite"),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "adminboardview",
        component: () =>
          import(/* webpackChunkName: "adminboard" */ "@/components/adminboard/AdminBoardView"),
      },
      {
        path: "modify/:articleNo",
        name: "adminboardmodify",
        component: () =>
          import(/* webpackChunkName: "adminboard" */ "@/components/adminboard/AdminBoardModify"),
      },
    ],
  },
  {
    path: "/userboard",
    name: "userboard",
    component: () => import(/* webpackChunkName: "userboard" */ "@/views/AppUserBoard.vue"),
    redirect: "/userboard/list",
    children: [
      {
        path: "list",
        name: "userboardlist",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/userboard/UserBoardList"),
      },
      {
        path: "write",
        name: "userboardwrite",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/userboard/UserBoardWrite"),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "userboardview",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/userboard/UserBoardView"),
      },
      {
        path: "modify/:articleNo",
        name: "userboardmodify",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/userboard/UserBoardModify"),
      },
    ],
  },
  {
    path: "/qnaboard",
    name: "qnaboard",
    component: () => import(/* webpackChunkName: "userboard" */ "@/views/AppQnABoard.vue"),
    redirect: "/qnaboard/list",
    children: [
      {
        path: "list",
        name: "qnaboardlist",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardList"),
      },
      {
        path: "write",
        name: "qnaboardwrite",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardWrite"),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "qnaboardview",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardView"),
      },
      {
        path: "modify/:articleNo",
        name: "qnaboardmodify",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardModify"),
      },
    ],
  },
  {
    path: "/qnaboardreply",
    name: "qnaboardreply",
    component: () => import(/* webpackChunkName: "userboard" */ "@/views/AppQnABoard.vue"),
    redirect: "/qnaboard/list",
    children: [
      {
        path: "list",
        name: "qnaboardreplylist",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardList"),
      },
      {
        path: "write",
        name: "qnaboardreplywrite",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardReplyWrite"),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "qnaboardreplyview",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardReplyView"),
      },
      {
        path: "modify/:articleNo",
        name: "qnaboardreplymodify",
        component: () =>
          import(/* webpackChunkName: "userboard" */ "@/components/qnaboard/QnABoardReplyModify"),
      },
    ],
  },
  {
    path: "/tripsearch",
    name: "tripsearch",
    redirect: "/tripsearch/list",
    component: () => import(/* webpackChunkName: "hotplaceboard" */ "@/views/AppTripSearch.vue"),
    children: [
      {
        path: "list",
        name: "tripsearchlist",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/tripsearch/TripSearchList.vue"
          ),
      },
    ],
  },
  {
    path: "/tripplanboard",
    name: "tripplanboard",
    redirect: "/tripplanboard/list",
    component: () => import(/* webpackChunkName: "hotplaceboard" */ "@/views/AppTripPlanBoard.vue"),
    children: [
      {
        path: "list",
        name: "tripplanboardlist",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/tripplanboard/TripPlanBoardList"
          ),
      },
      {
        path: "write",
        name: "tripplanboardwrite",
        props:true,
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/tripplanboard/TripPlanBoardWrite"
          ),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "tripplanboardview",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/tripplanboard/TripPlanBoardView"
          ),
      },
      {
        path: "modify/:articleNo",
        name: "tripplanboardmodify",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/tripplanboard/TripPlanBoardModify"
          ),
      },
    ],
  },
  {
    path: "/hotplaceboard",
    name: "hotplaceboard",
    redirect: "/hotplaceboard/list",
    component: () => import(/* webpackChunkName: "hotplaceboard" */ "@/views/AppHotPlaceBoard.vue"),
    children: [
      {
        path: "list",
        name: "hotplaceboardlist",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/hotplaceboard/HotPlaceBoardList"
          ),
      },
      {
        path: "write",
        name: "hotplaceboardwrite",
        props:true,
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/hotplaceboard/HotPlaceBoardWrite"
          ),
      },
      {
        path: "view/:articleNo", // 뒤에 :articleno
        name: "hotplaceboardview",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/hotplaceboard/HotPlaceBoardView"
          ),
      },
      {
        path: "modify/:articleNo",
        name: "hotplaceboardmodify",
        component: () =>
          import(
            /* webpackChunkName: "hotplaceboard" */ "@/components/hotplaceboard/HotPlaceBoardModify"
          ),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
