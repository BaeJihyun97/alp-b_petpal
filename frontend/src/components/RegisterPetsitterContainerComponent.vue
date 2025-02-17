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
              {{ locationHistory[locationHistory.length - 1].groupName || locationHistory[locationHistory.length - 1].codeName }}
            </div>
            <select 
              v-model="currentLocation" 
              @change="loadNextLocations"
              required
            >
              <option value="">선택지 없음</option>
              <option 
                v-for="location in availableLocations" 
                :key="getUniqueKey(location)"
                :value="location"
              >
                {{ location.groupName || location.codeName }}
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
          <select 
            v-model="petType.currentType" 
            @change="loadNextTypes(index)"
            required
          >
            <option value="">동물 선택</option>
            <option 
              v-for="type in petType.availableTypes" 
              :key="getUniqueKey(type)"
              :value="type"
            >
              {{ type.groupName || type.codeName }}
            </option>
          </select>
          <select 
            v-model="petType.currentLocation" 
            @change="loadNextServiceLocations(index)"
            required
          >
            <option value="">위치 선택</option>
            <option 
              v-for="location in petType.availableLocations" 
              :key="getUniqueKey(location)"
              :value="location"
            >
              {{ location.groupName || location.codeName }}
            </option>
          </select>
          <select 
            v-model="petType.currentPetSize" 
            @change="loadNextPetSizes(index)"
            required
          >
            <option value="">크기 선택</option>
            <option 
              v-for="size in petType.availablePetSizes" 
              :key="getUniqueKey(size)"
              :value="size"
            >
              {{ size.groupName || size.codeName }}
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
            <!-- <span class="currency-symbol">₩/H</span>ㄴ -->
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

