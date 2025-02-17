<template>
    <div class="main-container">
      <HeaderComponent class="header" />
      <NavigationBarComponent class="navigation-bar" :isLoggedIn="isUserLoggedIn" @logout="logout" />
      <div class="body">
        
        <div class="content-body">
          <div class="card-section">
            <h2 class="card-title">인기 펫시터</h2>
            <CardListComponent :items="sitterItems" />
          </div>
          <div class="card-section">
            <h2 class="card-title">인기 상품</h2>
            <CardListComponent :items="productItems" />
          </div>
          <div v-if="isUserLoggedIn && petItems.length > 0" class="card-section">
            <h2 class="card-title">나의 반려 동물</h2>
            <CardListComponent :items="petItems" />
          </div>
        </div>
      </div>
      <FooterComponent class="footer" />
    </div>
</template>

<script>
import axios from 'axios';
import NavigationBarComponent from '@/components/NavigationBarComponent.vue';
import HeaderComponent from '@/components/HeaderComponent.vue';
import CardListComponent from '@/components/CardListComponent.vue';
import FooterComponent from '@/components/FooterComponent.vue';

export default {
  components: {
    NavigationBarComponent,
    HeaderComponent,
    CardListComponent,
    FooterComponent
  },
  data() {
    return {
      sitterItems: [],
      productItems: [],
      petItems: [],
      isUserLoggedIn: !!localStorage.getItem('userId')
    };
  },
  created() {
    this.fetchItems();
  },
  methods: {
    async logout() {
      try {
        const response = await axios.post('http://localhost:8080/api/v1/auth/logout');
        if (response.status === 200) {
          localStorage.removeItem('userId');
          localStorage.removeItem('userName');
          localStorage.removeItem('userEmail');
          alert('로그아웃 되었습니다.');
          this.isUserLoggedIn = false;
          this.petItems = [];
          this.$router.push('/');
        }
      } catch (error) {
        console.error('로그아웃 중 오류 발생:', error);
        alert('로그아웃에 실패했습니다.');
      }
    },
    fetchItems() {
      // 예시 데이터
      this.sitterItems = Array.from({ length: 10 }, (_, i) => ({
        id: i,
        name: `펫시터 ${i + 1}`,
        description: `펫시터 설명 ${i + 1}`,
        rating: Math.floor(Math.random() * 5) + 1,
        reviewCount: Math.floor(Math.random() * 100),
        type: 'sitter'
      }));

      this.productItems = Array.from({ length: 10 }, (_, i) => ({
        id: i + 10,
        name: `제품 ${i + 1}`,
        description: `제품 설명 ${i + 1}`,
        rating: Math.floor(Math.random() * 5) + 1,
        reviewCount: Math.floor(Math.random() * 100),
        type: 'product'
      }));

      this.petItems = Array.from({ length: 10 }, (_, i) => ({
        id: i + 20,
        name: `반려동물 ${i + 1}`,
        description: `반려동물 설명 ${i + 1}`,
        type: 'pet'
      }));
    }
  }
}
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.header {
  flex: 0 0 auto;
}

.body {
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
}

.navigation-bar {
  flex: 0 0 auto; /* 네비게이션 바의 높이를 설정 */
  width: 100%; /* 네비게이션 바가 전체 너비를 차지하도록 설정 */
}

.content-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  max-width: 1200px; /* 컨텐츠 바디의 최대 너비를 설정 */
  margin: 0 auto; /* 가운데 정렬 */
  width: 100%; /* 화면에 맞게 너비를 설정 */
}

.card-section {
  margin-bottom: 30px; /* 카드 리스트와 제목 사이의 간격 */
}

.card-title {
  margin-bottom: 10px; /* 제목과 카드 리스트 사이의 간격 */
  font-size: 1.5em;
  font-weight: bold;
}

.footer {
  flex: 0 0 auto;
  width: 100%;
  margin-top: auto; /* 컨텐츠와 footer 사이의 남은 공간을 자동으로 채움 */
}
</style> 