<template>
  <div class="page-container">
    <Header2Component />
    <Header3Component 
      title="Are you ready to register as Pet Sitter?"
      subtitle="Fill the form below"
    />
    <RegisterPetsitterContainerComponent />
    <FooterComponent />
  </div>
</template>

<script>
import Header2Component from '@/components/Header2Component.vue';
import FooterComponent from '@/components/FooterComponent.vue';
import RegisterPetsitterContainerComponent from '@/components/RegisterPetsitterContainerComponent.vue';
import Header3Component from '@/components/Header3Component.vue';

export default {
  components: {
    Header2Component,
    FooterComponent,
    RegisterPetsitterContainerComponent,
    Header3Component
  }
}
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
</style> 