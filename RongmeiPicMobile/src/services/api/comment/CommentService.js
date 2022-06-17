import {http} from "../HttpService";

export class CommentServiceImpl {
    async sendComment(toUsername, content, toCommentId, relationId) {
        return await http.post('/comment', {
            toUsername,
            content,
            toCommentId,
            relationId
        });
    }

    async getComments(relationId, toCommentId, page, limit) {
        return await http.get(`comment?relationId=${relationId}&toCommentId=${toCommentId}&page=${page}&limit=${limit}`);
    }

    async likeComments(commentId) {
        return await http.post(`comment/like?commentId=${commentId}`)
    }
}

