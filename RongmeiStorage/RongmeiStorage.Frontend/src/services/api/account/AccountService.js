import {httpCommon} from '../HttpService';

export class AccountServiceImpl {
    async login(loginData) {
        return await httpCommon.get(
            '/account', loginData
        )
    }

    async loginWithPassword(loginData) {
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
}
