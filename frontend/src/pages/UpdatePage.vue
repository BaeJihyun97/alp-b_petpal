<template>
  <div class="background">
    <div class="update-container">
      <h2>내 정보 변경하기</h2>
      <form @submit.prevent="updateInfo">
        <div class="form-group">
          <label>이름</label>
          <input type="text" v-model="name" class="input-field" />
        </div>
        <div class="form-group">
          <label>휴대전화</label>
          <input type="tel" v-model="phone" class="input-field" />
        </div>
        <div class="button-group">
          <button type="submit" class="button">변경</button>
          <button type="button" class="button cancel" @click="cancel">취소</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: this.$route.query.name || '',
      phone: this.$route.query.phone || ''
    };
  },
  methods: {
    async updateInfo() {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        alert('로그인이 필요합니다.');
        this.$router.push('/login');
        return;
      }

      try {
        const response = await axios.put(`http://localhost:8080/api/v1/users/${userId}`, {
          name: this.name,
          phoneNumber: this.phone
        });

        if (response.status === 200) {
          alert('정보가 변경되었습니다.');
          this.$router.push('/my-page');
        }
      } catch (error) {
        console.error('정보 변경 중 오류 발생:', error);
        alert('정보 변경에 실패했습니다.');
      }
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

.update-container {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
}

.input-field {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
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