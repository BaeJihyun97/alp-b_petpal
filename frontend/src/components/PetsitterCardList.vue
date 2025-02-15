<template>
  <div class="petsitter-card-list">
    <template v-for="(petSitter, index) in petSitters" :key="index">
      <PetsitterCard 
        :imageSrc="petSitter.imageSrc" 
        :name="petSitter.name" 
        :rating="petSitter.rating" 
        :reviews="petSitter.reviews" 
        :location="petSitter.location" 
        :fee="petSitter.fee" 
        :description="petSitter.description" 
        :isOpen="petSitter.isOpen" 
        :hours="petSitter.hours" 
      />
    </template>
    <button @click="loadMore" class="more-button">More</button>
  </div>
</template>

<script>
import PetsitterCard from '@/components/PetsitterCard.vue';

export default {
  components: {
    PetsitterCard
  },
  data() {
    return {
      petSitters: [],
      totalLoaded: 0,
      loadCount: 5
    };
  },
  created() {
    this.loadPetSitters();
  },
  methods: {
    loadPetSitters() {
      for (let i = 0; i < this.loadCount; i++) {
        this.petSitters.push(this.generateRandomPetSitter());
      }
      this.totalLoaded += this.loadCount;
    },
    loadMore() {
      this.loadPetSitters();
    },
    generateRandomPetSitter() {
      return {
        imageSrc: require('@/assets/images/cats-dogs-image_1.jpg'), // 고정된 이미지 URL
        name: `Pet Sitter ${Math.floor(Math.random() * 100) + 1}`,
        rating: (Math.random() * 5).toFixed(1),
        reviews: Math.floor(Math.random() * 1000),
        location: '시흥동',
        fee: Math.floor(Math.random() * 200) + 50,
        description: '펫시터 설명...',
        isOpen: Math.random() > 0.5,
        hours: 'Monday - Friday at 8.00 am - 5.00 pm'
      };
    }
  }
};
</script>

<style scoped>
.petsitter-card-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  min-width: 800px;
  margin: 50px auto;
}

.more-button {
  margin-top: 20px;
  background-color: #6a1b9a; /* 보라색 버튼 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
}
</style> 