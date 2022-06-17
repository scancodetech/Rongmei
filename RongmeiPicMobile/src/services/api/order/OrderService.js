import {http} from '../HttpService';

export class OrderServiceImpl {
    async getOrders(userGroupId) {
        return await http.get(
            `/order?userGroupId=${userGroupId}`
        )
    }

    async getMineOrders(status) {
        return await http.get(
            `/order/mine?status=${status}`
        )
    }

    async getOrder(orderId) {
        return await http.get(
            `/order/${orderId}`
        )
    }

    async updateOrder(orderData) {
        return await http.post(
            `/order`, orderData
        )
    }

    async isOrderExist(relationId) {
        return await http.get(
            `order/existence?status=已完成&orderType=pic&relationId=${relationId}`
        )
    }
}
