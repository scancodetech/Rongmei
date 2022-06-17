import {httpMetrics} from "../HttpService";

export class MetricsServiceImpl {
    async getShare(id) {
        return httpMetrics.get(`metrics/count?key=dimension_share_${id}`);
    }
    async getSingleShare(id) {
        return httpMetrics.get(`metrics/count/single?key=dimension_share_${id}`);
    }

    async addShare(id, msg) {
        return httpMetrics.post(`metrics`, {
            key: `dimension_share_${id}`,
            msg: msg
        });
    }
}