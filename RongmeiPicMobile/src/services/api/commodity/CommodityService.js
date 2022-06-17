import {http} from "../HttpService";

export class CommodityServiceImpl {
    async getCommodities(parameters) {
        return await http.post('/commodity', parameters);
    }

    async getCommodity(commodityId) {
        return await http.get(`commodity/${commodityId}`);
    }

    async getCommodityTopic(key, offset, limit) {
        return await http.get(`commodity/topic?key=${key}&offset=${offset}&limit=${limit}`)
    }

    async getFavoritesCommodities(favoritesId, status) {
        return await http.get(`commodity/favorites?favorites_id=${favoritesId}&status=${status}`)
    }

    async updateCommodity(commodityData) {
        return await http.put('/commodity', commodityData);
    }
}

