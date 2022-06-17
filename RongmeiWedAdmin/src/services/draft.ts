import request from "@/utils/request";
import {API_SERVICE} from "@/services/config";

export async function getDraftReason(relationId: number, draftType: number) {
    return request(API_SERVICE, `draft/reason?relationId=${relationId}&draftType=${draftType}`);
}