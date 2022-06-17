import {httpCommon} from "../HttpService";

export class ApplyServiceImpl {
    async applyInfos(applyData) {
        return await httpCommon.post(
            '/certification/user', applyData
        )
    }

    async applyMasterpiece(applyData){
        return await httpCommon.post(
            '/certification/masterpiece', applyData
        )
    }

    async getMineCertification(certificationType){
        return await httpCommon.get(
            `/certification/mine?certificationType=${certificationType}`
        )
    }
}
