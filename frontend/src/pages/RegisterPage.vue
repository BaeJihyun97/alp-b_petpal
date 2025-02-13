<template>
  <div class="background">
    <div class="register-container">
      <h2>PetPal 회원가입</h2>
      <form @submit.prevent="validateForm">
        <div class="form-group">
          <label>이메일</label>
          <input type="email" v-model="email" @input="checkEmailFormat" class="input-field" />
          <button type="button" class="verify-button" @click="checkEmail">확인</button>
        </div>
        <div :class="{'error': emailError, 'success': !emailError && email}" class="message">{{ emailMessage }}</div>

        <div class="form-group">
          <label>비밀번호</label>
          <input type="password" v-model="password" @input="checkPassword" class="input-field" />
        </div>
        <div :class="{'error': passwordError, 'success': !passwordError && password}" class="message">{{ passwordMessage }}</div>

        <div class="form-group">
          <label>비밀번호 확인</label>
          <input type="password" v-model="confirmPassword" @input="checkConfirmPassword" class="input-field" />
        </div>
        <div :class="{'error': confirmPasswordError, 'success': !confirmPasswordError && confirmPassword}" class="message">{{ confirmPasswordMessage }}</div>

        <div class="form-group">
          <label>이름</label>
          <input type="text" v-model="name" @input="checkName" class="input-field" />
        </div>
        <div :class="{'error': nameError, 'success': !nameError && name}" class="message">{{ nameMessage }}</div>

        <div class="form-group">
          <label>전화번호</label>
          <input type="tel" v-model="phone" @input="checkPhone" class="input-field" />
        </div>
        <div :class="{'error': phoneError, 'success': !phoneError && phone}" class="message">{{ phoneMessage }}</div>

        <button type="submit" class="submit-button">가입하기</button>
      </form>
      <p>계정이 있으신가요? <a @click.prevent="goToLogin">로그인하기</a></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      confirmPassword: '',
      name: '',
      phone: '',
      emailError: true,
      passwordError: true,
      confirmPasswordError: true,
      nameError: true,
      phoneError: true,
      emailMessage: '',
      passwordMessage: '',
      confirmPasswordMessage: '',
      nameMessage: '',
      phoneMessage: '',
      emailChecked: false,
      emailAvailable: false
    };
  },
  methods: {
    goToLogin() {
      this.$router.push('/login');
    },
    checkEmailFormat() {
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailPattern.test(this.email)) {
        this.emailError = true;
        this.emailMessage = '유효한 이메일 형식을 입력하세요.';
      } else {
        this.emailError = false;
        this.emailMessage = '';
      }
    },
    async checkEmail() {
      if (this.emailError) {
        alert('이메일 형식이 올바르지 않습니다.');
        return;
      }

      try {
        const response = await axios.get(`http://localhost:8080/api/v1/users/email/${this.email}`);
        if (response.data.data) {
          this.emailAvailable = true;
          this.emailMessage = '가능한 이메일입니다.';
          this.emailError = false;
        } else {
          this.emailAvailable = false;
          this.emailMessage = '이미 가입된 이메일입니다.';
          this.emailError = true;
        }
        this.emailChecked = true;
      } catch (error) {
        console.error('이메일 중복 체크 중 오류 발생:', error);
        this.emailMessage = '이메일 중복 체크에 실패했습니다.';
        this.emailError = true;
      }
    },
    async validateForm() {
      this.checkPassword();
      this.checkConfirmPassword();
      this.checkName();
      this.checkPhone();

      if (!this.emailChecked) {
        alert('이메일 중복 체크를 해주세요.');
        return;
      }

      if (!this.emailError && !this.passwordError && !this.confirmPasswordError && !this.nameError && !this.phoneError && this.emailAvailable) {
        try {
          const response = await axios.post('http://localhost:8080/api/v1/users', {
            email: this.email,
            password: this.password,
            name: this.name,
            phoneNumber: this.phone
          });

          if (response.status === 200) {
            alert('회원가입 성공!');
            this.$router.push('/');
          }
        } catch (error) {
          console.error('회원가입 중 오류 발생:', error);
          alert('회원가입에 실패했습니다.');
        }
      }
    },
    checkPassword() {
      const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
      this.passwordError = !passwordPattern.test(this.password);
      this.passwordMessage = this.passwordError ? '비밀번호는 최소 8자, 문자와 숫자를 포함해야 합니다.' : '유효한 비밀번호입니다.';
    },
    checkConfirmPassword() {
      this.confirmPasswordError = this.password !== this.confirmPassword;
      this.confirmPasswordMessage = this.confirmPasswordError ? '비밀번호가 일치하지 않습니다.' : '비밀번호가 일치합니다.';
    },
    checkName() {
      this.nameError = this.name.length > 32;
      this.nameMessage = this.nameError ? '이름은 최대 32자까지 입력 가능합니다.' : '유효한 이름입니다.';
    },
    checkPhone() {
      const phonePattern = /^010\d{8}$/;
      this.phoneError = !phonePattern.test(this.phone);
      this.phoneMessage = this.phoneError ? '전화번호는 01012341234 형식으로 입력하세요.' : '유효한 전화번호입니다.';
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

.register-container {
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

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
  width: 100%;
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

.verify-button {
  margin-left: 10px;
  padding: 8px 12px;
  background-color: #ff6b6b;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.verify-button:hover {
  background-color: #ff4c4c;
}

.submit-button {
  width: 100%;
  padding: 10px;
  background-color: #ff6b6b;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 20px;
}

.submit-button:hover {
  background-color: #ff4c4c;
}

p {
  margin-top: 20px;
  color: #777;
}

a {
  color: #007bff;
  text-decoration: none;
  cursor: pointer;
}

a:hover {
  text-decoration: underline;
}

.message {
  font-size: 0.8em;
  margin-bottom: 10px;
  text-align: left;
  width: 100%;
}

.error {
  color: red;
}

.success {
  color: blue;
}
</style> 