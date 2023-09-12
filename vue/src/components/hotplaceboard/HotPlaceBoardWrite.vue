<template>
  <div>
    <b-container>
      <b-row class="justify-content-center" style="margin-top: 10px;">
        <b-col cols="12">
          <h2 class="my-0 shadow-sm bg-light text-center">
            <mark class="sky">글쓰기</mark>
          </h2>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="auto" style="margin-right:0px; padding-right:3px; margin-top: 5px;">
          <h2>
            {{ hotplace.title }}
          </h2>
        </b-col>
        <b-col cols="auto" style="margin-top: 1%; margin-left: 0px; padding-left: 0px;">
          {{hotplace.addr1}}
        </b-col>
        <b-col cols="auto" style="margin-top: 10px;">
          <div class="text-box">{{ hotplace.overview }}</div>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <hr class="border-top">
        </b-col>
      </b-row>
      <b-row class="justify-content-center" style="margin-top: 10px;">
        <b-col cols="12">
          <b-form>
            <div class="mb-3 d-flex flex-column align-items-start">
              <label for="subject" class="form-label" style="font-size: 1.5rem;">제목 : </label>
              <b-form-input v-model="subject" type="text" style="border-color: black;"></b-form-input>
              <div style="display: flex; align-items: center;">
                <label for="subject" class="form-label text-nowrap" style="font-size: 1.5rem; margin-right: 5px;">내용 :</label>
                <label for="photoUrl" class="form-label text-nowrap" style="font-size: 1.5rem; margin-right: 5px;">이미지 파일만 가능합니다.</label>
                <b-form-file ref="fileInput" v-model="files" class="text-nowrap" accept="image/*"  multiple plain @change="previewImage" style="width:75px;"></b-form-file>
              </div>
              <div v-for="(photoUrl, index) in photoUrl" :key="index" class="image-preview">
                <img :src="photoUrl.photoUrl" alt="Image preview" style="width: 90%; margin-top: 5px; margin-bottom: 5px; margin-right: 30px;" />
                <button @click="removeImage(index); $event.preventDefault()" class="remove-button">x</button>
              </div>
              <b-row>
                <b-col>
                  <hr class="border-top">
                </b-col>
              </b-row>
              <b-form-textarea v-model="content" rows="20" style="border-color: black;"></b-form-textarea>
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
export default {
  props: {
    user: {
      type:String,
    },
    hotplace: {
      type:Object,
    },
  },
  name: "HotPlaceBoardWrite",
  data() {
    return {
      subject: "",
      content: "",
      imagePreview: null,
      photoUrl: [],
      files:[],
      searchParam_list:{
        key: null,
        word: null,
        spp: 20,
        pg: 1,
        num: null,
      },
    }
  },
  computed: {
    userId() {
      return this.user;
    }
  },
  methods: {
    previewImage(event) {
      const newFiles = Array.from(event.target.files);

      newFiles.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.photoUrl.push({
            photoUrl: e.target.result
          });
        };
        reader.readAsDataURL(file);
      });
      
      this.files = [...this.files, ...newFiles]; // 이전 파일과 새 파일을 함께 저장
    },
    removeImage(index) {
      this.photoUrl.splice(index, 1);
      this.files.splice(index, 1);
      this.$refs.fileInput.reset(); // file input reset
    },
    write() {
      http.post("/hotplaceboard/write", {
        subject: this.subject,
        content: this.content,
        userId: this.userId,
        contentId: this.hotplace.contentId,
        title: this.hotplace.title,
        addr1: this.hotplace.addr1,
        overview: this.hotplace.overview,
        firstImg: this.photoUrl && this.photoUrl[0] ? this.photoUrl[0].photoUrl : null,
        photoUrl : this.photoUrl,
    })
      .then(() => {
        this.$emit('search', this.searchParam_list);
        this.$router.push({ name: 'hotplaceboardlist' });
    })
    },
    list() {
      this.$router.push({ name: 'hotplaceboardlist' });
    }
  },
  created() {
  },
};
</script>

<style scoped>
.button_rounded {
    border-radius: 5px; /* 원하는 둥근 정도에 맞게 값을 조정하세요 */
}
.image-preview {
  display: flex;
  position: relative;
  align-items: flex-start;
}

.preview-image {
  width: 100%;
  margin-top: 5px;
  margin-bottom: 5px;
}

.remove-button {
  border-radius: 5px;
  margin-top: 10px;
  margin-left: auto;
  color: red;
  background-color: white;
  border-collapse: collapse;
}
/* 새로운 스타일 */
</style>