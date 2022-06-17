import {httpMall} from '../HttpService';

export class NoticeServiceImpl {
    async sendNotice(title, content, fromUsername, toUsername, type) {
        return await httpMall.post(
            `/notice`, {
                id: 0,
                title,
                content,
                fromUsername,
                toUsername,
                type
            }
        )
    }
}
