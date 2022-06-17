import {http} from "../HttpService";

export class ReportServiceImpl {
    async submitReport(reason, description, imageUrls, relationId) {
        return http.post(`report`, {
            reason,
            description,
            imageUrls,
            relationId
        });
    }

    async getReport(page, limit) {
        return http.get(`report?page=${page}&limit=${limit}`);
    }
}
