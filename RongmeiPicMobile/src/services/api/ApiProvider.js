import {AccountServiceImpl} from './account/AccountService'
import {AccountServiceMock} from './account/AccountServiceMock'
import {UploadServiceImpl} from "./upload/UploadService";
import {OrderServiceImpl} from "./order/OrderService";
import {UserGroupServiceImpl} from "./usergroup/UserGroupService";
import {TCCServiceImpl} from "./tcc/TCCService";
import {MoneyServiceImpl} from "./money/MoneyService";
import {CommodityServiceImpl} from "./commodity/CommodityService";
import {UserServiceImpl} from './user/User';
import {AuctionServiceImpl} from './auction/AuctionService';
import {RelationServiceImpl} from "./relation/RelationService";
import {MetricsServiceImpl} from "./metrics/MetricsService";
import {ReportServiceImpl} from "./report/ReportService";
import {ExamServiceImpl} from "./exam/ExamService";
import {BalanceServiceImpl} from "./balance/BalanceService";
import {BoxOrderServiceImpl} from "./boxorder/BoxOrderService";
import {ApplyServiceImpl} from "./apply/ApplyService";
import {OuterServiceImpl} from "./outer/OuterService";
import {BlindBoxServiceImpl} from "./blindbox/BlindBoxService";
import {ChildServiceImpl} from "./child/ChildService";
import {RecommendServiceImpl} from "./recommend/RecommendService";
import {DistributionServiceImpl} from "./distribution/DistributionService";
import {CommentServiceImpl} from "./comment/CommentService";

class ApiProvider {
    isMock = false;

    accountService = undefined;
    uploadService = undefined;
    orderService = undefined;
    userGroupService = undefined;
    tccService = undefined;
    moneyService = undefined;
    commodityService = undefined;
    auctionService = undefined;
    userService = undefined;
    relationService = undefined;
    metricsService = undefined;
    reportService = undefined;
    examService = undefined;
    balanceService = undefined;
    boxOrderService = undefined;
    applyService = undefined;
    outerService = undefined;
    blindBoxService = undefined;
    childService = undefined;
    recommendService = undefined;
    distributionService = undefined;
    commentService = undefined;

    constructor() {
        if (this.isMock) {
            this.accountService = new AccountServiceMock();
            this.uploadService = new UploadServiceImpl();
            this.orderService = new OrderServiceImpl();
            this.userGroupService = new UserGroupServiceImpl();
            this.tccService = new TCCServiceImpl();
            this.moneyService = new MoneyServiceImpl();
            this.commodityService = new CommodityServiceImpl();
            this.auctionService = new AuctionServiceImpl();
            this.userService = new UserServiceImpl();
            this.relationService = new RelationServiceImpl();
            this.metricsService = new MetricsServiceImpl();
            this.reportService = new ReportServiceImpl();
            this.examService = new ExamServiceImpl();
            this.balanceService = new BalanceServiceImpl();
            this.boxOrderService = new BoxOrderServiceImpl();
            this.applyService = new ApplyServiceImpl();
            this.outerService = new OuterServiceImpl();
            this.blindBoxService = new BlindBoxServiceImpl();
            this.childService = new ChildServiceImpl();
            this.recommendService = new RecommendServiceImpl();
            this.distributionService = new DistributionServiceImpl();
            this.commentService = new CommentServiceImpl();
        } else {
            this.accountService = new AccountServiceImpl();
            this.uploadService = new UploadServiceImpl();
            this.orderService = new OrderServiceImpl();
            this.userGroupService = new UserGroupServiceImpl();
            this.tccService = new TCCServiceImpl();
            this.moneyService = new MoneyServiceImpl();
            this.commodityService = new CommodityServiceImpl();
            this.auctionService = new AuctionServiceImpl();
            this.userService = new UserServiceImpl();
            this.relationService = new RelationServiceImpl();
            this.metricsService = new MetricsServiceImpl();
            this.reportService = new ReportServiceImpl();
            this.examService = new ExamServiceImpl();
            this.balanceService = new BalanceServiceImpl();
            this.boxOrderService = new BoxOrderServiceImpl();
            this.applyService = new ApplyServiceImpl();
            this.outerService = new OuterServiceImpl();
            this.blindBoxService = new BlindBoxServiceImpl();
            this.childService = new ChildServiceImpl();
            this.recommendService = new RecommendServiceImpl();
            this.distributionService = new DistributionServiceImpl();
            this.commentService = new CommentServiceImpl();
        }
    }
}

export const api = new ApiProvider();
