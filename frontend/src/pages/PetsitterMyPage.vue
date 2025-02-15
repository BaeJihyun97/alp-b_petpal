<template>
  <div class="page-container">
    <Header2Component />
    <Header3Component />
    <div class="petsitter-container">
        <RegisterPetsitterContentsComponent />
    <div class="service-list">
      <h2>Services</h2>
      <div v-for="(service, index) in services" :key="index" class="service-item">
        <PetsitterServiceRegisterComponent />
        <button 
          v-if="index > 0" 
          type="button" 
          @click="removeService(index)" 
          class="remove-button">-</button>
      </div>
      <button type="button" @click="addService" class="add-button">+</button>
    </div>
    </div>
    
    <button type="button" @click="goToDeletePage" class="delete-button">탈퇴하기</button>

    <FooterComponent />
  </div>
</template>

<script>
import Header2Component from '@/components/Header2Component.vue';
import FooterComponent from '@/components/FooterComponent.vue';
import Header3Component from '@/components/Header3Component.vue';
import RegisterPetsitterContentsComponent from '@/components/RegisterPetSitterContentsComponent.vue';
import PetsitterServiceRegisterComponent from '@/components/PetsitterServiceRegisterComponent.vue';
// import { useRouter } from 'vue-router';

export default {
  components: {
    Header2Component,
    FooterComponent,
    Header3Component,
    RegisterPetsitterContentsComponent,
    PetsitterServiceRegisterComponent
  },
  data() {
    return {
      nickname: '',
      phone: '',
      email: '',
      location: '',
      petType: '',
      bio: '',
      servicePetType: '',
      fee: '',
      services: [1] // 초기 서비스 항목 1개
    };
  },
  methods: {
    updateProfile() {
      console.log('Profile updated', this.$data);
    },
    addService() {
      this.services.push(this.services.length + 1); // 서비스 항목 추가
    },
    removeService(index) {
      if (this.services.length > 1) {
        this.services.splice(index, 1); // 서비스 항목 삭제
      }
    },
    goToDeletePage() {
      this.$router.push('/delete-petsitter');
    }
  }
};
</script>

<style scoped>
.petsitter-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.input-field {
  /* width: 100%; */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
}
.btn {
  background-color: #8b5cf6;
  color: white;
  padding: 10px 15px;
  border-radius: 8px;
}

.service-list {
  margin-top: 20px;
}

.service-item {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.add-button, .remove-button, .delete-button {
  background-color: #6a1b9a; /* 보라색 버튼 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px;
  margin-left: 10px; /* 버튼과 입력란 간격 */
  margin-bottom: 10px;
  margin-top: auto;
}

.delete-button {
  margin-top: 20px; /* 탈퇴하기 버튼의 여백 */
}
</style>