<template>
  <div class="login-page">
    <div class="login-box">
      <h2><span class="highlight">PetPal</span> 로그인</h2>
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="email">email</label>
          <input type="email" id="email" v-model="email" required />
        </div>
        <div class="input-group">
          <label for="password">password</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <button type="submit" class="login-button">로그인</button>
      </form>
      <p>계정이 없으신가요? <a @click.prevent="goToRegister">가입하기</a></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'LoginPage',
  data() {
    return {
      email: '',
      password: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/login', {
          email: this.email,
          password: this.password
        });

        if (response.status === 200) {
          console.log('로그인 성공');
          console.log(response);
          const userData = response.data.data; // 로그인 성공 시 받은 사용자 데이터
          localStorage.setItem('userId', userData.userId); // userId 저장
          localStorage.setItem('userName', userData.name); // 사용자 이름 저장
          localStorage.setItem('userEmail', userData.email); // 사용자 이메일 저장
          this.$router.push('/'); // 로그인 성공 시 메인 페이지로 이동
        }
      } catch (error) {
        if (error.response && error.response.status >= 400) {
          alert('로그인 실패: 이메일 또는 비밀번호를 확인하세요.');
        } else {
          console.error('로그인 중 오류 발생:', error);
        }
      }
    },
    goToRegister() {
      this.$router.push('/register'); // 회원가입 페이지로 이동
    }
  }
};
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #808080;
}

.login-box {
  background-color: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.highlight {
  color: #ff6b6b;
}

.input-group {
  margin-bottom: 20px;
  text-align: left;
}

label {
  display: block;
  margin-bottom: 5px;
  font-size: 14px;
}

input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
}

.login-button {
  width: 100%;
  padding: 10px;
  background-color: #ff6b6b;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.login-button:hover {
  background-color: #ff4c4c;
}

p {
  margin-top: 20px;
  font-size: 14px;
  color: #888;
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style> 