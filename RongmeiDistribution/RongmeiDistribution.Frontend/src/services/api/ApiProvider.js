import {AccountServiceImpl} from './account/AccountService'
import {AccountServiceMock} from './account/AccountServiceMock'
import {TCCServiceImpl} from "./tcc/TCCService";
import {DistributionServiceImpl} from "./distribution/DistributionService";

class ApiProvider {
    isMock = false;

    accountService = undefined;
    distributionService = undefined;
    tccService = undefined;

    constructor() {
        if (this.isMock) {
            this.accountService = new AccountServiceMock();
            this.distributionService = new DistributionServiceImpl();
            this.tccService = new TCCServiceImpl();
        } else {
            this.accountService = new AccountServiceImpl();
            this.distributionService = new DistributionServiceImpl();
            this.tccService = new TCCServiceImpl();
        }
    }
}

export const api = new ApiProvider();
