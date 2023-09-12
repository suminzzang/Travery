<template>
  <div>
    <div id="map" ></div>
    
    <!-- 모달 -->
    <transition name="modal">
      <div class="modal-mask" v-if="showModal" style="z-index: 1000;">
        <div class="modal-wrapper">
          <div class="modal-container">
            <b-container class="modal-header text-center">
              <b-row>
                <h2>{{ selectedArticle.title }}</h2>
                <p style="margin-top: 20px; margin-left: 10px;">{{ selectedArticle.addr1 }}</p>
              </b-row>
              <!-- <i class="bi bi-heart" style="color: red; width:5%; height: 5%;">asd</i> -->
            </b-container>
            <div class="modal-body">
              <p>{{ selectedArticle.addr }}</p>
              <b-img :src="selectedArticle.firstImage|| require('@/assets/img/noimg.jpg')" style="width: 100%; height: 250px; object-fit: cover;"  />
              <div class="text-box">{{ selectedArticle.overview }}</div>
            </div>

            <div class="modal-footer">
              <button class="modal-default-button blue-button" @click="addPlan">
                여행계획 추가
              </button>
              <button class="modal-default-button blue-button" @click="showModal = false">
                <router-link v-if="isUser" :to="{ name: 'hotplaceboardwrite',params:{hotplace:selectedArticle}}" style="color: blue;">핫플레이스 작성</router-link>
                  <div v-else style="color: blue;" @click="mvwrite">핫플레이스 작성</div>
              </button>
              <button class="modal-default-button red-button" @click="showModal = false">
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import 'bootstrap-icons/font/bootstrap-icons.css';

export default {
  name: "KakaoMap",
  components: {},
  data() {
    return {
      map: null,
      positions: [],
      markers: [],
      showModal: false,
      selectedArticle: null,
      currentInfoWindow: null,
    };
  },
  props: {
    articles: [],
    center: {
    },
    tripPlan: [],
    user: {
      type:String,
    },
  },
  computed: {
    isUser() {
      return !!this.user;
    }
  },
  watch: {
    articles() {
      this.positions = [];
      this.articles.forEach((article) => {
        let obj = {};
        obj.title = article.title;
        obj.latlng = new kakao.maps.LatLng(article.latitude, article.longitude);
        if (article.firstImage) {
          obj.img = article.firstImage;
        } else {
          obj.img=require('@/assets/img/noimg.jpg')
        }

        this.positions.push(obj);
      });
      console.log("***"+this.articles.length);
      this.loadMaker();
    },
    center() {
      this.map.setCenter(new kakao.maps.LatLng(this.center.lat, this.center.lng));
      const position = this.positions.find(pos => pos.title === this.center.title);
      if (position) {
        const index = this.positions.findIndex(pos => pos.title === this.center.title);
        const title = position.title.length > 20 ? position.title.slice(0, 20) + '...' : position.title;
        const infowindow = new kakao.maps.InfoWindow({
          content: `
            <div style="width:300px; height:180px;">
              <img src="${position.img}" alt="${position.title}" style="width: 100%; height: 85%;" />
              ${title}
              <a id="centerInfoWindowButton${index}" class="info-window-button">상세 설명</a>
            </div>
          `
        });
        // 현재 활성화된 infowindow가 있다면 제거
        if(this.currentInfoWindow) {
          this.currentInfoWindow.close();
        }
        // 새로운 infowindow를 열고 현재 infowindow로 설정
        const marker = this.markers.find(marker => marker.getTitle() === position.title);
        infowindow.open(this.map, marker);
        this.currentInfoWindow = infowindow;

        this.$nextTick(() => {
          const infoWindowButton = document.getElementById(`centerInfoWindowButton${index}`);
          if (infoWindowButton) {
            infoWindowButton.addEventListener('click', () => {
              this.showModal = true;
              this.selectedArticle = this.articles[index];
            });
          }
        });
      }
    }
  },
  created() {
  },
  mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    mvwrite() {
      alert('회원만 글을 작성할 수 있습니다.');
    },
    // api 불러오기
    addPlan() {
    this.$emit('add-plan', this.selectedArticle);
    this.showModal = false;
    },
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
        process.env.VUE_APP_KAKAO_MAP_API_KEY +
        "&autoload=false";
      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
      kakao.maps.event.addListener(this.map, 'click' , () => {
        // 현재 활성화된 infowindow가 있다면 제거합니다.
        if (this.currentInfoWindow) {
          this.currentInfoWindow.setMap(null);
          this.currentInfoWindow = null;
        }
      });
      this.loadMaker();
    },
    // 지정한 위치에 마커 불러오기
    loadMaker() {
      console.log("loadMaker"+this.articles.length);
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();
      // 마커 이미지를 생성합니다
      const imgSrc = require("@/assets/img/markerStar.png");
      // 마커 이미지의 이미지 크기 입니다
      const imgSize = new kakao.maps.Size(24, 35);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

      // 마커를 생성합니다
      this.markers = [];
      this.positions.forEach((position,index) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커의 이미지
        });
        kakao.maps.event.addListener(marker, 'click', () => {
          this.showModal = true;
          this.selectedArticle = this.articles[index];
        });
        const infowindow = new kakao.maps.InfoWindow({
          content: `
            <div style="width:300px; height:180px;">
              <img src="${position.img}" alt="${position.title}" style="width: 100%; height: 85%;" />
              ${position.title.length > 15 ? position.title.slice(0, 15) + '...' : position.title} <span style="color:blue;">클릭하여 상세설명</span>
            </div>
          `
        });
        kakao.maps.event.addListener(marker, 'mouseover', this.makeOverListener(this.map, marker, infowindow));
        kakao.maps.event.addListener(marker, 'mouseout', this.makeOutListener(infowindow));

        this.markers.push(marker);
      });

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      const bounds = this.positions.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
      );

      this.map.setBounds(bounds);
    },
    // 인포윈도우를 표시하는 클로저를 만드는 함수입니다.
    makeOverListener(map, marker, infowindow) {
      return function() {
        infowindow.open(map, marker);
      };
    },

    // 인포윈도우를 닫는 클로저를 만드는 함수입니다.
    makeOutListener(infowindow) {
      return function() {
        infowindow.close();
      };
    },
    
    deleteMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
  },
};

