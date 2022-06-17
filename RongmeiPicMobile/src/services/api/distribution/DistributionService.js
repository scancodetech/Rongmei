import {httpDistribution} from "../HttpService";

export class DistributionServiceImpl {
    async getUserDistribution(username) {
        return await httpDistribution.get(`distribution/user/${username}`);
    }

    async distribution(code, username) {
        return await httpDistribution.post('distribution/share', {
            code, username
        });
    }
}
