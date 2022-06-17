import React from 'react'
import {withRouter, Switch, Redirect, Route} from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'

const Home = LoadableComponent(() => import('../../routes/Home/index'))  //参数一定要是函数，否则不会懒加载，只会代码拆分
const RolePage = LoadableComponent(() => import('../../routes/Access/Role/index'))
const EmployeePage = LoadableComponent(() => import('../../routes/Access/Employee/index'))
const PlatformPage = LoadableComponent(() => import('../../routes/Platform/index'))
const UserGroupPage = LoadableComponent(() => import('../../routes/UserGroup/index'))
const CommodityPage = LoadableComponent(() => import('../../routes/Commodity/index'))
const RechargePage = LoadableComponent(() => import('../../routes/Pay/Recharge/index'))
const WithdrawPage = LoadableComponent(() => import('../../routes/Pay/Withdraw/index'))
const CertificationUserPage = LoadableComponent(() => import('../../routes/RoleAudit/CertificationUser/index'))
const CertificationMasterpiecePage = LoadableComponent(() => import('../../routes/RoleAudit/CertificationMasterpiece/index'))
const ContentReviewCommodityPage = LoadableComponent(() => import('../../routes/ContentReview/Commodity/index'))
const ContentReviewSalePage = LoadableComponent(() => import('../../routes/ContentReview/Sale/index'))
const ContentReviewBlindBoxSalePage = LoadableComponent(() => import('../../routes/ContentReview/BlindBoxSale/index'))
const ContentReviewGroupShoppingPage = LoadableComponent(() => import('../../routes/ContentReview/GroupShopping/index'))
const ContentReviewProposalPage = LoadableComponent(() => import('../../routes/ContentReview/Proposal/index'))
const ContentReviewChildPage = LoadableComponent(() => import('../../routes/ContentReview/Child/index'))
const ExamExercisePage = LoadableComponent(() => import('../../routes/Exam/Exercise/index'))
const ExamCheckExercisePage = LoadableComponent(() => import('../../routes/Exam/CheckExercise/index'))
const ReportPage = LoadableComponent(() => import('../../routes/Report/index'))
const TopicPage = LoadableComponent(() => import('../../routes/Topic/index'))

//关于
const About = LoadableComponent(() => import('../../routes/About/index'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{padding: 16, position: 'relative'}}>
                <Switch>
                    <Route exact path='/home' component={Home}/>

                    <Route exact path='/home/access/role' component={RolePage}/>
                    <Route exact path='/home/access/employee' component={EmployeePage}/>
                    <Route exact path='/home/platform' component={PlatformPage}/>
                    <Route exact path='/home/group' component={UserGroupPage}/>
                    <Route exact path='/home/commodity' component={CommodityPage}/>
                    <Route exact path='/home/about' component={About}/>
                    <Route exact path='/home/pay/recharge' component={RechargePage}/>
                    <Route exact path='/home/pay/withdraw' component={WithdrawPage}/>
                    <Route exact path='/home/certification/roleaudit/user' component={CertificationUserPage}/>
                    <Route exact path='/home/certification/roleaudit/masterpiece'
                           component={CertificationMasterpiecePage}/>
                    <Route exact path='/home/certification/content/commodity' component={ContentReviewCommodityPage}/>
                    <Route exact path='/home/certification/content/sale'
                           component={ContentReviewSalePage}/>
                    <Route exact path='/home/certification/content/blindboxsale'
                           component={ContentReviewBlindBoxSalePage}/>
                    <Route exact path='/home/certification/content/groupshopping'
                           component={ContentReviewGroupShoppingPage}/>
                    <Route exact path='/home/certification/content/proposal'
                           component={ContentReviewProposalPage}/>
                    <Route exact path='/home/certification/content/child'
                           component={ContentReviewChildPage}/>
                    <Route exact path='/home/exam/exercise' component={ExamExercisePage}/>
                    <Route exact path='/home/exam/checkexercise' component={ExamCheckExercisePage}/>
                    <Route exact path='/home/report' component={ReportPage}/>
                    <Route exact path='/home/topic' component={TopicPage}/>

                    <Redirect exact from='/' to='/home'/>
                </Switch>
            </div>
        )
    }
}

export default ContentMain
