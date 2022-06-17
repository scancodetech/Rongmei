import request from "@/utils/request";
import {API_SERVICE} from "@/services/config";

export async function getSaleLikeUserStatistics(startTime: number, endTime: number) {
    return getLikeUserStatistics(0, startTime, endTime);
}

export async function getCommodityLikeUserStatistics(startTime: number, endTime: number) {
    return getLikeUserStatistics(1, startTime, endTime);
}

export async function getBlindBoxLikeUserStatistics(startTime: number, endTime: number) {
    return getLikeUserStatistics(2, startTime, endTime);
}

function getLikeUserStatistics(type: number, startTime: number, endTime: number) {
    return request(API_SERVICE, `relation/like/statistics/mine?type=${type}&startTime=${startTime}&endTime=${endTime}`);
}