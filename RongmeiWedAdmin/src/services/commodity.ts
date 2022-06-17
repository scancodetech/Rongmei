import request from '@/utils/request';
import {API_SERVICE, METRICS_SERVICE} from "@/services/config";

export interface CommodityQueryParameters {
    tags: string[];
    key: string;
    orderKey: string;
    offset: number;
    limit: number;
}

export interface CommodityItem {
    id: number;
    title: string;
    coverUrl: string;
    tags: string[];
    createTime: number;
    updateTime: number;
}

export interface Commodity {
    id: number;
    title: string;
    largePrice: number;
    coverUrl: string;
    tags: string[]
    contentUrl: string;
    description: string;
    signingInfo: string;
    extra: string;
    creatorUserGroupId: number;
}

export async function getCommodities(parameters: CommodityQueryParameters) {
    return request(API_SERVICE, `commodity`, {
        method: 'POST',
        data: parameters
    });
}

export async function getCommodity(commodityId: number) {
    return request(API_SERVICE, `commodity/${commodityId}`);
}

export async function updateCommodity(commodityData: any) {
    return request(API_SERVICE, `commodity`, {
        method: 'PUT',
        data: commodityData
    });
}

export async function deleteCommodity(commodityId: number) {
    return request(API_SERVICE, `commodity?commodityId=${commodityId}`, {
        method: 'DELETE',
    });
}

export async function getAuthorCommodities(status: number) {
    return request(API_SERVICE, `commodity/author?status=${status}`);
}

export async function getUserGroupCommodities(userGroupId: number) {
    return request(API_SERVICE, `commodity/group?userGroupId=${userGroupId}`);
}

export async function addCommodityView(id: number, msg: any) {
    return request(METRICS_SERVICE, `metrics`, {
        method: 'POST',
        data: {
            key: 'dimension_commodity_view_' + id,
            msg: msg
        }
    })
}

export async function getCommodityView(id: number) {
    return request(METRICS_SERVICE, `metrics/count?key=dimension_commodity_view_${id}`);
}

export async function addCommodityDownload(id: number, msg: any) {
    return request(METRICS_SERVICE, `metrics`, {
        method: 'POST',
        data: {
            key: 'dimension_commodity_download_' + id,
            msg: msg
        }
    })
}

export async function getCommodityDownload(id: number) {
    return request(METRICS_SERVICE, `metrics/count?key=dimension_commodity_download_${id}`);
}

export async function getCommoditySaleStatistics(startTime: number, endTime: number) {
    return request(API_SERVICE, `commodity/statistics/mine?startTime=${startTime}&endTime=${endTime}`);
}
