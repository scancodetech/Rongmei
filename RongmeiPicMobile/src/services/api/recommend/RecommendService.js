import {http} from "../HttpService";

export class RecommendServiceImpl {
    async getRecommend(page, limit, startFromId, startFromType) {
        return await http.get(`recommend?page=${page}&limit=${limit}&startFromId=${startFromId}&startFromType=${startFromType}`);
    }
}
