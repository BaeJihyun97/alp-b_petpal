<template>
  <div class="sitter-list-container">
    <button class="arrow left" @click="scrollLeft">‹</button>
    <div class="sitter-list" ref="sitterList">
      <CardComponent
        v-for="sitter in sitters"
        :key="sitter.id"
        :sitterName="sitter.name"
        :sitterDescription="sitter.description"
        :rating="sitter.rating"
        :reviewCount="sitter.reviewCount"
        class="sitter-card"
      />
    </div>
    <button class="arrow right" @click="scrollRight">›</button>
  </div>
</template>

<script>
import CardComponent from './CardComponent.vue';

export default {
  components: {
    CardComponent
  },
  data() {
    return {
      sitters: [] // 서버에서 받아올 데이터
    };
  },
  created() {
    this.fetchSitters();
  },
  methods: {
    fetchSitters() {
      // 예시 데이터
      this.sitters = Array.from({ length: 20 }, (_, i) => ({
        id: i,
        name: `펫시터 ${i + 1}`,
        description: `펫시터 설명 ${i + 1}`,
        rating: Math.floor(Math.random() * 5) + 1,
        reviewCount: Math.floor(Math.random() * 100)
      }));
    },
    scrollLeft() {
      this.$refs.sitterList.scrollBy({ left: -300, behavior: 'smooth' });
    },
    scrollRight() {
      this.$refs.sitterList.scrollBy({ left: 300, behavior: 'smooth' });
    }
  }
}
</script>

<style scoped>
.sitter-list-container {
  position: relative;
  display: flex;
  align-items: center;
  overflow: hidden;
  width: 100%;
}

.sitter-list {
  display: flex;
  overflow-x: auto;
  scroll-behavior: smooth;
  padding: 20px 0;
}

.sitter-card {
  margin-left: 10px; /* 왼쪽 간격 추가 */
}

.arrow {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  font-size: 2em;
  cursor: pointer;
  z-index: 1;
  padding: 0 10px;
}

.left {
  left: 0;
}

.right {
  right: 0;
}
</style> 