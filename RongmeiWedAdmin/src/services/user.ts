import request from '@/utils/request';
import {COMMON_SERVICE} from '@/services/config';

export interface RegisterParamsType {
    phone: string;
    password: string;
    captcha: string;
}

export interface UserAccountParamsType {
    username?: string;
    largeCoins: number;
}

export interface UserInfo {
    id: number;
    avatarUrl: string;
    nickname: string;
}

export async function sendCaptcha(phone: string) {
    return request(COMMON_SERVICE, `account/captcha?phone=${phone}`);
}

export async function login(phone: string, captcha: string) {
    return request(COMMON_SERVICE, `account?phone=${phone}&captcha=${captcha}`);
}

export async function loginWithPassword(phone: string, password: string) {
    return request(COMMON_SERVICE, `account?phone=${phone}&password=${password}`);
}

export async function register(params: RegisterParamsType) {
    return request(COMMON_SERVICE, `account/register`, {
        method: 'POST',
        data: params,
    });
}

export async function getUserInfo() {
    return request(COMMON_SERVICE, `account/info`);
}

export async function getUserBase() {
    return request(COMMON_SERVICE, `account/user/base`);
}

export interface UserInfoParams {
    avatarUrl: string;
    nickname: string;
    email: string;
    description: string;
    personWebsite: string;
    address: string;

}

export async function saveUserInfo(params: UserInfoParams) {
    return request(COMMON_SERVICE, `account/info`, {
        method: 'POST',
        data: params,
    });
}

export async function getUserAccount() {
    return request(COMMON_SERVICE, `user_account`);
}

export async function updateUserAccount(params: UserAccountParamsType) {
    return request(COMMON_SERVICE, `user_account`, {
        method: 'POST',
        data: params,
    });
}

export interface UserSecurityParams {
    id: number;
    userId: number;
    ethAddress: string;
    ethPrivateKey: string;
    payNum: string;
    captcha: string;
}

export async function getUserBasisSecurity() {
    return request(COMMON_SERVICE, `user_security/basis`);
}

export async function getUserAdvancedSecurityByCaptcha(captcha: string) {
    return request(COMMON_SERVICE, `user_security/advanced/captcha?captcha=${captcha}`);
}

export async function getUserAdvancedSecurityByPayCode(payCode: string) {
    return request(COMMON_SERVICE, `user_security/advanced/payCode?payCode=${payCode}`);
}

export async function updateUserSecurity(params: UserSecurityParams) {
    return request(COMMON_SERVICE, `user_security`, {
        method: 'POST',
        data: params,
    });
}

export async function getMineUserRelation() {
    return request(COMMON_SERVICE, `user_relation/mine`, {
        method: 'GET'
    });
}

export async function updateUserGroup(params) {
    return request(COMMON_SERVICE, `user_group`, {
        method: 'POST',
        data: params
    });
}

export async function getUserGroup(userGroupId) {
    return request(COMMON_SERVICE, `user_group/${userGroupId}`);
}

export async function getUserGroupByUsername(username) {
    return request(COMMON_SERVICE, `user_group/username?username=${username}`);
}

export async function getUserRoles() {
    return request(COMMON_SERVICE, `user_group/application`);
}

export async function discount(totalAmount: number) {
    return request(COMMON_SERVICE, `pay/discount?total_amount=${totalAmount}`);
}
