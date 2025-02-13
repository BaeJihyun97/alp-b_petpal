<template>
  <header>
    <div class="top-bar">
      <nav>
        <a href="#">ABOUT US</a>
      </nav>
    </div>
    <div class="header-content">
      <h1>PetPal</h1>
      <p>소중한 반려동물을 위한 맞춤 관리 서비스, 함께하는 모든 순간을 더욱 특별하게</p>
    </div>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent'
};
</script>

<style scoped>
header {
  background-color: #b0b0b0;
  margin: 0;
}

.top-bar {
  background-color: #ffffff;
  padding: 3px 10px;
  text-align: right;
}

nav a {
  text-decoration: none;
  color: black;
  margin-right: 10px;
  font-size: 14px;
}

.header-content {
  padding: 20px 10px;
}

h1 {
  margin: 0;
}

p {
  margin: 5px 0 0 0;
}
</style> 