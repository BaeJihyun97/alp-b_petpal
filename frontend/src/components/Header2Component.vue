<template>
  <header class="header">
    
    <div class="header-content">
    <h1 @click="goToHome" class="logo">PetPal</h1>
      <nav class="nav">
        <a href="#" class="nav-link">About Us</a>
        <a href="#" class="nav-link">Help</a>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
  methods: {
    goToHome() {
      this.$router.push("/"); // 'Home'은 메인 페이지의 라우트 이름입니다.
    }
  }
}
</script>

<style scoped>
.header {
  background-color: white;
  padding: 10px 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.logo {
  cursor: pointer; /* 클릭 가능하게 커서 변경 */
  font-size: 1.5em;
  color: #6a1b9a;
  margin-right: auto;
}

.nav {
  display: flex;
  gap: 10px;
}

.nav-link {
  color: #333;
  text-decoration: none;
  font-size: 1em;
}

.nav-link:hover {
  text-decoration: underline;
}
</style> 