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
          <div class="location-selects">
            <div v-if="locationHistory.length > 0" class="previous-selection">
              {{ locationHistory[locationHistory.length - 1].locationName }}
            </div>
            <select 
              v-model="currentLocation" 
              @change="loadNextLocations"
              required
            >
              <option value="">선택지 없음</option>
              <option 
                v-for="location in availableLocations" 
                :key="location.locationCode" 
                :value="location"
              >
                {{ location.locationName }}
              </option>
            </select>
          </div>
        </div>
      </div>
      <div class="form-group-inline">
        <div class="form-group">
          <label>Email</label>
          <input 
            type="email" 
            v-model="email" 
            required 
            readonly 
            disabled
            class="disabled-input"
          />
        </div>
        <div class="form-group">
          <label>Phone *</label>
          <input type="tel" v-model="phoneNumber" required />
        </div>
      </div>
      <div class="form-group">
        <label>Service *</label>
        <!-- <button type="button" @click="addPetType" class="add-button">+</button> -->
      </div>
      <div v-for="(petType, index) in petTypes" :key="index" class="form-group">
        <div class="pet-type-selects">
          <div v-if="petType.typeHistory.length > 0" class="previous-selection">
            {{ petType.typeHistory[petType.typeHistory.length - 1].petTypeName }}
          </div>
          <select 
            v-model="petType.currentType" 
            @change="loadNextTypes(index)"
            required
          >
            <option value="">선택지 없음</option>
            <option 
              v-for="type in petType.availableTypes" 
              :key="type.petTypeCode" 
              :value="type"
            >
              {{ type.petTypeName }}
            </option>
          </select>
          <div class="fee-input-container">
            <input 
              type="number" 
              v-model="petType.fee" 
              placeholder="요금 입력" 
              required
              min="0"
              class="fee-input"
            />
            <span class="currency-symbol">₩</span>
          </div>
          <button type="button" @click="removePetType(index)" v-if="index > 0" class="remove-button">-</button>
        </div>
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
import axios from 'axios';

