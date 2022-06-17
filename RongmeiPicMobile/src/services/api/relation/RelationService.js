import {http, httpCommon} from "../HttpService";

export class RelationServiceImpl {
    async getSale(id) {
        return http.get(`auction/sale/${id}`);
    }

    async postLike(relationId, type) {
        return http.post(`relation/like`, {
            relationId,
            type
        })
    }

    async getLike(relationId, type) {
        return http.get(`relation/like?relation_id=${relationId}&type=${type}`);
    }

    async getUserRelation(username) {
        return httpCommon.get(`user_relation/user?username=${username}`);
    }

    async getUserRelationLike(toUsername, type) {
        return httpCommon.get(`user_relation/like?toUsername=${toUsername}&type=${type}`);
    }

    async updateUserRelation(toUsername, type) {
        return httpCommon.post(`user_relation`, {
            fromUsername: "",
            toUsername,
            relationType: type
        });
    }
}
