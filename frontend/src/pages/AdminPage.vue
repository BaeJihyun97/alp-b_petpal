<template>
    <div class="admin-container">
      <!-- 코드 그룹 섹션 -->
      <div class="code-group-section">
        <!-- 현재 그룹 정보 표시 추가 -->
        <div v-if="currentGroup" class="current-group-info">
          <div class="group-info">
            <h3>현재 그룹: {{ currentGroup.groupName }} ({{ currentGroup.groupId }})</h3>
            <button @click="goToParentGroup" v-if="currentGroup.parentGroupId">
              상위 그룹으로 돌아가기
            </button>
          </div>
        </div>
  
        <div class="section-header">
          <h2>코드 그룹 관리</h2>
          <div class="header-buttons">
            <button @click="openAddGroupModal">새 그룹 추가</button>
            <button @click="loadMainGroups">최상위 그룹만 보기</button>
            <button @click="loadAllGroups">전체 그룹 보기</button>
          </div>
        </div>
  
        <!-- 하위 그룹이 없을 경우 표시 -->
        <div v-if="codeGroups.length === 0" class="no-data">
          <p>하위 그룹이 없습니다.</p>
          <button @click="openAddGroupModal">하위 그룹 만들기</button>
        </div>
  
        <table v-else class="data-table">
          <thead>
            <tr>
              <th>그룹 ID</th>
              <th>그룹명</th>
              <th>상위그룹 ID</th>
              <th>설명</th>
              <th>생성일</th>
              <th>수정일</th>
              <th>작업</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="group in codeGroups" :key="group.groupId">
              <td>{{ group.groupId }}</td>
              <td>{{ group.groupName }}</td>
              <td>{{ group.parentGroupId || '-' }}</td>
              <td>{{ group.description }}</td>
              <td>{{ formatDate(group.createdAt) }}</td>
              <td>{{ formatDate(group.updatedAt) }}</td>
              <td class="action-buttons">
                <button @click="openEditGroupModal(group)">수정</button>
                <button @click="confirmDeleteGroup(group.groupId)">삭제</button>
                <button @click="loadSubGroups(group)">하위그룹</button>
                <button @click="loadGroupDetails(group)">상세코드</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <!-- 코드 상세 섹션 -->
      <div v-if="selectedGroup" class="code-detail-section">
        <div class="section-header">
          <h2>코드 상세 관리 - {{ selectedGroup.groupName }}</h2>
          <button @click="openAddDetailModal">새 코드 추가</button>
        </div>
  
        <table class="data-table">
          <thead>
            <tr>
              <th>코드 ID</th>
              <th>코드명</th>
              <th>코드값</th>
              <th>정렬순서</th>
              <th>사용여부</th>
              <th>생성일</th>
              <th>수정일</th>
              <th>작업</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="detail in codeDetails" :key="`${detail.groupId}-${detail.codeId}`">
              <td>{{ detail.codeId }}</td>
              <td>{{ detail.codeName }}</td>
              <td>{{ detail.codeValue }}</td>
              <td>{{ detail.sortOrder }}</td>
              <td>{{ detail.isActive ? '사용' : '미사용' }}</td>
              <td>{{ formatDate(detail.createdAt) }}</td>
              <td>{{ formatDate(detail.updatedAt) }}</td>
              <td class="action-buttons">
                <button @click="openEditDetailModal(detail)">수정</button>
                <button @click="confirmDeleteDetail(detail)">삭제</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
  
      <!-- 코드 그룹 모달 -->
      <ModalComponent v-if="showGroupModal" @close="closeGroupModal">
        <template #header>
          <h3>{{ isEditMode ? '코드 그룹 수정' : '새 코드 그룹 추가' }}</h3>
        </template>
        <template #body>
          <form @submit.prevent="submitGroupForm">
            <div class="form-group">
              <label>그룹 ID</label>
              <input v-model="groupForm.groupId" :disabled="isEditMode" required>
            </div>
            <div class="form-group">
              <label>그룹명</label>
              <input v-model="groupForm.groupName" required>
            </div>
            <div class="form-group">
              <label>상위 그룹 ID</label>
              <input v-model="groupForm.parentGroupId">
            </div>
            <div class="form-group">
              <label>설명</label>
              <textarea v-model="groupForm.description"></textarea>
            </div>
            <div class="form-actions">
              <button type="submit">저장</button>
              <button type="button" @click="closeGroupModal">취소</button>
            </div>
          </form>
        </template>
      </ModalComponent>
  
      <!-- 코드 상세 모달 -->
      <ModalComponent v-if="showDetailModal" @close="closeDetailModal">
        <template #header>
          <h3>{{ isEditMode ? '코드 상세 수정' : '새 코드 상세 추가' }}</h3>
        </template>
        <template #body>
          <form @submit.prevent="submitDetailForm">
            <div class="form-group">
              <label>코드 ID</label>
              <input v-model="detailForm.codeId" :disabled="isEditMode" required>
            </div>
            <div class="form-group">
              <label>코드명</label>
              <input v-model="detailForm.codeName" required>
            </div>
            <div class="form-group">
              <label>코드값</label>
              <input v-model="detailForm.codeValue">
            </div>
            <div class="form-group">
              <label>정렬순서</label>
              <input type="number" v-model="detailForm.sortOrder">
            </div>
            <div class="form-group">
              <label>
                <input type="checkbox" v-model="detailForm.isActive">
                사용여부
              </label>
            </div>
            <div class="form-actions">
              <button type="submit">저장</button>
              <button type="button" @click="closeDetailModal">취소</button>
            </div>
          </form>
        </template>
      </ModalComponent>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue'
  import axios from 'axios'
  import ModalComponent from '@/components/ModalComponent.vue'
  
  export default {
    name: 'AdminPage',
    components: { ModalComponent },
    setup() {
      // 상태 관리
      const codeGroups = ref([])
      const codeDetails = ref([])
      const selectedGroup = ref(null)
      const showGroupModal = ref(false)
      const showDetailModal = ref(false)
      const isEditMode = ref(false)
      const currentGroup = ref(null)
  
      // 폼 데이터
      const groupForm = ref({
        groupId: '',
        groupName: '',
        parentGroupId: '',
        description: ''
      })
  
      const detailForm = ref({
        codeId: '',
        groupId: '',
        codeName: '',
        codeValue: '',
        sortOrder: 0,
        isActive: true
      })
  
      // API 호출 함수들
      const loadAllGroups = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/v1/code-groups')
          codeGroups.value = response.data
        } catch (error) {
          console.error('그룹 로딩 실패:', error)
          alert('그룹 목록을 불러오는데 실패했습니다.')
        }
      }
  
      const loadMainGroups = async () => {
        try {
          const response = await axios.get('http://localhost:8080/api/v1/code-groups/main')
          codeGroups.value = response.data
        } catch (error) {
          console.error('메인 그룹 로딩 실패:', error)
          alert('최상위 그룹 목록을 불러오는데 실패했습니다.')
        }
      }
  
      const loadSubGroups = async (group) => {
        currentGroup.value = group
        try {
          const response = await axios.get(`http://localhost:8080/api/v1/code-groups/${group.groupId}/sub`)
          codeGroups.value = response.data
        } catch (error) {
          console.error('하위 그룹 로딩 실패:', error)
          alert('하위 그룹 목록을 불러오는데 실패했습니다.')
        }
      }
  
      const loadGroupDetails = async (group) => {
        selectedGroup.value = group
        try {
          const response = await axios.get(`http://localhost:8080/api/v1/code-details/group/${group.groupId}`)
          codeDetails.value = response.data
        } catch (error) {
          console.error('코드 상세 로딩 실패:', error)
          alert('코드 상세 목록을 불러오는데 실패했습니다.')
        }
      }
  
      // 에러 처리 함수 추가
      const handleError = (error) => {
        const errorMessage = error.response?.data?.message || '작업 중 오류가 발생했습니다.';
        alert(errorMessage);
      };
  
      // 그룹 CRUD
      const submitGroupForm = async () => {
        try {
          if (isEditMode.value) {
            await axios.put(`http://localhost:8080/api/v1/code-groups/${groupForm.value.groupId}`, groupForm.value)
          } else {
            await axios.post('http://localhost:8080/api/v1/code-groups', groupForm.value)
          }
          closeGroupModal()
          loadAllGroups()
        } catch (error) {
          console.error('그룹 저장 실패:', error)
          handleError(error)
        }
      }
  
      const confirmDeleteGroup = async (groupId) => {
        if (!confirm('정말 삭제하시겠습니까?')) return
        try {
          await axios.delete(`http://localhost:8080/api/v1/code-groups/${groupId}`)
          loadAllGroups()
        } catch (error) {
          console.error('그룹 삭제 실패:', error)
          handleError(error)
        }
      }
  
      // 코드 상세 CRUD
      const submitDetailForm = async () => {
        try {
          detailForm.value.groupId = selectedGroup.value.groupId
          if (isEditMode.value) {
            await axios.put(
              `http://localhost:8080/api/v1/code-details/${detailForm.value.groupId}/${detailForm.value.codeId}`, 
              detailForm.value
            )
          } else {
            await axios.post('http://localhost:8080/api/v1/code-details', detailForm.value)
          }
          closeDetailModal()
          loadGroupDetails(selectedGroup.value)
        } catch (error) {
          console.error('코드 상세 저장 실패:', error)
          handleError(error)
        }
      }
  
      const confirmDeleteDetail = async (detail) => {
        if (!confirm('정말 삭제하시겠습니까?')) return
        try {
          await axios.delete(`http://localhost:8080/api/v1/code-details/${detail.groupId}/${detail.codeId}`)
          loadGroupDetails(selectedGroup.value)
        } catch (error) {
          console.error('코드 상세 삭제 실패:', error)
          handleError(error)
        }
      }
  
      // 모달 제어
      const openAddGroupModal = () => {
        isEditMode.value = false
        groupForm.value = {
          groupId: '',
          groupName: '',
          parentGroupId: currentGroup.value?.groupId || '',
          description: ''
        }
        showGroupModal.value = true
      }
  
      const openEditGroupModal = (group) => {
        isEditMode.value = true
        groupForm.value = { ...group }
        showGroupModal.value = true
      }
  
      const openAddDetailModal = () => {
        isEditMode.value = false
        detailForm.value = {
          codeId: '',
          groupId: selectedGroup.value.groupId,
          codeName: '',
          codeValue: '',
          sortOrder: 0,
          isActive: true
        }
        showDetailModal.value = true
      }
  
      const openEditDetailModal = (detail) => {
        isEditMode.value = true
        detailForm.value = { ...detail }
        showDetailModal.value = true
      }
  
      const closeGroupModal = () => {
        showGroupModal.value = false
        groupForm.value = {
          groupId: '',
          groupName: '',
          parentGroupId: '',
          description: ''
        }
      }
  
      const closeDetailModal = () => {
        showDetailModal.value = false
        detailForm.value = {
          codeId: '',
          groupId: '',
          codeName: '',
          codeValue: '',
          sortOrder: 0,
          isActive: true
        }
      }
  
      // 유틸리티 함수
      const formatDate = (date) => {
        return new Date(date).toLocaleString()
      }
  
      // 상위 그룹으로 이동하는 함수 추가
      const goToParentGroup = async () => {
        if (!currentGroup.value?.parentGroupId) return
        try {
          const response = await axios.get(`http://localhost:8080/api/v1/code-groups/${currentGroup.value.parentGroupId}`)
          const parentGroup = response.data
          currentGroup.value = parentGroup
          loadSubGroups(parentGroup)
        } catch (error) {
          console.error('상위 그룹 로딩 실패:', error)
          handleError(error)
        }
      }
  
      // 초기 로딩
      onMounted(loadAllGroups)
  
      return {
        // 상태
        codeGroups,
        codeDetails,
        selectedGroup,
        showGroupModal,
        showDetailModal,
        isEditMode,
        groupForm,
        detailForm,
        currentGroup,
  
        // 메서드
        loadAllGroups,
        loadMainGroups,
        loadSubGroups,
        loadGroupDetails,
        submitGroupForm,
        submitDetailForm,
        confirmDeleteGroup,
        confirmDeleteDetail,
        openAddGroupModal,
        openEditGroupModal,
        openAddDetailModal,
        openEditDetailModal,
        closeGroupModal,
        closeDetailModal,
        formatDate,
        goToParentGroup,
        handleError
      }
    }
  }
  </script>
  
  <style scoped>
  .admin-container {
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
  }
  
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
  
  .header-buttons {
    display: flex;
    gap: 10px;
  }
  
  .data-table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 30px;
  }
  
  .data-table th,
  .data-table td {
    border: 1px solid #ddd;
    padding: 12px;
    text-align: left;
  }
  
  .data-table th {
    background-color: #f5f5f5;
    font-weight: bold;
  }
  
  .data-table tr:nth-child(even) {
    background-color: #f9f9f9;
  }
  
  .action-buttons {
    display: flex;
    gap: 5px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  .form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
  }
  
  .form-group input,
  .form-group textarea {
    width: 100%;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }
  
  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
  }
  
  button {
    padding: 8px 16px;
    border: none;
    border-radius: 4px;
    background-color: #4CAF50;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  button[type="button"] {
    background-color: #666;
  }
  
  button[type="button"]:hover {
    background-color: #555;
  }
  
  .current-group-info {
    background-color: #f5f5f5;
    padding: 15px;
    margin-bottom: 20px;
    border-radius: 4px;
    border: 1px solid #ddd;
  }
  
  .group-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .group-info h3 {
    margin: 0;
    color: #333;
  }
  
  .no-data {
    text-align: center;
    padding: 30px;
    background-color: #f9f9f9;
    border-radius: 4px;
    margin: 20px 0;
  }
  
  .no-data p {
    margin-bottom: 15px;
    color: #666;
  }
  </style>
  