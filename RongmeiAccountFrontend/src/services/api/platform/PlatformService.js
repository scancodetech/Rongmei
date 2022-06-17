import {httpCommon} from '../HttpService';

export class PlatformServiceImpl {
    async getPlatforms() {
        return await httpCommon.get(
            '/platform'
        )
    }

    async getPlatformDetail(platformId) {
        return await httpCommon.get(
            `/platform/${platformId}`
        )
    }

    async updatePlatform(platformData) {
        return await httpCommon.post(
            `/platform`, platformData
        )
    }
}