</script>

<style>
.modal-mask {
  position: fixed; /* 모달창을 화면 중앙에 고정 */
  z-index: 9998; /* 다른 요소보다 앞에 나타나도록 z-index 설정 */
  top: 0; /* 화면 상단부터 시작 */
  left: 0; /* 화면 왼쪽부터 시작 */
  width: 100%; /* 화면 가득 채우도록 설정 */
  height: 100%; /* 화면 가득 채우도록 설정 */
  background-color: rgba(0, 0, 0, .5); /* 반투명한 검은색 배경 */
  display: flex; /* 내부 요소를 flex로 배치 */
  justify-content: center; /* 가로 방향으로 중앙 정렬 */
  align-items: center; /* 세로 방향으로 중앙 정렬 */
}

.modal-wrapper {
  width: 80%; /* 화면의 80% 크기로 설정 */
  max-width: 500px; /* 모달창의 최대 크기 설정 */
  background: white; /* 배경색을 하얀색으로 설정 */
  padding: 30px; /* 내부 요소와의 간격 설정 */
  border-radius: 10px; /* 모달창 모서리를 둥글게 설정 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, .33); /* 모달창에 그림자 효과 적용 */
  transition: all .3s ease; /* 모든 요소에 대해 0.3초 동안 변화하는 효과 적용 */
}
  .modal-header {
    text-align: center;
    max-height: 100px;
  overflow-y: auto;
  }

  .modal-header h2 {
    text-align: center;
  }

  .modal-header p {
    font-size: 14px;
  }
  .modal-enter-active, .modal-leave-active {
  transition: opacity .5s;
}
.modal-enter, .modal-leave-to {
  opacity: 0;
}

.text-box {
  max-height: 200px;  /* 원하는 높이로 설정 */
  overflow-y: auto;   /* 스크롤바 추가 */
}
</style>
