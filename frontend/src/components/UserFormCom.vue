<template>
    <div>
      <input v-model="formData.userId" placeholder="User ID" />
      <input v-model="formData.password" placeholder="Password" type="password" />
      <input v-model="formData.email" placeholder="Email" type="email" />
      <input v-model="formData.name" placeholder="Name" />
      <input v-model="formData.phoneNumber" placeholder="Phone Number" />
      <button @click="submitForm">Submit</button>
    </div>
  </template>
  
  <script>
  import UserForm from '@/composables/UserForm';
  
  export default {
    setup() {
      return UserForm();
    }
  };
  </script>