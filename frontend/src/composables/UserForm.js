import { ref } from 'vue';

export default function useUserForm() {
  const formData = ref({
    userId: '',
    password: '',
    email: '',
    name: '',
    phoneNumber: ''
  });

  const validateForm = () => {
    return Object.values(formData.value).every(value => value.trim() !== '');
  };

  const submitForm = () => {
    if (!validateForm()) {
      alert('All fields must be filled out!');
      return;
    }
    console.log('Submitted Data:', formData.value);
    alert('User data submitted!');
  };

  return { formData, submitForm };
}