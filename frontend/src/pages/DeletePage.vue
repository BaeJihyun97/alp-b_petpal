<template>
  <div class="background">
    <div class="delete-container">
      <h2>계정 탈퇴</h2>
      <p>정말로 탈퇴하시겠습니까?</p>
      <div class="button-group">
        <button type="button" class="button" @click="confirmDelete">탈퇴</button>
        <button type="button" class="button cancel" @click="cancel">취소</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
    async confirmDelete() {
      if (confirm('정말로 탈퇴하시겠습니까?')) {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          alert('로그인이 필요합니다.');
          this.$router.push('/login');
          return;
        }

        try {
          const response = await axios.delete(`http://localhost:8080/api/v1/users/${userId}`);
          if (response.status === 200) {
            await this.logout();
            alert('탈퇴 처리되었습니다.');
          }
        } catch (error) {
          console.error('탈퇴 중 오류 발생:', error);
          alert('탈퇴에 실패했습니다.');
        }
      }
    },
    async logout() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/logout');
        if (response.status === 200) {
          this.clearLocalStorage();
          alert('로그아웃 되었습니다.');
          this.$router.push('/');
        }
      } catch (error) {
        console.error('로그아웃 중 오류 발생:', error);
        alert('로그아웃에 실패했습니다.');
      }
    },
    clearLocalStorage() {
      localStorage.removeItem('userId');
      localStorage.removeItem('userName');
      localStorage.removeItem('userEmail');
    },
    cancel() {
      this.$router.push('/my-page');
    }
  }
}
</script>

<style scoped>
.background {
  background-color: #808080;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.delete-container {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

p {
  margin-bottom: 20px;
  color: #555;
}

.button-group {
  display: flex;
  justify-content: space-between;
}

.button {
  background-color: #ff6b6b;
  color: white;
  border: none;
  padding: 8px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.button.cancel {
  background-color: #ccc;
}

.button:hover {
  background-color: #ff4c4c;
}

.button.cancel:hover {
  background-color: #aaa;
}
</style> 