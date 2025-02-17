<template>
  <div class="register-container">
    <h2>Update Pet Sitter Profile</h2>
    <hr class="divider" />
    <form @submit.prevent="updatePetSitter">
      <div class="form-group-inline">
        <div class="form-group">
          <label>Pet Sitter Nickname *</label>
          <input type="text" v-model="petSitterNickname" required />
        </div>
        <div class="form-group">
          <label>Your Location *</label>
          <div class="location-display" v-if="!isEditingLocation">
            <span class="location-text">
              {{ getLocationDisplayText() }}
            </span>
            <button type="button" @click="startEditingLocation" class="edit-button">
              수정
            </button>
          </div>
          <div class="location-selects" v-else>
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
            <button type="button" @click="confirmLocationEdit" class="confirm-button">
              확인
            </button>
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
        <!-- 기존 서비스 목록 표시 -->
        <div v-for="(petType, index) in petTypes" :key="petType.uniqueId" class="service-item">
          <div class="service-info">
            <div class="service-detail">
              <span class="service-label">Pet Type:</span>
              <span class="service-value">{{ petType.currentType.codeName }}</span>
            </div>
            <div class="service-detail">
              <span class="service-label">Location:</span>
              <span class="service-value">{{ petType.currentLocation.codeName }}</span>
            </div>
            <div class="service-detail">
              <span class="service-label">Pet Size:</span>
              <span class="service-value">{{ petType.currentPetSize.codeName }}</span>
            </div>
            <div class="service-detail">
              <span class="service-label">Service Fee:</span>
              <span class="service-value">₩{{ petType.fee }}/H</span>
            </div>
          </div>
          <button type="button" @click="removePetType(index)" class="remove-button">삭제</button>
        </div>

        <!-- 새 서비스 추가 버튼 -->
        <button type="button" @click="addNewService" class="add-button">+ Add New Service</button>

        <!-- 새 서비스 입력 폼 (모달 또는 확장 패널) -->
        <div v-if="isAddingService" class="new-service-form">
          <div class="pet-type-selects">
            <div v-if="newService.typeHistory.length > 0" class="previous-selection">
              {{ newService.typeHistory[newService.typeHistory.length - 1].groupName || 
                 newService.typeHistory[newService.typeHistory.length - 1].codeName }}
            </div>
            <select 
              v-model="newService.currentType" 
              @change="loadNextTypes(-1)"
              required
            >
              <option value="">동물 선택</option>
              <option 
                v-for="type in newService.availableTypes" 
                :key="getUniqueKey(type)"
                :value="type"
              >
                {{ type.groupName || type.codeName }}
              </option>
            </select>

            <div v-if="newService.locationHistory.length > 0" class="previous-selection">
              {{ newService.locationHistory[newService.locationHistory.length - 1].groupName || 
                 newService.locationHistory[newService.locationHistory.length - 1].codeName }}
            </div>
            <select 
              v-model="newService.currentLocation" 
              @change="loadNextServiceLocations(-1)"
              required
            >
              <option value="">위치 선택</option>
              <option 
                v-for="location in newService.availableLocations" 
                :key="getUniqueKey(location)"
                :value="location"
              >
                {{ location.groupName || location.codeName }}
              </option>
            </select>

            <select 
              v-model="newService.currentPetSize" 
              required
            >
              <option value="">크기 선택</option>
              <option 
                v-for="size in newService.availablePetSizes" 
                :key="getUniqueKey(size)"
                :value="size"
              >
                {{ size.codeName }}
              </option>
            </select>
          </div>
          <div class="fee-input-container">
            <input 
              type="number" 
              v-model="newService.fee" 
              placeholder="요금 입력" 
              required
              min="0"
              class="fee-input"
            />
          </div>
          <div class="button-group">
            <button type="button" @click="confirmAddService" class="confirm-button">확인</button>
            <button type="button" @click="cancelAddService" class="cancel-button">취소</button>
          </div>
        </div>
      </div>
      <div class="form-group">
        <label>Self Introduction</label>
        <textarea v-model="introduction" required class="self-intro"></textarea>
      </div>
      <div class="form-group">
        <label>Available Time Slots</label>
        <div class="time-grid">
          <div class="time-header">
            <div class="time-cell"></div>
            <div class="day-cell" v-for="day in days" :key="day">{{ day }}</div>
          </div>
          <div class="time-row" v-for="hour in timeSlots" :key="hour.time">
            <div class="time-cell">{{ hour.label }}</div>
            <div 
              v-for="day in days" 
              :key="`${day}-${hour.time}`"
              :class="['slot-cell', { selected: isSlotSelected(day, hour.time) }]"
              @click="toggleTimeSlot(day, hour.time)"
            ></div>
          </div>
        </div>
      </div>
      <button type="submit" :class="{'register-button': true, 'valid': isValid}" :disabled="!isValid">Update</button>
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
  name: 'RegisterPetSitterContentsComponent',
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
      }],
      petSitterId: null,
      isEditingLocation: false,
      savedLocation: null,
      savedLocationHistory: [],
      isAddingService: false,
      newService: {
        currentType: '',
        availableTypes: [],
        currentLocation: '',
        availableLocations: [],
        currentPetSize: '',
        availablePetSizes: [],
        fee: ''
      },
      newServices: [], // 새로 추가될 서비스들 추적
      days: ['월', '화', '수', '목', '금', '토', '일'],
      timeSlots: [],
      selectedTimeSlots: new Set(),
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
        if (index === -1) {
          // 새 서비스 추가를 위한 초기 데이터 로드
          await this.loadRootPetTypes();
          this.newService.availableTypes = [...this.availableTypes];
          
          await this.loadRootLocations();
          this.newService.availableLocations = [...this.availableLocations];
          
          // Pet Size 데이터 로드
          const sizeResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PAT_SIZE}`);
          this.newService.availablePetSizes = sizeResponse.data.filter(code => code.isActive);
        } else {
          // 기존 서비스의 초기 데이터 로드
          const currentPetType = this.petTypes[index];
          
          await this.loadRootPetTypes();
          currentPetType.availableTypes = [...this.availableTypes];
          
          await this.loadRootLocations();
          currentPetType.availableLocations = [...this.availableLocations];
          
          // Pet Size 데이터 로드
          const sizeResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PAT_SIZE}`);
          currentPetType.availablePetSizes = sizeResponse.data.filter(code => code.isActive);
        }
      } catch (error) {
        console.error('초기 데이터 로딩 실패:', error);
      }
    },

    async loadNextTypes(index) {
      try {
        const target = index === -1 ? this.newService : this.petTypes[index];
        
        if (!target.currentType) {
          if (target.typeHistory.length > 0) {
            const previousType = target.typeHistory.pop();
            const response = await axios.get(`http://localhost:8080/api/v1/code-details/group/${previousType.groupId}`);
            target.availableTypes = response.data.filter(code => code.isActive);
          }
          return;
        }

        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${target.currentType.groupId}/sub`);
        if (groupResponse.data.length > 0) {
          target.availableTypes = groupResponse.data;
          if (target.currentType && !target.typeHistory.includes(target.currentType)) {
            target.typeHistory.push(target.currentType);
          }
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${target.currentType.groupId}`);
          target.availableTypes = detailResponse.data.filter(code => code.isActive);
          if (target.currentType && !target.typeHistory.includes(target.currentType)) {
            target.typeHistory.push(target.currentType);
          }
        }
      } catch (error) {
        console.error('다음 타입 로딩 실패:', error);
      }
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
      try {
        const target = index === -1 ? this.newService : this.petTypes[index];
        
        if (!target.currentLocation) {
          if (target.locationHistory.length > 0) {
            const previousLocation = target.locationHistory.pop();
            const response = await axios.get(`http://localhost:8080/api/v1/code-details/group/${previousLocation.groupId}`);
            target.availableLocations = response.data.filter(code => code.isActive);
          }
          return;
        }

        const groupResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${target.currentLocation.groupId}/sub`);
        if (groupResponse.data.length > 0) {
          target.availableLocations = groupResponse.data;
          if (target.currentLocation && !target.locationHistory.includes(target.currentLocation)) {
            target.locationHistory.push(target.currentLocation);
          }
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${target.currentLocation.groupId}`);
          target.availableLocations = detailResponse.data.filter(code => code.isActive);
          if (target.currentLocation && !target.locationHistory.includes(target.currentLocation)) {
            target.locationHistory.push(target.currentLocation);
          }
        }
      } catch (error) {
        console.error('다음 위치 로딩 실패:', error);
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

    async removePetType(index) {
      const service = this.petTypes[index];
      console.log('service : ', service);
      if (service.serviceId) {
        // 기존 서비스인 경우 즉시 삭제
        try {
          await axios.delete(`http://localhost:8080/api/v1/pet-sitter-services/${service.serviceId}`);
          this.petTypes.splice(index, 1);
          console.log('서비스 삭제 성공');
        } catch (error) {
          console.error('서비스 삭제 실패:', error);
          alert('서비스 삭제에 실패했습니다.');
        }
      } else {
        // 새로 추가된 서비스인 경우 (아직 저장되지 않은 경우)
        this.petTypes.splice(index, 1);
      }
    },

    async loadPetSitterInfo() {
      try {
        const userId = localStorage.getItem('userId');
        console.log('Loading pet sitter info for userId:', userId);
        
        const response = await axios.get(`http://localhost:8080/api/v1/pet-sitters/user/${userId}`);
        console.log('Pet sitter response:', response.data);
        
        if (response.data.status === 200) {
          const petSitterData = response.data.data;
          console.log('Pet sitter data:', petSitterData);
          
          this.petSitterId = petSitterData.petSitterId;
          this.petSitterNickname = petSitterData.nickname;
          this.phoneNumber = petSitterData.phoneNumber;
          this.introduction = petSitterData.introduction;
          
          // 위치 정보 설정
          await this.loadRootLocations(); // 먼저 루트 위치 로드
          
          if (petSitterData.locationCodeId) {
            // 위치 데이터 로드
            await this.loadLocationsByGroup(petSitterData.locationCodeGroupId);
            
            // 현재 위치 찾기
            this.currentLocation = this.availableLocations.find(
              loc => loc.codeId === petSitterData.locationCodeId || loc.groupId === petSitterData.locationCodeGroupId
            );

            if (petSitterData.locationCodeGroup) {
              this.locationHistory = [petSitterData.locationCodeGroup];
            }
          }

          // 서비스 정보 로드
          await this.loadPetSitterServices();
        }
      } catch (error) {
        console.error('펫시터 정보 로딩 실패:', error);
        if (error.response) {
          console.error('Error response:', error.response.data);
        }
      }
    },

    async loadPetSitterServices() {
      try {
        console.log('Loading services for petSitterId:', this.petSitterId);
        
        const response = await axios.get(`http://localhost:8080/api/v1/pet-sitter-services/pet-sitter/${this.petSitterId}`);
        console.log('Services response:', response.data);

        console.log('response : ', response);
        
        if (response.status === 200) {
          const services = response.data;
          console.log('Services data:', services);
          
          // 서비스 데이터 직접 매핑
          this.petTypes = services.map(service => ({
            serviceId: service.serviceId,  // serviceId 추가
            currentType: {
              codeId: service.petTypeCodeId,
              groupId: service.petTypeCodeGroupId,
              codeName: service.petTypeCodeName,
            },
            currentLocation: {
              codeId: service.locationCodeId,
              groupId: service.locationCodeGroupId,
              codeName: service.locationCodeName,
            },
            currentPetSize: {
              codeId: service.petSizeCodeId,
              groupId: service.petSizeCodeGroupId,
              codeName: service.petSizeCodeName
            },
            fee: service.serviceFee,
            uniqueId: Math.random(),
            // 나머지 필드들은 필요할 때 사용
            availableTypes: [],
            typeHistory: [],
            availableLocations: [],
            locationHistory: [],
            availablePetSizes: [],
            petSizeHistory: []
          }));

          console.log('Mapped petTypes:', this.petTypes);
        }
      } catch (error) {
        console.error('서비스 정보 로딩 실패:', error);
        if (error.response) {
          console.error('Error response:', error.response.data);
        }
      }
    },

    async updatePetSitter() {
      try {
        // 1. 프로필 업데이트
        const profileUpdateResponse = await axios.put(`http://localhost:8080/api/v1/pet-sitters/${this.petSitterId}`, {
          nickname: this.petSitterNickname,
          phoneNumber: this.phoneNumber,
          locationCodeId: this.currentLocation.codeId,
          locationCodeGroupId: this.currentLocation.groupId,
          introduction: this.introduction
        });

        if (profileUpdateResponse.data.status !== 200) {
          throw new Error('프로필 업데이트에 실패했습니다.');
        }

        // 2. 새로운 서비스만 추가
        for (const service of this.petTypes) {
          // serviceId가 없는 것만 새로운 서비스로 처리
          if (!service.serviceId) {
            const newServiceData = {
              petSitterId: this.petSitterId,
              petTypeCodeId: service.currentType.codeId,
              petTypeCodeGroupId: service.currentType.groupId,
              locationCodeId: service.currentLocation.codeId,
              locationCodeGroupId: service.currentLocation.groupId,
              petSizeCodeId: service.currentPetSize.codeId,
              petSizeCodeGroupId: service.currentPetSize.groupId,
              serviceFee: service.fee
            };
            await axios.post('http://localhost:8080/api/v1/pet-sitter-services', newServiceData);
          }
        }

        // 3. 새로 추가 중인 서비스 처리 (isAddingService가 true인 경우)
        if (this.isAddingService && this.newService) {
          const newServiceData = {
            petSitterId: this.petSitterId,
            petTypeCodeId: this.newService.currentType.codeId,
            petTypeCodeGroupId: this.newService.currentType.groupId,
            locationCodeId: this.newService.currentLocation.codeId,
            locationCodeGroupId: this.newService.currentLocation.groupId,
            petSizeCodeId: this.newService.currentPetSize.codeId,
            petSizeCodeGroupId: this.newService.currentPetSize.groupId,
            serviceFee: this.newService.fee
          };
          await axios.post('http://localhost:8080/api/v1/pet-sitter-services', newServiceData);
        }

        // 가능 시간 업데이트
        const availabilityData = Array.from(this.selectedTimeSlots).map(slot => {
          const [day, time] = slot.split('-');
          return {
            dayOfWeek: this.convertDayToEnglish(day),
            time: time
          };
        });

        await axios.put(
          `http://localhost:8080/api/v1/petsitters/${this.petSitterId}/availability`,
          availabilityData
        );

        alert('프로필과 서비스가 성공적으로 업데이트되었습니다.');
        this.$router.push('/petsitter-my-page');

      } catch (error) {
        console.error('업데이트 실패:', error);
        alert(error.response?.data?.message || '업데이트 중 오류가 발생했습니다.');
      }
    },

    getLocationDisplayText() {
      if (!this.currentLocation && this.locationHistory.length === 0) {
        return '위치를 선택해주세요';
      }
      
      const locations = [...this.locationHistory];
      if (this.currentLocation) {
        locations.push(this.currentLocation);
      }
      
      return locations
        .map(loc => loc.groupName || loc.codeName)
        .join(' > ');
    },

    startEditingLocation() {
      this.isEditingLocation = true;
      // 현재 선택값 저장
      this.savedLocation = this.currentLocation;
      this.savedLocationHistory = [...this.locationHistory];
    },

    confirmLocationEdit() {
      this.isEditingLocation = false;
    },

    getUniqueKey(item) {
      if (item.groupId && item.groupName) {
        return `group_${item.groupId}`;
      }
      return `detail_${item.codeId}`;
    },

    getServiceDisplayText(item) {
      if (!item) return '-';
      if (item.groupName && item.codeName) {
        return `${item.groupName} > ${item.codeName}`;
      }
      return item.codeName || item.groupName || '-';
    },

    async addNewService() {
      try {
        this.isAddingService = true;
        this.newService = {
          currentType: '',
          availableTypes: [],
          typeHistory: [],
          currentLocation: '',
          availableLocations: [],
          locationHistory: [],
          currentPetSize: '',
          availablePetSizes: [],
          petSizeHistory: [],
          fee: ''
        };

        // 펫 타입 초기 데이터 로드
        const petTypeResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.PET_TYPE}/sub`);
        if (petTypeResponse.data.length > 0) {
          this.newService.availableTypes = petTypeResponse.data;
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PET_TYPE}`);
          this.newService.availableTypes = detailResponse.data.filter(code => code.isActive);
        }

        // 위치 초기 데이터 로드
        const locationResponse = await axios.get(`http://localhost:8080/api/v1/code-groups/${CODE_GROUP.LOCATION}/sub`);
        if (locationResponse.data.length > 0) {
          this.newService.availableLocations = locationResponse.data;
        } else {
          const detailResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.LOCATION}`);
          this.newService.availableLocations = detailResponse.data.filter(code => code.isActive);
        }

        // 펫 사이즈 초기 데이터 로드
        const sizeResponse = await axios.get(`http://localhost:8080/api/v1/code-details/group/${CODE_GROUP.PAT_SIZE}`);
        this.newService.availablePetSizes = sizeResponse.data.filter(code => code.isActive);

      } catch (error) {
        console.error('새 서비스 초기화 실패:', error);
      }
    },

    async confirmAddService() {
      if (this.validateNewService()) {
        this.petTypes.push({...this.newService});
        this.resetNewService();
        this.isAddingService = false;
      }
    },

    cancelAddService() {
      this.resetNewService();
      this.isAddingService = false;
    },

    resetNewService() {
      this.newService = {
        currentType: '',
        availableTypes: [],
        currentLocation: '',
        availableLocations: [],
        currentPetSize: '',
        availablePetSizes: [],
        fee: '',
        typeHistory: [],
        locationHistory: [],
        petSizeHistory: []
      };
    },

    validateNewService() {
      return (
        this.newService.currentType &&
        this.newService.currentLocation &&
        this.newService.currentPetSize &&
        this.newService.fee
      );
    },

    initializeTimeSlots() {
      this.timeSlots = [];
      for (let hour = 0; hour < 24; hour++) {
        for (let minute of ['00', '30']) {
          const time = `${hour.toString().padStart(2, '0')}:${minute}`;
          const label = `${hour.toString().padStart(2, '0')}:${minute}`;
          this.timeSlots.push({ time, label });
        }
      }
    },

    isSlotSelected(day, time) {
      return this.selectedTimeSlots.has(`${day}-${time}`);
    },

    toggleTimeSlot(day, time) {
      const slotKey = `${day}-${time}`;
      if (this.selectedTimeSlots.has(slotKey)) {
        this.selectedTimeSlots.delete(slotKey);
      } else {
        this.selectedTimeSlots.add(slotKey);
      }
    },

    async loadAvailability() {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/petsitters/${this.petSitterId}/availability`);
        console.log('Availability response:', response.data);
        
        this.selectedTimeSlots = new Set();
        
        // 각 요일별로 순회
        Object.entries(response.data).forEach(([day, timeSlots]) => {
          // timeSlots 배열을 순회하면서 true인 시간대만 선택
          timeSlots.forEach((isAvailable, index) => {
            if (isAvailable) {
              const hour = Math.floor(index / 2);
              const minute = (index % 2) * 30;
              const time = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
              // MONDAY -> 월 변환
              const koreanDay = this.convertDayToKorean(day);
              this.selectedTimeSlots.add(`${koreanDay}-${time}`);
            }
          });
        });
      } catch (error) {
        console.error('가능 시간 로딩 실패:', error);
      }
    },

    // 영어 요일을 한글로 변환하는 메서드
    convertDayToKorean(day) {
      const dayMap = {
        'MONDAY': '월',
        'TUESDAY': '화',
        'WEDNESDAY': '수',
        'THURSDAY': '목',
        'FRIDAY': '금',
        'SATURDAY': '토',
        'SUNDAY': '일'
      };
      return dayMap[day];
    },

    // 한글 요일을 영어로 변환하는 메서드 추가
    convertDayToEnglish(day) {
      const dayMap = {
        '월': 'MONDAY',
        '화': 'TUESDAY',
        '수': 'WEDNESDAY',
        '목': 'THURSDAY',
        '금': 'FRIDAY',
        '토': 'SATURDAY',
        '일': 'SUNDAY'
      };
      return dayMap[day];
    },
  },
  async created() {
    console.log('Component created');
    await this.loadUserInfo();
    console.log('User info loaded');
    this.email = localStorage.getItem('userEmail') || '';
    console.log('Email loaded:', this.email);
    await this.loadPetSitterInfo();
    console.log('Pet sitter info loaded');
    await this.loadRootPetTypes();
    console.log('Root pet types loaded');
    await this.loadRootLocations();
    console.log('Root locations loaded');
    this.initializeTimeSlots();
    await this.loadAvailability();
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

.location-display {
  display: flex;
  align-items: center;
  gap: 10px;
  margin: 10px 0;
}

.location-text {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f5f5f5;
}

.edit-button, .confirm-button {
  padding: 8px 16px;
  background-color: #6a1b9a;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.edit-button:hover, .confirm-button:hover {
  background-color: #8e24aa;
}

.location-selects {
  display: flex;
  gap: 10px;
  align-items: center;
  margin: 10px 0;
}

.previous-selection {
  color: #6a1b9a;
  font-weight: bold;
  padding: 8px;
  background-color: #f3e5f5;
  border-radius: 5px;
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

.service-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  margin: 10px 0;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #f8f9fa;
}

.service-info {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.service-detail {
  display: flex;
  align-items: center;
  gap: 8px;
}

.service-label {
  font-weight: bold;
  color: #6a1b9a;
  min-width: 100px;
}

.service-value {
  color: #333;
}

.new-service-form {
  margin-top: 15px;
  padding: 15px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  background-color: #fff;
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 15px;
  justify-content: flex-end;
}

.confirm-button, .cancel-button {
  padding: 8px 16px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.confirm-button {
  background-color: #6a1b9a;
  color: white;
}

.cancel-button {
  background-color: #e0e0e0;
  color: #333;
}

.add-button {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  background-color: #6a1b9a;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.remove-button {
  padding: 8px 16px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.remove-button:hover {
  background-color: #c82333;
}

.time-grid {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  overflow: hidden;
  margin-top: 10px;
}

.time-header {
  display: grid;
  grid-template-columns: 80px repeat(7, 1fr);
  background-color: #f8f9fa;
  border-bottom: 1px solid #e0e0e0;
}

.time-row {
  display: grid;
  grid-template-columns: 80px repeat(7, 1fr);
  border-bottom: 1px solid #e0e0e0;
}

.time-row:last-child {
  border-bottom: none;
}

.time-cell {
  padding: 8px;
  text-align: center;
  font-size: 12px;
  border-right: 1px solid #e0e0e0;
}

.day-cell {
  padding: 8px;
  text-align: center;
  font-weight: bold;
  border-right: 1px solid #e0e0e0;
}

.slot-cell {
  padding: 8px;
  border-right: 1px solid #e0e0e0;
  cursor: pointer;
  transition: background-color 0.2s;
}

.slot-cell:hover {
  background-color: #f3e5f5;
}

.slot-cell.selected {
  background-color: #6a1b9a;
}

.time-grid {
  max-height: 400px;
  overflow-y: auto;
}
</style> 