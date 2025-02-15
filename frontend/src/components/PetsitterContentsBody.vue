<template>
    <div class="body">
      <form @submit.prevent="register">
        <div class="form-group">
          <label>Pet Sitter Nickname</label>
          <input type="text" v-model="formData.petSitterNickname" required />
        </div>
        <div class="form-group">
          <label>Phone</label>
          <input type="tel" v-model="formData.phoneNumber" required />
        </div>
        <div class="form-group">
          <label>Email</label>
          <input type="email" v-model="formData.email" required />
        </div>
        <div class="form-group">
          <label>Your Location</label>
          <select v-model="formData.locationId" required>
            <option value="" disabled>Select Location</option>
            <!-- Add location options here -->
          </select>
        </div>
        <div class="form-group">
          <label>Self Introduction</label>
          <textarea v-model="formData.introduction" required></textarea>
        </div>
        <div class="service-list">
          <div v-for="(service, index) in formData.services" :key="index" class="service">
            <label>Service</label>
            <select v-model="service.petType" required>
              <option value="" disabled>Select Pet Type</option>
              <option v-for="(type, idx) in petTypes" :key="idx" :value="type">{{ type }}</option>
            </select>
            <input type="number" v-model="service.fee" placeholder="Fee" required />
            <button type="button" @click="removeService(index)" v-if="index > 0">-</button>
          </div>
          <button type="button" @click="addService">+</button>
        </div>
        <button type="submit">Update</button>
      </form>
    </div>
  </template>

  <script>
  export default {
    props: ['formData', 'petTypes'],
    methods: {
      addService() {
        this.$emit('addService');
      },
      removeService(index) {
        this.$emit('removeService', index);
      },
      register() {
        this.$emit('register', this.formData);
      }
    }
  }
  </script>

  <style scoped>
  .body {
    margin-top: 20px;
  }
  .form-group {
    margin-bottom: 15px;
  }
  .service-list {
    margin-top: 20px;
  }
  .service {
    display: flex;
    align-items: center;
    margin-bottom: 10px;
  }
  </style>