import request from '@/utils/request';
import {COMMON_SERVICE} from '@/services/config';

export interface OuterPlatform {
    platform: string,
    userInfo: string,
}

export interface PostApplyParams {
    id: number,
    name: string,
    userTypes: string[],
    avatarUrl: string,
    position: string,
    email: string,
    outerPlatforms: OuterPlatform[],
    howToUse: string,
    howToKnow: string,
    gender: string,
    birthday: string,
    address: string,
    wechat: string,
    qq: string,
    description: string,
}

export interface ApplyArtworksParams {
    id: number,
    certificationType: string,
    imageUrls: string[],
    title: string,
    enTitle: string,
    pieceType: string,
    pieceStyle: string,
    pieceDate: string,
    description: string,
    outerPlatforms: OuterPlatform[],
    authorizationUrl: string,
}

export async function applyInfos(applyParams: PostApplyParams) {
    return request(COMMON_SERVICE, `certification/user`, {
        method: 'POST',
        data: applyParams
    });
}

export async function applyArtworks(applyParams: ApplyArtworksParams) {
    return request(COMMON_SERVICE, `certification/masterpiece`, {
        method: 'POST',
        data: applyParams
    });
}

export async function getMineCertification(certificationType: string) {
    return request(COMMON_SERVICE, `certification/mine?certificationType=${certificationType}`);
}