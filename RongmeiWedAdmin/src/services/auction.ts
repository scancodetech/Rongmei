import request from '@/utils/request';
import {API_SERVICE, METRICS_SERVICE} from "@/services/config";

export interface SaleQueryParameters {
    key: string;
    offset: number;
    limit: number;
    outdates: boolean;
    ownedByAuthor: boolean;
    // 0: 最近活跃, 1: 价格（低到高）, 2: 价格（高到低）, 3: 最近发布
    rankType: number;
    tags: string[];
}


export interface QueryBasicParams {
    limit: number;
    offset: number;
}

export interface SaleItem {
    id: number;
    startPrice: number;
    status: string;
    intervalPrice: number;
    thing: Thing;
    createTime: number;
    startTime: number;
    endTime: number;
}

export interface Thing {
    id: number;
    name: string;
    url: string;
    price: number;
    description: string;
    createTime: number;
    author: string;
    owner: string;
    tokenId: string;
}

export async function updateToken(tokenData: any) {
    return request(API_SERVICE, `auction/token`, {
        method: 'POST',
        data: tokenData
    });
}

export async function getToken(tokenId: number) {
    return request(API_SERVICE, `auction/token/${tokenId}`);
}

export async function updateThing(thingData: any) {
    return request(API_SERVICE, `auction/thing`, {
        method: 'POST',
        data: thingData
    });
}

export async function getThing(thingId: number) {
    return request(API_SERVICE, `auction/thing/${thingId}`);
}

export async function getThings() {
    return request(API_SERVICE, `auction/thing`);
}

export async function deleteThing(thingId: number) {
    return request(API_SERVICE, `auction/thing/${thingId}`, {
        method: 'DELETE'
    });
}

export async function deleteSale(saleId: number) {
    return request(API_SERVICE, `auction/sale/${saleId}`, {
        method: 'DELETE'
    });
}

export async function getSale(id: number) {
    return request(API_SERVICE, `auction/sale/${id}`);
}

export async function getSales(parameters: SaleQueryParameters) {
    return request(API_SERVICE, `auction/sale/query`, {
        method: 'POST',
        data: parameters
    });
}

export async function updateSale(saleData: any) {
    return request(API_SERVICE, `auction/sale`, {
        method: 'POST',
        data: saleData
    });
}

export async function getMineSales(draftStatus: number) {
    return request(API_SERVICE, `auction/sale/mine?draftStatus=${draftStatus}`);
}

export async function bid(price: number, saleId: number) {
    return request(API_SERVICE, `auction/bid`, {
        method: 'POST',
        data: {
            price,
            saleId
        }
    });
}

export async function getSaleHistory(id: number) {
    return request(API_SERVICE, `auction/history/${id}`);
}

export async function getAuthorArtworks(authorName: string) {
    return request(API_SERVICE, `auction/thing/author?author=${authorName}`);
}

export async function getAuctionTransactionHistory() {
    return request(API_SERVICE, `auction/transaction/history/mine`);
}

export async function getTopCollector(params: QueryBasicParams) {
    return request(API_SERVICE, `auction/collector?limit=${params.limit}&offset=${params.offset}`)
}

export async function getTopArtist(params: QueryBasicParams) {
    return request(API_SERVICE, `auction/artist?limit=${params.limit}&offset=${params.offset}`)
}

export async function getAuctionTransactionHistories(params: QueryBasicParams) {
    return request(API_SERVICE, `auction/transaction/current?limit=${params.limit}&offset=${params.offset}`)
}

export async function getThingByTokenId(tokenId: string) {
    return request(API_SERVICE, `auction/thing/token/${tokenId}`);
}

export async function getStatistics() {
    return request(API_SERVICE, `auction/statistics`);
}

export async function addAuctionTransaction(auctionTransactionParameters: any) {
    return request(API_SERVICE, `auction/transaction`, {
        method: 'POST',
        data: auctionTransactionParameters
    });
}

export async function getSaleView(id: number) {
    return request(METRICS_SERVICE, `metrics/count?key=dimension_sale_view_${id}`);
}

export async function getSaleDownload(id: number) {
    return request(METRICS_SERVICE, `metrics/count?key=dimension_sale_download_${id}`);
}

export async function getMineAuctionStatistics(startTime: number, endTime: number) {
    return request(API_SERVICE, `auction/statistics/mine?startTime=${startTime}&endTime=${endTime}`);
}
