import {httpCommon} from '../HttpService';

export class PayServiceImpl {
    async getWithdrawList() {
        return await httpCommon.get(
            '/pay/withdraw'
        )
    }

    async getRechargeList() {
        return await httpCommon.get(
            '/pay/recharge'
        )
    }

    async doneWithdraw(requestId) {
        return await httpCommon.get(
            `/pay/withdraw/done/${requestId}`
        )
    }
}
