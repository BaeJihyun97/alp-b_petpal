<template>
    <div class="background">
      <div class="update-container">
        <h2>비밀번호 변경하기</h2>
        <form @submit.prevent="updatePassword">
          <div class="form-group">
            <label>새 비밀번호</label>
            <input type="password" v-model="newPassword" class="input-field" />
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
        newPassword: ''
      };
    },
    methods: {
      async updatePassword() {
        const email = localStorage.getItem('userEmail');
        if (!email) {
          alert('로그인이 필요합니다.');
          this.$router.push('/login');
          return;
        }
  
        try {
          const response = await axios.post('http://localhost:8080/api/v1/auth/reset-password/confirm', {
            email: email,
            password: this.newPassword
          });
  
          if (response.status === 200) {
            alert('비밀번호가 변경되었습니다.');
            await this.logout();
          }
        } catch (error) {
          console.error('비밀번호 변경 중 오류 발생:', error);
          alert('비밀번호 변경에 실패했습니다.');
        }
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