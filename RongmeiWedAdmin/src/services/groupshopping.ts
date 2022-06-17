import request from '@/utils/request';
import {API_SERVICE} from "@/services/config";

export interface GroupShoppingItem {
    id: number;
    title: string;
    price: number;
    coverUrl: string;
    tags: string[];
    contentUrl: string;
    information: string;
    description: string;
    comment: string;
    startTime: number;
    endTime: number;
    createTime: number;
    updateTime: number;
}

export interface GroupShopping {
    id: number;
    title: string;
    price: number;
    coverUrl: string;
    tags: string[];
    contentUrl: string;
    information: string;
    description: string;
    comment: string;
    startTime: number;
    endTime: number;
}

export async function getAuthorGroupShoppings(draftStatus: number) {
    return request(API_SERVICE, `group_shopping/author?status=${draftStatus}`);
}

export async function getGroupShoppings(page: number, limit: number, draftStatus: number) {
    return request(API_SERVICE, `group_shopping/?page=${page}&limit=${limit}&draftStatus=${draftStatus}`);
}

export async function getGroupShopping(groupShoppingId: number) {
    return request(API_SERVICE, `group_shopping/${groupShoppingId}`);
}

export async function updateGroupShopping(groupShoppingData: GroupShopping) {
    return request(API_SERVICE, `group_shopping`, {
        method: 'POST',
        data: groupShoppingData
    });
}

export async function deleteGroupShopping(groupShoppingId: number) {
    return request(API_SERVICE, `group_shopping/${groupShoppingId}`, {
        method: 'DELETE',
    });
}
