import {http} from '../HttpService';

export class StatsServiceImpl {
    async loadStats() {
        return await http.get(
            `/stats`
        )
    }

    async loadHomeStats() {
        return await http.get(
            `/stats/home`
        )
    }
}
