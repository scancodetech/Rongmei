import request from '@/utils/request';
import {COMMON_URL} from "@/services/config";
import {isPC} from "@/utils/utils";

export async function postAlipay(totalAmount: number, returnUrl: string) {
    return request(`${COMMON_URL}/pay/alipay`, {
        method: 'POST',
        data: {
            payType: isPC() ? 1 : 0,
            returnUrl: returnUrl,
            totalAmount: totalAmount
        },
    });
}

export async function postWechat(totalAmount: number, returnUrl: string, ip: string) {
    return request(`${COMMON_URL}/pay/wechat`, {
        method: 'POST',
        data: {
            payType: isPC() ? 1 : 0,
            totalAmount: totalAmount,
            ip
        },
    });
}

export async function alipayCallback(totalAmount: number) {
    return request(`${COMMON_URL}/pay/alipay/notify`, {
        method: 'POST',
        data: {
            payType: 1,
            totalAmount: totalAmount
        },
    });
}

// POST https://api.dimension.pub/rongmei_account_api_prod/pay/withdraw
// {
//   username:string;
//   coins:number(真实金额，即IMM/100)
// }

// 完成提现申请
// POST https://api.dimension.pub/rongmei_account_api_prod/pay/withdraw/done/${requestId}

// 获取提现列表
// GET https://api.dimension.pub/rongmei_account_api_prod/pay/withdraw

export async function postWithdraw(username: String, coins: number) {
    return request(`${COMMON_URL}/pay/withdraw
  `, {
        method: 'POST',
        data: {
            username: username,
            coins: coins,
        },
    });
}
