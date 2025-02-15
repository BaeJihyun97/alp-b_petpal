<template>
  <div class="service-container">
    <div class="service-fields">
      <div class="field-group">
        <label for="petType">Pet Type *</label>
        <select>
          <option value="" disabled>Select Pet Type</option>
          <option value="Dog">Dog</option>
          <option value="Cat">Cat</option>
          <option value="Bird">Bird</option>
          <option value="Other">Other</option>
        </select>
      </div>
      <div class="field-group">
        <label for="fee">Fee *</label>
        <input v-model="fee" type="text" id="fee" class="input-field" required />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      petType: '',
      fee: ''
    };
  }
};
</script>

<style scoped>
.service-container {
  margin-top: 20px;
}

input, select {
  width: 80%;
  margin: 10px;
  padding: 10px; /* 패딩 조정 */
  border: 1px solid #ccc;
  border-radius: 5px;
}

.service-fields {
  display: flex;
  justify-content: space-between;
}

.field-group {
  flex: 1;
  margin-right: 10px;
}

.field-group:last-child {
  margin-right: 0;
}
</style>
