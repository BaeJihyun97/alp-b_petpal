<template>
  <div class="petsitter-card-list">
    <template v-for="service in services" :key="service.serviceId">
      <PetsitterCard 
        :petSitterId="service.petSitterId"
        :serviceId="service.serviceId"
        :location="service.locationCodeName"
        :petType="service.petTypeCodeName"
        :petSize="service.petSizeCodeName"
        :fee="service.serviceFee"
      />
    </template>
  </div>
</template>

<script>
import PetsitterCard from '@/components/PetsitterCard.vue';
import axios from 'axios';

export default {
  components: {
    PetsitterCard
  },
  data() {
    return {
      services: []
    }
  },
  methods: {
    async fetchServices() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/pet-sitter-services');
        if (response.data && response.data.data) {
          this.services = response.data.data;
        }
      } catch (error) {
        console.error('서비스 목록 로딩 실패:', error);
      }
    }
  },
  async created() {
    await this.fetchServices();
  }
};
</script>

<style scoped>
.petsitter-card-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  padding: 20px;
}
</style> 