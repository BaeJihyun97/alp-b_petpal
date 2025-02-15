<template>
  <div class="header3-container">
    <img :src="imageSrc" alt="Pet Header" class="header-image" />
    <div class="header-text">
      <h2>{{ title }}</h2>
      <p>{{ subtitle }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true
    },
    subtitle: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      imageSrc: require('@/assets/images/cats-dogs-image_1.jpg') // 이미지 파일 이름으로 변경
    };
  }
}
</script>

<style scoped>
.header3-container {
  display: flex;
  align-items: center;
  background-color: #f8e8d4; /* 배경색 조정 */
  padding: 0; /* 패딩 제거 */
  clip-path: polygon(0 0, 100% 0, 100%); /* 불규칙한 파형으로 자르기 */
}

.header-image {
  width: 50%; /* 이미지 크기 조정 */
  /* border-radius: 0 20px 20px 0; 오른쪽을 약간의 곡선으로 자르기 */
  margin: 0; /* 마진 제거 */
}

.header-text {
  margin-left: 20px; /* 텍스트와 이미지 간격 조정 */
}

h2 {
  color: #6a1b9a; /* 텍스트 색상 조정 */
  margin: 0;
}

p {
  color: #333; /* 텍스트 색상 조정 */
  margin: 5px 0 0;
}
</style> 