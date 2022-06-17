import {http} from '../HttpService';

export class AccessServiceImpl {
    async updateAccess(accessUpdateParameters) {
        return await http.post(
            `/access`, accessUpdateParameters
        )
    }

    async loadAccess() {
        return await http.get(
            `/access`
        )
    }
}
