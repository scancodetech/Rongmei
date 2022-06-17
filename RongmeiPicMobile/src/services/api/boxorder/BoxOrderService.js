import {http} from "../HttpService";

export class BoxOrderServiceImpl {
    async submitBoxOrderRequest(boxOrderData) {
        return await http.put(
            '/box_order/request', boxOrderData
        )
    }

    async queryMineBoxOrderRequest(status, page, limit) {
        return await http.get(
            `/box_order/request/query/mine?status=${status}&page=${page}&limit=${limit}`
        )
    }

    async queryBoxOrder(status, page, limit) {
        return await http.get(
            `/box_order/query?status=${status}&page=${page}&limit=${limit}`
        )
    }

    async grabBoxOrder(orderId) {
        return await http.get(
            `/box_order/grab/${orderId}`
        )
    }

    async submitBoxOrderResultCover(id, coverUrl) {
        return await http.post(
            '/box_order/result_cover', {
                id, coverUrl
            }
        )
    }

    async submitBoxOrderResult(id, resultUrl) {
        return await http.post(
            '/box_order/result', {
                id, resultUrl
            }
        )
    }

    async commentBoxOrder(id, commentStatus, comment) {
        return await http.post(
            '/box_order/comment', {
                id, commentStatus, comment
            }
        )
    }

    async shareBoxOrder(orderId) {
        return await http.post(
            `/box_order/share/${orderId}`
        )
    }

    async getSharingBoxOrder() {
        return await http.get(
            `/box_order/sharing`
        )
    }

    async getBoxOrder(boxOrderId) {
        return await http.get(
            `/box_order/${boxOrderId}`
        )
    }
}
