import {AccountServiceImpl} from './account/AccountService'
import {AccountServiceMock} from './account/AccountServiceMock'
import {PlatformServiceImpl} from "./platform/PlatformService";
import {UserGroupServiceImpl} from "./account/UserGroupService";
import {MallServiceImpl} from "./mall/MallService";
import {PayServiceImpl} from "./account/PayService";
import {RoleServiceImpl} from "./access/RoleService";
import {BiddingServiceImpl} from './RoleAudit/Bidding/BiddingService';
import {TCCServiceImpl} from './tcc/TCCService';
import {DraftServiceImpl} from "./draft/DraftService";
import {ExamServiceImpl} from "./exam/ExamService";
import {ReportServiceImpl} from "./report/ReportService";
import {NoticeServiceImpl} from "./notice/NoticeService";
import {TopicServiceImpl} from "./topic/TopicService";

class ApiProvider {
    isMock = false;

    accountService = undefined;
    platformService = undefined;
    userGroupService = undefined;
    mallService = undefined;
    payService = undefined;
    roleService = undefined;
    bidformService = undefined;
    tccService = undefined;
    draftService = undefined;
    examService = undefined;
    reportService = undefined;
    noticeService = undefined;
    topicService = undefined;

    constructor() {
        if (this.isMock) {
            this.accountService = new AccountServiceMock();
            this.platformService = new PlatformServiceImpl();
            this.userGroupService = new UserGroupServiceImpl();
            this.mallService = new MallServiceImpl();
            this.payService = new PayServiceImpl();
            this.roleService = new RoleServiceImpl();
            this.bidformService = new BiddingServiceImpl();
            this.tccService = new TCCServiceImpl();
            this.draftService = new DraftServiceImpl();
            this.examService = new ExamServiceImpl();
            this.reportService = new ReportServiceImpl();
            this.noticeService = new NoticeServiceImpl();
            this.topicService = new TopicServiceImpl();
        } else {
            this.accountService = new AccountServiceImpl();
            this.platformService = new PlatformServiceImpl();
            this.userGroupService = new UserGroupServiceImpl();
            this.mallService = new MallServiceImpl();
            this.payService = new PayServiceImpl();
            this.roleService = new RoleServiceImpl();
            this.bidformService = new BiddingServiceImpl();
            this.tccService = new TCCServiceImpl();
            this.draftService = new DraftServiceImpl();
            this.examService = new ExamServiceImpl();
            this.reportService = new ReportServiceImpl();
            this.noticeService = new NoticeServiceImpl();
            this.topicService = new TopicServiceImpl();
        }
    }
}

export const api = new ApiProvider();
