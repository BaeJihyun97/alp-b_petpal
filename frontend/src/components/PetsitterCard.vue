<template>
  <div class="petsitter-card">
    <div class="card-content">
      <img :src="imageSrc" alt="Pet Sitter" class="petsitter-image" />
      <div class="petsitter-info">
        <h3>{{ name }}</h3>
        <div class="rating">
          <span>⭐ {{ rating }} ({{ reviews }} reviews)</span>
        </div>
        <div class="location">
          <span>📍 {{ location }}</span>
        </div>
        <div class="fee">
          <span>💲 {{ fee }}</span>
        </div>
        <div class="bottom-info">
          <div class="availability">
            <span v-if="isOpen" class="open">OPEN</span>
            <span v-else class="closed">CLOSED</span>
          </div>
          <div class="hours">
            <span>{{ hours }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="description">
      <p>{{ description }}</p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    imageSrc: {
      type: String,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    rating: {
      type: Number,
      required: true
    },
    reviews: {
      type: Number,
      required: true
    },
    location: {
      type: String,
      required: true
    },
    fee: {
      type: Number,
      required: true
    },
    description: {
      type: String,
      required: true
    },
    isOpen: {
      type: Boolean,
      required: true
    },
    hours: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>
.petsitter-card {
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  flex-direction: row;
  background-color: #ffffff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  margin-bottom: 16px;
}

.card-content {
  display: flex;
  flex: 1;
  width: 40%;
}

.petsitter-image {
  width: 120px;
  height: 120px;
  border-radius: 12px;
  margin-right: 16px;
}

.petsitter-info {
  flex: 1;
}

.rating, .location, .fee {
  margin: 4px 0;
}

.bottom-info {
  display: flex;
  justify-content: space-between;
  margin-top: 8px;
}

.open {
  color: #4caf50;
}

.closed {
  color: #f44336;
}

.description {
  margin-top: 12px;
  width: 60%;
  flex-direction: column;
  color: #6a6a6a;
  font-size: 14px;
}
</style> 