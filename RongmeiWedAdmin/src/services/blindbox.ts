import request from '@/utils/request';
import {API_SERVICE} from "@/services/config";

export async function updateBlindBoxNFT(blindBoxNftData: any) {
    return request(API_SERVICE, `blind_box_nft/nft`, {
        method: 'POST',
        data: blindBoxNftData
    });
}

export async function deleteBlindBoxNFT(nftId: number) {
    return request(API_SERVICE, `blind_box_nft/nft/${nftId}`, {
        method: 'DELETE'
    });
}

export async function getMineBlindBoxNFTList(page: number, limit: number) {
    return request(API_SERVICE, `blind_box_nft/nft/mine?page=${page}&limit=${limit}`, {
        method: 'GET'
    });
}

export async function getBlindBoxNFT(nftId: number) {
    return request(API_SERVICE, `blind_box_nft/nft/${nftId}`);
}

export async function updateBlindBoxSale(saleData: any) {
    return request(API_SERVICE, `blind_box_nft/sale`, {
        method: 'POST',
        data: saleData
    });
}

// TODO:
export async function castEgg(castData: any) {
    return request(API_SERVICE, `box_egg/castBoxEgg`, {
        method: 'POST',
        data: castData
    });
}

export async function castList(status: any) {
    return request(API_SERVICE, `box_egg/findBoxEggByStatus?status=${status}`, {
        method: 'GET'
    });
}

export async function serList() {
    return request(API_SERVICE, `box_egg/findAllAddBoxEggName`, {
        method: 'GET'
    });
}

export async function serInfo(name: any) {
    return request(API_SERVICE, `box_egg/findAddBoxEggByName?seriesName=${name}`, {
        method: 'GET'
    });
}

export async function eggPack(eggData: any) {
    return request(API_SERVICE, `box_egg/randomPackageAddBoxEgg`, {
        method: 'POST',
        data: eggData
    });
}

export async function eggList(status: any) {
    return request(API_SERVICE, `box_egg/findAddBoxEggByStatus?status=${status}`, {
        method: 'GET'
    });
}

export async function eggPub(eggData: any) {
    return request(API_SERVICE, `box_egg/publishBoxEgg`, {
        method: 'POST',
        data: eggData
    });
}


export async function deleteBlindBoxSale(saleId: number) {
    return request(API_SERVICE, `blind_box_nft/sale/${saleId}`, {
        method: 'DELETE'
    });
}

export async function getMineBlindBoxSales(draftStatus: number) {
    return request(API_SERVICE, `blind_box_nft/sale/mine?draftStatus=${draftStatus}`);
}

export async function getBlindBoxSaleDetail(saleId: number) {
    return request(API_SERVICE, `blind_box_nft/sale/${saleId}`, {
        method: 'GET'
    });
}

export async function getMineBlindBoxSaleStatistics(startTime: number, endTime: number) {
    return request(API_SERVICE, `blind_box_nft/statistics/mine?startTime=${startTime}&endTime=${endTime}`);
}
