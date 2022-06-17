import request from "@/utils/request";
import {TCC_URL} from "@/services/config";

export async function getTCC(key: string) {
  return request(`${TCC_URL}/tcc/key?key=${key}`);
}
