import {httpOuter} from '../HttpService';

export class OuterServiceImpl {
    async postToStorage(params) {
        return await httpOuter.post(
            `/outer_api/storage`, params
        )
    }

    async queryFromStorage(key) {
        return await httpOuter.get(
            `/outer_api/storage/query?key=${key}`
        )
    }
}
