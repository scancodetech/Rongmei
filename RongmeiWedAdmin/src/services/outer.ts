import {OUTER_API_SERVICE} from '@/services/config';
import request from '@/utils/request';

// export async function postToCunanchain(fileHash: string, fileTitle: string) {
//     return request(OUTER_API_SERVICE, `outer_api/cunanchain`, {
//         method: 'POST',
//         data: {
//             fileHash,
//             fileTitle
//         }
//     });
// }
//
// export async function getFromCunanchain(fileHash: string, chainTxId: string) {
//     return request(OUTER_API_SERVICE, `outer_api/cunanchain?fileHash=${fileHash}&chainTxId=${chainTxId}`);
// }

export async function postToStorage(fileHash: string, fileTitle: string, username: string, address: string, source: string, picUrl: string) {
    return request(OUTER_API_SERVICE, `outer_api/storage`, {
        method: 'POST',
        data: {
            fileHash,
            fileTitle,
            username,
            address,
            source,
            picUrl
        }
    });
}

export async function getFromStorage(fileHash: string, chainTxId: string) {
    return request(OUTER_API_SERVICE, `outer_api/storage?fileHash=${fileHash}&chainTxId=${chainTxId}`);
}
