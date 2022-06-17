import {httpMall} from '../HttpService';

export class TopicServiceImpl {
    async getTopTopics(page, limit) {
        return await httpMall.get(
            `/simple_child/topic/top?page=${page}&limit=${limit}`
        )
    }

    async updateTopic(topicData) {
        return await httpMall.post(
            `/simple_child/topic`, topicData
        )
    }

    async getTopicDetail(topic) {
        return await httpMall.get(
            `/simple_child/topic/detail?topic=${topic}`
        )
    }
}
