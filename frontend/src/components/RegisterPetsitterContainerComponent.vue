<template>
  <div class="register-container">
    <h2>Register as Pet Sitter</h2>
    <hr class="divider" />
    <form @submit.prevent="registerPetSitter">
      <div class="form-group-inline">
        <div class="form-group">
          <label>Pet Sitter Nickname *</label>
          <input type="text" v-model="petSitterNickname" required />
        </div>
        <div class="form-group">
          <label>Your Location *</label>
          <select v-model="locationId" required>
            <option value="" disabled>Select Location</option>
            <!-- Add location options here -->
          </select>
        </div>
      </div>
      <div class="form-group-inline">
        <div class="form-group">
          <label>Email *</label>
          <input type="email" v-model="email" required />
        </div>
        <div class="form-group">
          <label>Phone *</label>
          <input type="tel" v-model="phoneNumber" required />
        </div>
      </div>
      <div class="form-group">
        <label>Pet Type *</label>
        <!-- <button type="button" @click="addPetType" class="add-button">+</button> -->
      </div>
      <div v-for="(petType, index) in petTypes" :key="index" class="form-group">
        <select v-model="petTypes[index]" required>
          <option value="" disabled>Select Pet Type</option>
          <option value="Dog">Dog</option>
          <option value="Cat">Cat</option>
          <option value="Bird">Bird</option>
          <option value="Other">Other</option>
        </select>
        <button type="button" @click="removePetType(index)" v-if="index > 0" class="remove-button">-</button>
      </div>
      <button type="button" @click="addPetType" class="add-button">+</button>
      <div class="form-group">
        <label>Self Introduction</label>
        <textarea v-model="introduction" required class="self-intro"></textarea>
      </div>
      <button type="submit" :class="{'register-button': true, 'valid': isValid}" :disabled="!isValid">Register</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      petSitterNickname: '',
      phoneNumber: '',
      email: '',
      locationId: '',
      introduction: '',
      petTypes: [''] // 기본 pet type 배열
    };
  },
  computed: {
    isValid() {
      return this.petSitterNickname && this.phoneNumber && this.email && this.locationId && this.introduction;
    }
  },
  methods: {
    addPetType() {
      this.petTypes.push(''); // 새로운 pet type 추가
    },
    removePetType(index) {
      if (index > 0) {
        this.petTypes.splice(index, 1); // 선택한 pet type 삭제
      }
    },
    registerPetSitter() {
      // 등록 로직 구현
      alert('펫시터로 등록되었습니다.');
    }
  }
}
</script>

<style scoped>
.register-container {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 10px;
  color: #6a1b9a;
}

.divider {
  border: 1px solid #ccc; /* 회색 선 */
  margin: 10px 0; /* 위아래 여백 */
}

.description {
  text-align: center;
  margin-bottom: 20px;
  color: #555;
}

.form-group-inline {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.form-group {
  flex: 1; /* 각 입력 필드가 동일한 너비를 가지도록 설정 */
  margin-right: 10px; /* 오른쪽 여백 */
}

.form-group:last-child {
  margin-right: 0; /* 마지막 입력 필드의 오른쪽 여백 제거 */
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

input, select {
  width: 80%;
  margin: 10px;
  padding: 10px; /* 패딩 조정 */
  border: 1px solid #ccc;
  border-radius: 5px;
}

textarea {
  width: 100%;
  padding: 10px; /* 패딩 조정 */
  border: 1px solid #ccc;
  border-radius: 5px;
}

.add-button, .remove-button {
  background-color: #6a1b9a; /* 보라색 버튼 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 10px;
  margin-left: 10px; /* 버튼과 입력란 간격 */
}

.self-intro {
  height: 100px; /* 자기소개 입력란 높이 조정 */
}

.register-button {
  width: 100%;
  padding: 10px;
  background-color: #4CAF50; /* 기본 색상 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.register-button.valid {
  background-color: #6a1b9a; /* 유효할 때 색상 변경 */
}

.register-button:hover {
  background-color: #45a049;
}
</style> 