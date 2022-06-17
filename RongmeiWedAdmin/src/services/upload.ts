import request from '@/utils/request';
import {COMMON_SERVICE} from "@/services/config";
import {ProgressInfo} from "cos-js-sdk-v5";

export async function upload(file: Blob) {
    const formData = new FormData();
    formData.append("file", file)
    return request(COMMON_SERVICE, `upload`, {
        method: 'POST',
        data: formData
    });
}

const COS = require('cos-js-sdk-v5');

export async function uploadFileToCos(key: string, fileObject: File) {
    const cos = new COS({
        SecretId: 'AKIDSkwD1ZGnW1GK8lj1OAh42nl7SYu1arXr',
        SecretKey: 'sYM7pRtBSa2iovaCfDYq2iWcltwnT0OX',
    });
    return await cos.putObject({
        Bucket: 'rongmei-1255617399', /* 必须 */
        Region: 'ap-nanjing',     /* 存储桶所在地域，必须字段 */
        Key: key,              /* 必须 */
        Body: fileObject, // 上传文件对象
        StorageClass: 'STANDARD',
        onProgress: function (progressData: ProgressInfo) {
            console.log(JSON.stringify(progressData));
        }
    });
}
