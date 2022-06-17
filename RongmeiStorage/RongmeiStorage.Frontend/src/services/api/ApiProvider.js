import {AccountServiceImpl} from './account/AccountService'
import {AccountServiceMock} from './account/AccountServiceMock'
import {AccessServiceImpl} from "./access/AccessService";
import {AccessServiceMock} from "./access/AccessServiceMock";
import {StatsServiceImpl} from "./stats/StatsService";

class ApiProvider {
    isMock = false;

    accountService = undefined;
    accessService = undefined;
    statsService = undefined;

    constructor() {
        if (this.isMock) {
            this.accountService = new AccountServiceMock();
            this.accessService = new AccessServiceMock();
            this.statsService = new StatsServiceImpl();
        } else {
            this.accountService = new AccountServiceImpl();
            this.accessService = new AccessServiceImpl();
            this.statsService = new StatsServiceImpl();
        }
    }
}

export const api = new ApiProvider();