export default {
  data() {
    return {
      petSitterNickname: '',
      phoneNumber: '',
      userId: null,
      email: '',
      currentLocation: '',
      availableLocations: [],
      locationHistory: [],
      introduction: '',
      petTypes: [{
        currentType: '',
        availableTypes: [],
        typeHistory: [],
        uniqueId: 0,
        fee: ''
      }]
    };
  },
  computed: {
    isValid() {
      return this.petSitterNickname && this.phoneNumber && this.email && this.currentLocation && this.introduction;
    }
  },
  methods: {
    async loadUserInfo() {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          console.error('사용자 ID를 찾을 수 없습니다.');
          return;
        }
        
        const response = await axios.get(`http://localhost:8080/api/v1/users/${userId}`);
        if (response.data.status === 200) {
          const userData = response.data.data;
          this.petSitterNickname = userData.name;
          this.phoneNumber = userData.phoneNumber;
          this.userId = userId;
        }
      } catch (error) {
        console.error('사용자 정보 로딩 실패:', error);
      }
    },
    async loadRootPetTypes() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/pet-types/by-parent/null');
        const rootTypes = response.data;
        this.petTypes.forEach(petType => {
          petType.availableTypes = [...rootTypes];
          petType.typeHistory = [];
        });
      } catch (error) {
        console.error('최상위 펫 타입 로딩 실패:', error);
      }
    },

    async loadNextTypes(index) {
      const currentPetType = this.petTypes[index];
      const selectedType = currentPetType.currentType;
      
      if (!selectedType) {
        if (currentPetType.typeHistory.length > 0) {
          const previousType = currentPetType.typeHistory.pop();
          try {
            const response = await axios.get(`http://localhost:8080/api/v1/pet-types/by-parent/${previousType.parent ? previousType.parent.petTypeCode : 'null'}`);
            currentPetType.availableTypes = [...response.data];
          } catch (error) {
            console.error('이전 펫 타입 로딩 실패:', error);
          }
        } else {
          const response = await axios.get('http://localhost:8080/api/v1/pet-types/by-parent/null');
          currentPetType.availableTypes = [...response.data];
        }
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/v1/pet-types/by-parent/${selectedType.petTypeCode}`);
        const childTypes = response.data;
        
        if (childTypes.length > 0) {
          currentPetType.typeHistory.push(selectedType);
          currentPetType.availableTypes = [...childTypes];
        }
      } catch (error) {
        console.error('하위 펫 타입 로딩 실패:', error);
      }
    },

    addPetType() {
      const newUniqueId = Math.max(...this.petTypes.map(pt => pt.uniqueId)) + 1;
      this.petTypes.push({
        currentType: '',
        availableTypes: [],
        typeHistory: [],
        uniqueId: newUniqueId,
        fee: ''
      });
      this.loadInitialTypesForIndex(this.petTypes.length - 1);
    },

    async loadInitialTypesForIndex(index) {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/pet-types/by-parent/null');
        this.petTypes[index].availableTypes = [...response.data];
      } catch (error) {
        console.error('펫 타입 로딩 실패:', error);
      }
    },

    removePetType(index) {
      if (index > 0) {
        this.petTypes.splice(index, 1);
      }
    },

    async loadRootLocations() {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/locations/by-parent/null');
        this.availableLocations = response.data;
        this.locationHistory = [];
      } catch (error) {
        console.error('최상위 지역 로딩 실패:', error);
      }
    },

    async loadNextLocations() {
      if (!this.currentLocation) {
        if (this.locationHistory.length > 0) {
          const previousLocation = this.locationHistory.pop();
          try {
            const response = await axios.get(`http://localhost:8080/api/v1/locations/by-parent/${previousLocation.parent ? previousLocation.parent.locationCode : 'null'}`);
            this.availableLocations = response.data;
          } catch (error) {
            console.error('이전 지역 로딩 실패:', error);
          }
        } else {
          await this.loadRootLocations();
        }
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/v1/locations/by-parent/${this.currentLocation.locationCode}`);
        const childLocations = response.data;
        
        if (childLocations.length > 0) {
          this.locationHistory.push(this.currentLocation);
          this.availableLocations = childLocations;
        }
      } catch (error) {
        console.error('하위 지역 로딩 실패:', error);
      }
    },

    registerPetSitter() {
      const selectedPetTypes = this.petTypes.map(pt => pt.currentType)
        .filter(type => type);
      
      const finalLocation = this.currentLocation || 
        (this.locationHistory.length > 0 ? this.locationHistory[this.locationHistory.length - 1] : null);
      
      console.log('선택된 펫 타입들:', selectedPetTypes);
      console.log('선택된 지역:', finalLocation);
      alert('펫시터로 등록되었습니다.');
    }
  },
  async created() {
    await this.loadUserInfo();
    this.email = localStorage.getItem('userEmail') || '';
    await this.loadRootPetTypes();
    await this.loadRootLocations();
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
  background-color: #6a6a6a; /* 기본 색상 */
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.register-button.valid {
  background-color: #6a1b9a; /* 유효할 때 색상 변경 */
}

.register-button:hover {
  /* background-color: #6a6a6a; */
}

.pet-type-selects {
  display: flex;
  gap: 10px;
  align-items: center;
}

select {
  flex: 1;
}

.location-selects {
  display: flex;
  gap: 10px;
  align-items: center;
}

.previous-selection {
  color: #6a1b9a;
  font-weight: bold;
  min-width: 100px;
}

.disabled-input {
  background-color: #f5f5f5;
  cursor: not-allowed;
  color: #666;
}

.fee-input-container {
  position: relative;
  display: flex;
  align-items: center;
  width: 150px;
}

.fee-input {
  width: 100%;
  padding: 8px;
  padding-right: 25px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.currency-symbol {
  position: absolute;
  right: 8px;
  color: #666;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield;
}
</style> 