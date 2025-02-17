import {  createWebHistory, createRouter } from 'vue-router';
import MainPage from '../pages/MainPage.vue';
import LoginPage from '../pages/LoginPage.vue';
import RegisterPage from '@/pages/RegisterPage.vue';
import MyPage from '@/pages/MyPage.vue';
import UpdatePage from '@/pages/UpdatePage.vue';
import DeletePage from '@/pages/DeletePage.vue';
import UpdatePasswordPage from '@/pages/UpdatePasswordPage.vue';
import RegisterPetSitterPage from '@/pages/RegisterPetSitterPage.vue';
import PetsitterMyPage from '@/pages/PetsitterMyPage.vue';
import DeletPetsiiterPage from '@/pages/DeletPetsiiterPage.vue';
import PetsitterSearchPage from '@/pages/PetsitterSearchPage.vue';
import AdminPage from '@/pages/AdminPage.vue';

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
  },
  {
    path: '/register-petsitter',
    name: 'RegisterPetSitter',
    component: RegisterPetSitterPage
  },
  {
    path: '/petsitter-my-page',
    name: 'PetsitterMyPage',
    component: PetsitterMyPage
  },
  {
    path: '/delete-petsitter',
    name: 'DeletePetsitter',
    component: DeletPetsiiterPage
  },
  {
    path: '/petsitter-search',
    name: 'PetsitterSearch',
    component: PetsitterSearchPage
  },
  {
    path: '/admin',
    name: 'Admin',
    component: AdminPage
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
});


export default router;
