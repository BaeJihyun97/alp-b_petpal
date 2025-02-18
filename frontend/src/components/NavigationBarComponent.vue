<template>
  <nav class="navigation-bar">
    <div class="links">
      <router-link to="/petsitter-search">펫시터</router-link>
      <a href="#">제품</a>
      <a href="#">반려동물</a>
    </div>
    <div class="auth-buttons">
      <button v-if="isLoggedIn" @click="$emit('logout')" class="nav-button">로그아웃</button>
      <button v-if="isLoggedIn" @click="goToMyPage" class="nav-button">MY</button>
      <button v-else @click="login" class="nav-button">로그인</button>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavigationBar',
  props: {
    isLoggedIn: Boolean // 로그인 상태를 부모로부터 받음
  },
  methods: {
    login() {
      this.$router.push('/login');
    },
    goToMyPage() {
      this.$router.push('/my-page'); // 마이 페이지로 이동
    }
  }
};
</script>

<style scoped>
.navigation-bar {
  width: 100%;
  /* max-width: 1200px; 부모 요소의 최대 너비에 맞춤 */
  margin: 0; /* 마진 제거 */
  display: flex;
  justify-content: space-between;
  padding: 10px 20px;
  box-sizing: border-box;
  background-color: #333;
  color: white;
}

.links a {
  margin-right: 15px;
  color: white;
  text-decoration: none;
}

.auth-buttons .nav-button {
  background-color: #ff6b6b;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 15px;
  cursor: pointer;
  margin-left: 10px;
  height: 30px; /* 높이 맞추기 */
}

.auth-buttons .nav-button:hover {
  background-color: #ff4c4c;
}
</style> 