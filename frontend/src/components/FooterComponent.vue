<template>
  <footer class="footer">
    <p>PetPal</p>
    <p>All Rights Reserved</p>
    <p>경기 성남시 수정구 고등동 410-57</p>
  </footer>
</template>

<script>
export default {
  name: 'FooterComponent'
};
</script>

<style scoped>
.footer {
  background-color: #f0f0f0;
  color: black;
  text-align: center;
  padding: 1px 0; /* 작게 조정 */
  font-size: 0.5em; /* 글씨 크기 작게 */
  display: flex;
  justify-content: space-around;
}
</style> 