// 코드 그룹 ID 상수 정의
const CODE_GROUP = {
  PAT_SIZE: '400',
  LOCATION: '200',
  PET_TYPE: '100'
};

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
        fee: '',
        currentLocation: '',
        availableLocations: [],
        locationHistory: [],
        currentPetSize: '',
        availablePetSizes: [],
        petSizeHistory: []
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
    async loadRootLocations() {
      try {
        // 먼저 코드 그룹의 하위 그룹을 확인
        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.LOCATION}/sub`);
        
        if (groupResponse.data.length > 0) {
          // 하위 그룹이 있으면 그룹 정보를 사용
          this.availableLocations = groupResponse.data;
        } else {
          // 하위 그룹이 없으면 코드 상세 정보를 가져옴
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.LOCATION}`);
          this.availableLocations = detailResponse.data.filter(code => code.isActive);
        }
        this.locationHistory = [];
      } catch (error) {
        console.error('지역 정보 로딩 실패:', error);
      }
    },

    async loadNextLocations() {
      console.log("loadNextLocations");
      if (!this.currentLocation) {
        if (this.locationHistory.length > 0) {
          const previousLocation = this.locationHistory.pop();
          console.log(previousLocation);
          await this.loadLocationsByGroup(previousLocation.groupId);
        } else {
          await this.loadRootLocations();
        }
        return;
      }

      await this.loadLocationsByGroup(this.currentLocation.groupId);
    },

    async loadLocationsByGroup(groupId) {
      try {
        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${groupId}/sub`);
        
        if (groupResponse.data.length > 0) {
          this.availableLocations = groupResponse.data;
          if (this.currentLocation && !this.locationHistory.includes(this.currentLocation)) {
            this.locationHistory.push(this.currentLocation);
          }
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${groupId}`);
          this.availableLocations = detailResponse.data.filter(code => code.isActive);
          if (this.currentLocation && !this.locationHistory.includes(this.currentLocation)) {
            this.locationHistory.push(this.currentLocation);
          }
        }
      } catch (error) {
        console.error('위치 정보 로딩 실패:', error);
      }
    },

    async loadRootPetTypes() {
      try {
        // 먼저 코드 그룹의 하위 그룹을 확인
        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.PET_TYPE}/sub`);
        
        if (groupResponse.data.length > 0) {
          // 하위 그룹이 있으면 그룹 정보를 사용
          const rootTypes = groupResponse.data;
          this.petTypes.forEach(petType => {
            petType.availableTypes = [...rootTypes];
            petType.typeHistory = [];
          });
        } else {
          // 하위 그룹이 없으면 코드 상세 정보를 가져옴
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PET_TYPE}`);
          const rootTypes = detailResponse.data.filter(code => code.isActive);
          this.petTypes.forEach(petType => {
            petType.availableTypes = [...rootTypes];
            petType.typeHistory = [];
          });
        }
      } catch (error) {
        console.error('펫 타입 로딩 실패:', error);
      }
    },

    async loadInitialData(index) {
      try {
        // 각각의 데이터를 독립적으로 로드
        const [petTypeResponse, locationResponse, sizeResponse] = await Promise.all([
          axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.PET_TYPE}/sub`),
          axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.LOCATION}/sub`),
          axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PAT_SIZE}`)
        ]);

        // 펫 타입 설정
        if (petTypeResponse.data.length > 0) {
          this.petTypes[index].availableTypes = petTypeResponse.data;
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PET_TYPE}`);
          this.petTypes[index].availableTypes = detailResponse.data.filter(code => code.isActive);
        }

        // 위치 설정
        this.petTypes[index].availableLocations = locationResponse.data;

        // 크기 설정 (바로 detail 데이터 사용)
        this.petTypes[index].availablePetSizes = sizeResponse.data.filter(code => code.isActive);
      } catch (error) {
        console.error('초기 데이터 로딩 실패:', error);
      }
    },

    async loadNextTypes(index) {
      const currentPetType = this.petTypes[index];
      if (!currentPetType.currentType) {
        await this.loadInitialData(index);
        return;
      }
      await this.loadTypesByGroup(currentPetType.currentType.groupId, currentPetType);
    },

    async loadTypesByGroup(groupId, petType) {
      try {
        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${groupId}/sub`);
        
        if (groupResponse.data.length > 0) {
          petType.availableTypes = groupResponse.data;
          if (petType.currentType && !petType.typeHistory.includes(petType.currentType)) {
            petType.typeHistory.push(petType.currentType);
          }
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${groupId}`);
          petType.availableTypes = detailResponse.data.filter(code => code.isActive);
          if (petType.currentType && !petType.typeHistory.includes(petType.currentType)) {
            petType.typeHistory.push(petType.currentType);
          }
        }
      } catch (error) {
        console.error('펫 타입 정보 로딩 실패:', error);
      }
    },

    async loadNextServiceLocations(index) {
      const currentPetType = this.petTypes[index];
      if (!currentPetType.currentLocation) {
        if (currentPetType.locationHistory.length > 0) {
          const previousLocation = currentPetType.locationHistory.pop();
          await this.loadServiceLocationsByGroup(previousLocation.groupId, currentPetType);
        } else {
          // 초기 위치 데이터 로드
          const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.LOCATION}/sub`);
          currentPetType.availableLocations = groupResponse.data;
        }
        return;
      }
      await this.loadServiceLocationsByGroup(currentPetType.currentLocation.groupId, currentPetType);
    },

    async loadServiceLocationsByGroup(groupId, petType) {
      try {
        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${groupId}/sub`);
        
        if (groupResponse.data.length > 0) {
          petType.availableLocations = groupResponse.data;
          if (petType.currentLocation && !petType.locationHistory.includes(petType.currentLocation)) {
            petType.locationHistory.push(petType.currentLocation);
          }
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${groupId}`);
          petType.availableLocations = detailResponse.data.filter(code => code.isActive);
          if (petType.currentLocation && !petType.locationHistory.includes(petType.currentLocation)) {
            petType.locationHistory.push(petType.currentLocation);
          }
        }
      } catch (error) {
        console.error('위치 정보 로딩 실패:', error);
      }
    },

    async loadNextPetSizes(index) {
      // petSize는 detail 데이터이므로 추가 API 호출이 필요하지 않음
      console.log("loadNextPetSizes : ", index);
      return;
    },

    addPetType() {
      const newUniqueId = Math.max(...this.petTypes.map(pt => pt.uniqueId)) + 1;
      const newPetType = {
        currentType: '',
        availableTypes: [],
        typeHistory: [],
        uniqueId: newUniqueId,
        fee: '',
        currentLocation: '',
        availableLocations: [],
        locationHistory: [],
        currentPetSize: '',
        availablePetSizes: [],
        petSizeHistory: []
      };
      this.petTypes.push(newPetType);
      this.loadInitialData(this.petTypes.length - 1);
    },

    removePetType(index) {
      if (index > 0) {
        this.petTypes.splice(index, 1);
      }
    },

    async registerPetSitter() {
      try {
        // 최종 위치 정보 가져오기
        const finalLocation = this.currentLocation || 
          (this.locationHistory.length > 0 ? this.locationHistory[this.locationHistory.length - 1] : null);

        // 1. 펫시터 정보 등록
        const petSitterRequest = {
          userId: this.userId,
          nickname: this.petSitterNickname,
          phoneNumber: this.phoneNumber,
          introduction: this.introduction,
          locationCodeId: finalLocation?.codeId || finalLocation?.groupId,
          locationCodeGroupId: finalLocation?.groupId
        };

        console.log('Sending pet sitter data:', petSitterRequest);
        
        const petSitterResponse = await axios.post('http://localhost:8080/api/v1/pet-sitters', petSitterRequest);

        console.log('Pet sitter response:', petSitterResponse.data);
        
        if (!petSitterResponse.data || !petSitterResponse.data.data) {
          throw new Error('펫시터 등록 응답이 올바르지 않습니다.');
        }

        const petSitterId = petSitterResponse.data.data.petSitterId; // data.data.petSitterId 형식으로 변경
        console.log('Pet sitter registered with ID:', petSitterId);

        // 2. 각 서비스 정보 등록
        const servicePromises = this.petTypes.map(async (pt) => {
          const serviceRequest = {
            petSitterId: petSitterId,
            petTypeCodeId: pt.currentType?.codeId || pt.currentType?.groupId,
            petTypeCodeGroupId: pt.currentType?.groupId,
            locationCodeId: pt.currentLocation?.codeId || pt.currentLocation?.groupId,
            locationCodeGroupId: pt.currentLocation?.groupId,
            petSizeCodeId: pt.currentPetSize?.codeId,
            petSizeCodeGroupId: pt.currentPetSize?.groupId,
            serviceFee: pt.fee
          };

          console.log('Sending service data:', serviceRequest);

          try {
            const serviceResponse = await axios.post(
              'http://localhost:8080/api/v1/pet-sitter-services', 
              serviceRequest
            );
            
            console.log('Service response:', serviceResponse.data);
            
            if (serviceResponse.status !== 201) {
              console.error('Invalid service response:', serviceResponse);
              throw new Error('서비스 등록 응답이 올바르지 않습니다.');
            }
            
            return serviceResponse.data;
          } catch (error) {
            console.error('Service registration error:', error);
            throw new Error(`서비스 등록 실패: ${error.response?.data?.message || error.message}`);
          }
        });

        // 모든 서비스 등록 완료 대기
        await Promise.all(servicePromises);

        alert('펫시터로 성공적으로 등록되었습니다.');
        this.$router.push('/petsitter-my-page');

      } catch (error) {
        console.error('Registration error:', error);
        alert(error.message || '등록 중 오류가 발생했습니다.');
      }
    },

    getUniqueKey(item) {
      if (item.groupId && item.groupName) {
        return `group_${item.groupId}`;
      }
      return `detail_${item.codeId}`;
    }
  },
  async created() {
    await this.loadUserInfo();
    this.email = localStorage.getItem('userEmail') || '';
    await this.loadRootPetTypes();
    await this.loadRootLocations();
    await this.loadInitialData(0); // 첫 번째 서비스 항목의 데이터 로드
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
  margin-bottom: 10px;
}

select {
  flex: 1;
  min-width: 120px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.fee-input-container {
  width: 150px;
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