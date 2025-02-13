<template>
  <div class="card-list-container">
    <button class="arrow left" @click="scrollLeft">‹</button>
    <div class="card-list" ref="cardList">
      <CardComponent
        v-for="item in items"
        :key="item.id"
        :title="item.name"
        :description="item.description"
        :rating="item.rating"
        :reviewCount="item.reviewCount"
        :type="item.type"
        class="card-item"
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
  props: {
    items: Array
  },
  methods: {
    scrollLeft() {
      const cardList = this.$refs.cardList;
      const scrollAmount = cardList.clientWidth;
      cardList.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    },
    scrollRight() {
      const cardList = this.$refs.cardList;
      const scrollAmount = cardList.clientWidth;
      cardList.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
  }
}
</script>

<style scoped>
.card-list-container {
  position: relative;
  display: flex;
  align-items: center;
  overflow: hidden;
  width: 100%;
}

.card-list {
  display: flex;
  overflow-x: hidden;
  scroll-behavior: smooth;
  width: 100%;
  padding: 20px 0;
}

.card-item {
  margin-left: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
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
  opacity: 0;
  transition: opacity 0.3s ease;
}

.card-list-container:hover .arrow {
  opacity: 1;
}

.left {
  left: 0;
}

.right {
  right: 0;
}
</style> 