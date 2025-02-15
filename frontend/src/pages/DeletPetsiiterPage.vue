<template>
  <div class="overlay">
    <div class="modal">
      <h2>펫시터 계정을 삭제하시겠습니까?</h2>
      <p>펫시터 계정을 삭제하시면 모든 사용자가 접근할 수 없습니다. 또한, 복구할 수 없는 점을 감안하시기 바랍니다.</p>
      <div class="button-group">
        <button @click="deleteAccount" class="delete-button">삭제</button>
        <button @click="cancel" class="cancel-button">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router';

export default {
  setup() {
    const router = useRouter();

    return {
      deleteAccount() {
        // 계정 삭제 로직 구현
        alert('계정이 삭제되었습니다.');
      },
      cancel() {
        // PetsitterMyPage로 돌아가기
        router.push({ name: 'PetsitterMyPage' });
      }
    };
  }
};
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.button-group {
  margin-top: 20px;
}

.delete-button {
  background-color: #e74c3c; /* 빨간색 버튼 */
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  margin-right: 10px;
}

.cancel-button {
  background-color: #bdc3c7; /* 회색 버튼 */
  color: black;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
}
</style> 