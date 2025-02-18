<template>
  <div class="search-container">
    <!-- 펫시터 목록 (항상 표시) -->
    <div class="petsitter-list">
      <h2>펫시터 목록</h2>
      <div class="petsitter-items">
        <div 
          v-for="petsitter in petsitters" 
          :key="petsitter.petSitterId"
          class="petsitter-item"
        >
          <div class="petsitter-info">
            <img 
              src="@/assets/icons/PetPal_icon_1.png" 
              alt="PetPal Icon" 
              class="petsitter-icon"
            />
            <div class="petsitter-details">
              <h3>{{ petsitter.nickname }}</h3>
              <p class="introduction">{{ petsitter.introduction }}</p>
              <p class="rating">평점: {{ petsitter.rating || '신규' }}</p>
            </div>
          </div>
          <button 
            @click="loadPetsitterServices(petsitter.petSitterId)"
            class="view-services-btn"
          >
            서비스 보기
          </button>
        </div>
      </div>
    </div>

    <!-- 선택된 펫시터의 서비스 목록 -->
    <div class="service-list" v-if="selectedPetsitterId">
      <h2>서비스 목록</h2>
      <div class="service-items">
        <PetsitterCard
          v-for="service in petsitterServices"
          :key="service.serviceId"
          :petSitterId="service.petSitterId"
          :serviceId="service.serviceId"
          :location="service.locationName"
          :petType="service.petTypeCodeName"
          :petSize="service.petSizeCodeName"
          :fee="service.serviceFee"
        />
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import PetsitterCard from './PetsitterCard.vue';

export default {
  name: 'PetsitterSearchPageComponent',
  components: {
    PetsitterCard
  },
  data() {
    return {
      petsitters: [],
      selectedPetsitterId: null,
      petsitterServices: []
    }
  },
  methods: {
    async fetchPetsitters() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/pet-sitters');
        console.log('Loaded petsitters:', response.data); // 데이터 확인
        this.petsitters = response.data.data;
      } catch (error) {
        console.error('펫시터 목록 로딩 실패:', error);
      }
    },
    async loadPetsitterServices(petsitterId) {
      if (!petsitterId) {
        console.error('Invalid petsitterId:', petsitterId);
        return;
      }
      console.log('Loading services for petsitterId:', petsitterId);
      this.selectedPetsitterId = petsitterId;
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/pet-sitter-services/pet-sitter/${petsitterId}`);
        console.log('Loaded services response:', response.data);
        this.petsitterServices = response.data;
        console.log('Updated petsitterServices:', this.petsitterServices);
      } catch (error) {
        console.error('펫시터 서비스 로딩 실패:', error);
        console.log('Error details:', error.response);
      }
    }
  },
  async created() {
    await this.fetchPetsitters();
  }
}
</script>

<style scoped>
.search-container {
  display: flex;
  gap: 30px;
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.petsitter-list, .service-list {
  flex: 1;
}

.petsitter-items {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.petsitter-item {
  background: white;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
}

.petsitter-info {
  display: flex;
  gap: 16px;
  flex: 1;
}

.petsitter-icon {
  width: 60px;
  height: 60px;
  object-fit: contain;
}

.petsitter-details {
  flex: 1;
}

.petsitter-details h3 {
  color: #6a1b9a;
  margin: 0 0 8px 0;
}

.introduction {
  color: #666;
  font-size: 0.9em;
  margin: 4px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.rating {
  color: #8e24aa;
  font-weight: bold;
  margin: 4px 0 0 0;
}

.view-services-btn {
  background-color: #6a1b9a;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  white-space: nowrap;
  transition: background-color 0.2s;
}

.view-services-btn:hover {
  background-color: #8e24aa;
}

.service-items {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

h2 {
  color: #6a1b9a;
  margin-bottom: 20px;
}
</style> 