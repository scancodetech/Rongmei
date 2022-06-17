import {httpDistribution} from '../HttpService';

export class DistributionServiceImpl {
    async getUserDistribution() {
        return await httpDistribution.get(
            '/distribution/user'
        )
    }

    async getUserDistributionWithUsername(username) {
        return await httpDistribution.get(
            `/distribution/user/${username}`
        )
    }

    async distribution(distributionData) {
        return await httpDistribution.post(
            `/distribution/share`,
            distributionData
        )
    }

    async updateUserDistribution(distributionUpdateData) {
        return await httpDistribution.post(
            `/distribution`,
            distributionUpdateData
        )
    }
}
