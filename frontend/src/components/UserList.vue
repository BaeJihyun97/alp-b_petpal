<template>
    <div id="app">
      <h1>{{ msg }}</h1>
      <h2>User List</h2>
      <button @click="fetchUsers">Load Users</button>
      <ul>
        <li v-for="user in users" :key="user.id">
          {{ user.name }} - {{ user.email }}
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "UserList",
    data() {
      return {
        users: [], // API에서 가져온 데이터를 저장할 배열
      };
    },
    methods: {
      async fetchUsers() {
        try {
          const response = await axios.get("http://localhost:8080/users");
          this.users = response.data; // 가져온 데이터를 users 배열에 저장
        } catch (error) {
          console.error("Error fetching users:", error);
          alert("Failed to fetch users. Check console for details.");
        }
      },
    },
    props: {
        msg: String
    },
  };
  </script>
  
  <style>
  #app {
    font-family: Arial, sans-serif;
    text-align: center;
    margin-top: 50px;
  }
  button {
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
  }
  ul {
    list-style: none;
    padding: 0;
  }
  li {
    margin: 5px 0;
  }
  </style>
  