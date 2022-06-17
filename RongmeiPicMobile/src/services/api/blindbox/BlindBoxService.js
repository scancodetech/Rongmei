import {http} from "../HttpService";

export class BlindBoxServiceImpl {
    async updateBlindBoxNFT(blindBoxNftData) {
        return http.post(`blind_box_nft/nft`, blindBoxNftData)
    }
}
