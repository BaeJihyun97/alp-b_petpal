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

const callback = async (response) => {
    const {
        success,
        merchant_uid,
        error_msg,
        imp_uid,
        paid_amount,
        pay_method,
        paid_at,
        status
    } = response;

    console.log(merchant_uid, response);

    if (success) {
        try {
            // 백엔드 API 호출하여 결제 정보 저장
            const result = await fetch('http://localhost:8080/api/payments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    impUid: imp_uid,
                    merchantUid: merchant_uid,
                    paidAmount: paid_amount,
                    payMethod: pay_method,
                    paidAt: paid_at,           // Unix timestamp 그대로 전송
                    status: status,
                    buyerName: props.buyer_name,
                    buyerEmail: props.buyer_email,
                    buyerTel: props.buyer_tel
                })
            });

            if (!result.ok) {
                throw new Error('결제 정보 저장 실패');
            }

            alert('결제가 성공적으로 완료되었습니다.');
        } catch (error) {
            console.error('결제 정보 저장 중 오류 발생:', error);
            alert('결제는 성공했으나 정보 저장에 실패했습니다.');
        }
    } else {
        alert(`결제 실패: ${error_msg}`);
    }
};
</script>