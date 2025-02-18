<template>
  <div class="petsitter-card">
    <div class="card-header">
      <img 
        src="@/assets/icons/PetPal_icon_1.png" 
        alt="PetPal Icon" 
        class="petsitter-icon"
      />
      <div class="service-info">
        <div class="petsitter-info">
          <span class="nickname">{{ petsitterInfo.nickname }}</span>
          <span class="phone">{{ petsitterInfo.phoneNumber }}</span>
        </div>
        <div class="location">{{ location }}</div>
        <div class="pet-info">
          <span class="pet-type">{{ petType }}</span>
          <span class="pet-size">({{ petSize }})</span>
        </div>
      </div>
    </div>
    <div class="card-body">
      <div class="introduction">{{ petsitterInfo.introduction }}</div>
      <div class="fee">시간당 {{ formatFee(fee) }}원</div>
      <PaymentTest
        v-if="showPayment"
        :merchant_uid="generateMerchantUid()"
        :amount="Number(fee)"
        :name="`${petsitterInfo.nickname} 펫시터 서비스`"
        :buyer_name="buyerName"
        :buyer_tel="buyerTel"
        :buyer_email="buyerEmail"
      />
      <button @click="showPayment = true" class="payment-button">
        예약하기
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import PaymentTest from './PaymentTest.vue';

export default {
  components: {
    PaymentTest
  },
  props: {
    petSitterId: {
      type: Number,
      required: true
    },
    serviceId: {
      type: Number,
      required: true
    },
    location: {
      type: String,
      required: true
    },
    petType: {
      type: String,
      required: true
    },
    petSize: {
      type: String,
      required: true
    },
    fee: {
      type: [Number, String],
      required: true
    }
  },
  data() {
    return {
      petsitterInfo: {
        nickname: '',
        phoneNumber: '',
        introduction: ''
      },
      showPayment: false,
      buyerName: '테스트 구매자', // 실제로는 로그인된 사용자 정보를 사용
      buyerTel: '01012341234',   // 실제로는 로그인된 사용자 정보를 사용
      buyerEmail: 'test@test.com' // 실제로는 로그인된 사용자 정보를 사용
    }
  },
  methods: {
    formatFee(fee) {
      return Number(fee).toLocaleString();
    },
    async fetchPetsitterInfo() {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/pet-sitters/${this.petSitterId}`);
        if (response.data && response.data.data) {
          this.petsitterInfo = response.data.data;
        }
      } catch (error) {
        console.error('펫시터 정보 로딩 실패:', error);
      }
    },
    generateMerchantUid() {
      return `mid_${this.petSitterId}_${new Date().getTime()}`;
    }
  },
  async created() {
    await this.fetchPetsitterInfo();
  }
};
</script>

<style scoped>
.petsitter-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px;
  transition: transform 0.2s;
  cursor: pointer;
}

.petsitter-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 16px;
}

.petsitter-icon {
  width: 60px;
  height: 60px;
  object-fit: contain;
}

.service-info {
  flex: 1;
}

.petsitter-info {
  margin-bottom: 8px;
}

.nickname {
  font-size: 1.2em;
  font-weight: bold;
  color: #6a1b9a;
  margin-right: 12px;
}

.phone {
  color: #666;
  font-size: 0.9em;
}

.location {
  font-size: 1.1em;
  font-weight: bold;
  color: #333;
  margin-bottom: 4px;
}

.pet-info {
  color: #666;
}

.pet-type {
  margin-right: 8px;
}

.pet-size {
  color: #888;
}

.card-body {
  border-top: 1px solid #eee;
  padding-top: 12px;
}

.introduction {
  color: #666;
  font-size: 0.9em;
  margin-bottom: 12px;
  line-height: 1.4;
  max-height: 2.8em;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.fee {
  font-size: 1.2em;
  font-weight: bold;
  color: #6a1b9a;
  text-align: right;
}

.payment-button {
  margin-top: 12px;
  background-color: #6a1b9a;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  width: 100%;
  font-weight: bold;
  transition: background-color 0.2s;
}

.payment-button:hover {
  background-color: #8e24aa;
}
</style> 