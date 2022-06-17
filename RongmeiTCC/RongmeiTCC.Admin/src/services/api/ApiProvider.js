import {AccountServiceImpl} from './account/AccountService'
import {AccountServiceMock} from './account/AccountServiceMock'
import {UploadServiceImpl} from "./upload/UploadService";
import {TCCServiceImpl} from "./tcc/TCCService";

class ApiProvider {
    isMock = false;

    accountService = undefined;
    tccService = undefined;
    uploadService = undefined;

    constructor() {
        if (this.isMock) {
            this.accountService = new AccountServiceMock();
            this.tccService = new TCCServiceImpl();
            this.uploadService = new UploadServiceImpl();
        } else {
            this.accountService = new AccountServiceImpl();
            this.tccService = new TCCServiceImpl();
            this.uploadService = new UploadServiceImpl();
        }
    }
}

export const api = new ApiProvider();
