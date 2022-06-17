import {httpMall} from "../HttpService";

export class ReportServiceImpl {
    async submitReport(reason, description, imageUrls, relationId) {
        return httpMall.post(`report`, {
            reason,
            description,
            imageUrls,
            relationId
        });
    }

    async getReport(page, limit) {
        return httpMall.get(`report?page=${page}&limit=${limit}`);
    }
}
