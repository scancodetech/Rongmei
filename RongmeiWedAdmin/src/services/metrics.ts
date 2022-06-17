import request from '@/utils/request';
import {METRICS_SERVICE} from "@/services/config";

export async function getViewBatch(ids: number[], startTime: number, endTime: number) {
    return request(METRICS_SERVICE, `metrics/count/batch`, {
        method: 'POST',
        data: {
            keys: packKeys("dimension_view", ids),
            startTime,
            endTime
        }
    });
}

export async function getShareBatch(ids: number[], startTime: number, endTime: number) {
    return request(METRICS_SERVICE, `metrics/count/batch`, {
        method: 'POST',
        data: {
            keys: packKeys("dimension_share", ids),
            startTime,
            endTime
        }
    });
}

export async function getDislikeBatch(ids: number[], startTime: number, endTime: number) {
    return request(METRICS_SERVICE, `metrics/count/batch`, {
        method: 'POST',
        data: {
            keys: packKeys("dimension_dislike", ids),
            startTime,
            endTime
        }
    });
}

export async function getDownloadBatch(ids: number[], startTime: number, endTime: number) {
    return request(METRICS_SERVICE, `metrics/count/batch`, {
        method: 'POST',
        data: {
            keys: packKeys("dimension_download", ids),
            startTime,
            endTime
        }
    });
}

function packKeys(prefix: string, ids: number[]): string[] {
    const keys: string[] = [];
    ids.forEach((value => {
        keys.push(`${prefix}_${value}`)
    }))
    return keys;
}