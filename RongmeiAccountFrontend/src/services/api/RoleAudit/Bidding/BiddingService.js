import {httpCommon} from '../../HttpService';

export class BiddingServiceImpl {
    async getCertificationUsers() {
        return await httpCommon.get(
            '/certification/user'
        )
    }

    async submitCertificationUsers(certificationData) {
        return await httpCommon.post(
            `/certification/user/approval`, certificationData
        )
    }

    async getCertificationMasterpieces() {
        return await httpCommon.get(
            '/certification/masterpiece'
        )
    }

    async submitCertificationMasterpieces(certificationData) {
        return await httpCommon.post(
            `/certification/masterpiece/approval`, certificationData
        )
    }
}
