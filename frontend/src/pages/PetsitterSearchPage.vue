<template>
    <div class="page-container">
      <Header2Component />
      <Header3Component 
        title="Search for Pet Sitters"
        subtitle="Find the perfect pet sitter for your pet"
      />
      <div class="search-container">
        <input type="text" placeholder="Search for pet sitters..." class="search-input" />
      </div>
      <!-- <PetsitterCardList /> -->
      <PetsitterSearchPageComponent />
      <FooterComponent />
    </div>
  </template>
  
  <script>
  import Header2Component from '@/components/Header2Component.vue';
  import FooterComponent from '@/components/FooterComponent.vue';
  import Header3Component from '@/components/Header3Component.vue';
  // import PetsitterCardList from '@/components/PetsitterCardList.vue';
  import PetsitterSearchPageComponent from '@/components/PetsitterSearchPageComponent.vue';

  export default {
    components: {
      Header2Component,
      FooterComponent,
      Header3Component,
      // PetsitterCardList,
      PetsitterSearchPageComponent
    }
  }
  </script>
  
  <style scoped>
  .page-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .search-container {
    margin: 20px; /* 헤더 아래 여백 추가 */
    display: flex;
    justify-content: flex-end; /* 우측 정렬 */
  }

  .search-input {
    width: 30%; /* 검색창 너비를 30%로 조정 */
    padding: 10px; /* 패딩 추가 */
    border: 1px solid #e0e0e0; /* 테두리 스타일 */
    border-radius: 8px; /* 모서리 둥글게 */
    font-size: 16px; /* 글자 크기 조정 */
  }
  </style> 