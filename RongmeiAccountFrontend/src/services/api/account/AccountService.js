import {httpCommon} from '../HttpService';

export class AccountServiceImpl {
    async login(loginData) {
        loginData.platformKey = "000000";
        return await httpCommon.get(
            '/account', loginData
        )
    }

    async loginWithPassword(loginData) {
        loginData.platformKey = "000000";
        return await httpCommon.get(
            '/account/password', loginData
        )
    }

    async register(registerData) {
        return await httpCommon.post(
            '/account/register', registerData
        )
    }

    async sendCaptcha(captchaData) {
        return await httpCommon.get(
            '/account/captcha', captchaData
        )
    }

    async saveUserInfo(data) {
        return await httpCommon.post(
            '/account/info', data
        )
    }

    async getUserInfo() {
        return await httpCommon.get(
            `/account/info`
        )
    }

    async getUserInfoEntity(username) {
        return await httpCommon.get(
            `/account/info/entity?username=${username}`
        )
    }

    async getUsers() {
        return await httpCommon.get(
            `/account/user`
        )
    }

    async updateUserRole(userRoleData) {
        return await httpCommon.post(
            `/account/role`, userRoleData
        )
    }
}
