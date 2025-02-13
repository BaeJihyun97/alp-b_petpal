import {  createWebHistory, createRouter } from 'vue-router';
import MainPage from '../pages/MainPage.vue';
import LoginPage from '../pages/LoginPage.vue';
import RegisterPage from '@/pages/RegisterPage.vue';
import MyPage from '@/pages/MyPage.vue';
import UpdatePage from '@/pages/UpdatePage.vue';
import DeletePage from '@/pages/DeletePage.vue';
import UpdatePasswordPage from '@/pages/UpdatePasswordPage.vue';

const routes = [
  { path: '/', component: MainPage },
  { path: '/login', component: LoginPage },
  { path: '/register', component: RegisterPage },
  {
    path: '/my-page',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/update',
    name: 'Update',
    component: UpdatePage
  },
  {
    path: '/delete',
    name: 'Delete',
    component: DeletePage
  },
  {
    path: '/update-password',
    name: 'UpdatePasswordPage',
    component: UpdatePasswordPage
  }  
]

const router = createRouter({
  history: createWebHistory(),
  routes
});


export default router;
