<template>
  <div class="card">
    <h3 class="title">{{ title }}</h3>
    <p class="description">{{ description }}</p>
    <div class="rating" v-if="(type === 'sitter' || type === 'product') && rating">
      <span v-for="n in 5" :key="n" class="star" :class="{ active: n <= rating }">★</span>
    </div>
    <div class="review" v-if="(type === 'sitter' || type === 'product') && reviewCount !== undefined">
      <span>review</span>
      <span class="review-count">{{ reviewCount }}</span>
    </div>
    <div class="pet-info" v-if="type === 'pet'">
      <p>반려동물 정보</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: String,
    description: String,
    rating: Number,
    reviewCount: Number,
    type: String // 'sitter', 'product', 'pet' 등
  }
}
</script>

<style scoped>
.card {
  max-width: 300px;
  min-width: 250px;
  max-height: 180px;
  min-height: 100px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.title, .description {
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.rating {
  display: flex;
  align-items: center;
  margin: 10px 0;
}

.star {
  color: #ccc;
  font-size: 1.5em;
  margin-right: 5px;
}

.star.active {
  color: gold;
}

.review {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-top: 1px solid #ddd;
  padding-top: 10px;
  color: #888;
}

.review-count {
  font-weight: bold;
}

.pet-info {
  color: #555;
}
</style> 