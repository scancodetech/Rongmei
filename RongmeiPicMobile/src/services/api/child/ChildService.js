import {http} from "../HttpService";

export class ChildServiceImpl {
    async updateChild(parameters) {
        return await http.put('simple_child', parameters);
    }

    async getMineChild(page, limit) {
        return await http.get(`simple_child/mine?page=${page}&limit=${limit}`);
    }

    async getChildren(page, limit) {
        return await http.get(`simple_child?page=${page}&limit=${limit}`);
    }

    async getTopicChildern(topic, page, limit) {
        return await http.get(`simple_child/topic?topic=${topic}&page=${page}&limit=${limit}`);
    }

    async getTopTopics(page, limit) {
        return await http.get(
            `/simple_child/topic/top?page=${page}&limit=${limit}`
        )
    }

    async getTopicDetail(topic) {
        return await http.get(
            `/simple_child/topic/detail?topic=${topic}`
        )
    }

    async getUserChildren(username, page, limit) {
        return await http.get(
            `/simple_child/user?username=${username}&page=${page}&limit=${limit}`
        )
    }
}
