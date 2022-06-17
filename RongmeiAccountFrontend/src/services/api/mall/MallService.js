import {httpMall} from '../HttpService';

export class MallServiceImpl {
    async getAllCommodities(limit, offset) {
        return await httpMall.post(
            `/commodity`, {
                key: "",
                limit: limit,
                offset: offset,
                orderKey: "all",
                tags: []
            }
        )
    }

    async getCommodityAuthor(commodityId) {
        return await httpMall.get(
            `/commodity/author/${commodityId}`
        )
    }

    async getCommodity(commodityId) {
        return await httpMall.get(
            `/commodity/${commodityId}`
        )
    }

    async deleteCommodity(commodityId) {
        return await httpMall.delete(
            `/commodity?commodityId=${commodityId}`
        )
    }

    async getSale(saleId) {
        return await httpMall.get(
            `/auction/sale/${saleId}`
        )
    }
}
