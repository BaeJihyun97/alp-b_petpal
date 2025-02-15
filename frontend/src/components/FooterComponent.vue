<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="footer-section">
        <h4>Working Hours</h4>
        <p>Monday - Sunday: 10 am - 9 pm</p>
        <p>All holidays & school vacations</p>
      </div>
      <div class="footer-section">
        <h4>Location</h4>
        <p>Happy Pet Care 151 Sutherland Rd, Brighton, MA</p>
      </div>
      <div class="footer-section">
        <h4>Contact</h4>
        <p>Got questions? Call us</p>
        <p><strong>617-608-3131</strong></p>
      </div>
      <div class="footer-section">
        <h4>Follow</h4>
        <!-- Add social media icons here -->
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'FooterComponent'
};
</script>

<style scoped>
.footer {
  background-color: #f8e8d4;
  padding: 20px 15px; /* 간격 조정 */
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.footer-content {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.footer-section {
  text-align: center;
}

h4 {
  margin-bottom: 10px;
  color: #6a1b9a;
}

p {
  margin: 5px 0;
  color: #333;
}
</style> 