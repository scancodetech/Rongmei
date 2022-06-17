import {http} from '../HttpService';

export class TCCServiceImpl {
    async getTCCs() {
        return await http.get(
            '/tcc'
        )
    }

    async getTCC(key) {
        return await http.get(
            `/tcc/key?key=${key}`
        )
    }

    async updateTCC(tccData) {
        return await http.post(
            '/tcc', tccData
        )
    }
}
