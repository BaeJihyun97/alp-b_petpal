<template>
    <div>
        <button @click="onPayment">
            결제하기
        </button>
    </div>
</template>

<script setup>
import { defineProps } from 'vue';

// props 정의
const props = defineProps({
    merchant_uid: {
        type: String,
        required: true
    },
    amount: {
        type: Number,
        required: true
    },
    name: {
        type: String,
        required: true
    },
    buyer_name: {
        type: String,
        required: true
    },
    buyer_tel: {
        type: String,
        required: true
    },
    buyer_email: {
        type: String,
        required: true
    }
});

const onPayment = () => {
    /* 1. 가맹점 식별하기 */
    const { IMP } = window;
    IMP.init('imp60016454'); //고객사 식별코드 입력시 실제 결제가됨
    
    /* 2. 결제 데이터 정의하기 */
    const data = {
        pg: 'uplus', // 채널로 등록된 PG사만 사용 가능하다. PG사마다 결제 화면이 달라진다
        pay_method: 'card', // 결제수단
        merchant_uid: props.merchant_uid,
        amount: props.amount,
        name: props.name,
        buyer_name: props.buyer_name,
        buyer_tel: props.buyer_tel,
        buyer_email: props.buyer_email,
        buyer_addr: '신사동 661-16', // 기본값으로 유지
        buyer_postcode: '06018' // 기본값으로 유지
    };

    /* 4. 결제 창 호출하기 */
    IMP.request_pay(data, callback);
};

const callback = (response) => {
    /* 3. 콜백 함수 정의하기 */
    const {
        success,
        merchant_uid,
        error_msg
    } = response;

    console.log(merchant_uid, response);

    if (success) {
        alert('결제 성공');
    } else {
        alert(`결제 실패: ${error_msg}`);
    }
};
</script>