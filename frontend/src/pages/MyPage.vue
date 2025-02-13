<template>
  <div class="background">
    <header class="header">
      <div class="left">
        <button @click="goToMain" class="header-button">PetPal</button>
      </div>
      <div class="right">
        <button @click="logout" class="header-button">로그아웃</button>
        <button class="header-button">도움말</button>
        <button class="header-button">ABOUT US</button>
      </div>
    </header>
    <div class="my-page-container">
      <h2>개인 정보</h2>
      <form @submit.prevent="updateInfo">
        <div class="form-group">
          <label>이름</label>
          <input type="text" v-model="name" class="input-field" disabled />
        </div>
        <div class="form-group">
          <label>이메일</label>
          <input type="email" v-model="email" class="input-field" disabled />
        </div>
        <div class="form-group">
          <label>휴대전화</label>
          <input type="tel" v-model="phone" class="input-field" disabled />
        </div>
        <div class="form-group">
          <label>가입일</label>
          <input type="text" v-model="joinDate" class="input-field" disabled />
        </div>
        <div class="form-group">
          <label>상태</label>
          <input type="text" v-model="state" class="input-field" disabled />
        </div>
        <div class="button-group">
          <button type="button" class="button" @click="changePassword">비밀번호 변경</button>
          <button type="submit" @click="goToUpdate">수정하기</button>
        </div>
      </form>
    </div>
    <footer class="footer">
      <button @click="goToDelete" class="footer-button">탈퇴하기</button>
    </footer>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: '',
      email: '',
      phone: '',
      joinDate: '',
      state: ''
    };
  },
  created() {
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      const userId = localStorage.getItem('userId');
      if (!userId) {
        alert('로그인이 필요합니다.');
        this.$router.push('/login');
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/v1/users/${userId}`);
        if (response.status === 200) {
          const userData = response.data.data;
          this.name = userData.name;
          this.email = userData.email;
          this.phone = userData.phoneNumber || ''; // phone이 없을 경우 빈 문자열
          this.joinDate = userData.createdAt || ''; // joinDate가 없을 경우 빈 문자열
          this.state = userData.state || ''; // 상태를 state로 변경
        }
      } catch (error) {
        console.error('사용자 정보 조회 중 오류 발생:', error);
        alert('사용자 정보를 불러오는 데 실패했습니다.');
      }
    },
    goToMain() {
      this.$router.push('/');
    },
    goToUpdate() {
      this.$router.push({
        path: '/update',
        query: {
          name: this.name,
          phone: this.phone
        }
      });
    },
    changePassword() {
      this.$router.push('/update-password'); // 비밀번호 변경 페이지로 이동
    },
    goToDelete() {
      this.$router.push('/delete');
    },
    async logout() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/logout');
        if (response.status === 200) {
          localStorage.removeItem('userId');
          localStorage.removeItem('userName');
          localStorage.removeItem('userEmail');
          alert('로그아웃 되었습니다.');
          this.$router.push('/');
        }
      } catch (error) {
        console.error('로그아웃 중 오류 발생:', error);
        alert('로그아웃에 실패했습니다.');
      }
    }
  }
}
</script>

<style scoped>
.background {
  background-color: #e0e0e0;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}

.header, .footer {
  width: 100%;
  background-color: white;
  color: black;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 15px;
}

.header-button, .footer-button {
  background: none;
  border: none;
  color: black;
  cursor: pointer;
  font-size: 0.9em;
}

.header-button:hover, .footer-button:hover {
  text-decoration: underline;
}

.my-page-container {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  width: 600px;
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
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

label {
  width: 80px;
  text-align: left;
  margin-right: 10px;
  color: #555;
}

.input-field {
  flex: 1;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.button {
  background-color: #ff6b6b;
  color: white;
  border: none;
  padding: 8px 20px;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.button:hover {
  background-color: #ff4c4c;
}
</style> 