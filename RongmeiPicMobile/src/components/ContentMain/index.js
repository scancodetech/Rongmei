import React from 'react'
import { withRouter, Switch, Redirect, Route } from 'react-router-dom'
import LoadableComponent from '../../utils/LoadableComponent'

const Register = LoadableComponent(() => import('../../routes/Register/index'))
const RegisterInterest = LoadableComponent(() => import('../../routes/Register/Interest/index'))

const Playground = LoadableComponent(() => import('../../routes/Playground/index'))  //参数一定要是函数，否则不会懒加载，只会代码拆分
const Friend = LoadableComponent(() => import('../../routes/Friend/index'))
const Message = LoadableComponent(() => import('../../routes/Message/index'))
const Auction = LoadableComponent(() => import('../../routes/Auction/index'))
const Detail = LoadableComponent(() => import('../../routes/Detail'))
const Development = LoadableComponent(() => import('../../routes/Detail/Development/index'))

const Sale = LoadableComponent(() => import('../../routes/Sale'))
const SourceMaterial = LoadableComponent(() => import('../../routes/SourceMaterial/index'))

const SaleOut = LoadableComponent(() => import('../../routes/SaleOut'))
const Report = LoadableComponent(() => import('../../routes/Report'))
const Me = LoadableComponent(() => import('../../routes/Me/index'))
const Other = LoadableComponent(() => import('../../routes/Other/index'))

const MinePortal = LoadableComponent(() => import('../../routes/MinePortal/index'))
const MineOrder = LoadableComponent(() => import('../../routes/MineOrder/index'))
const MineCoin = LoadableComponent(() => import('../../routes/MineCoin/index'))
const UserGroup = LoadableComponent(() => import('../../routes/UserGroup/index'))
const Answer = LoadableComponent(() => import('../../routes/Answer/index'))
const Setting = LoadableComponent(() => import('../../routes/Setting/index'))
const Info = LoadableComponent(() => import('../../routes/Setting/Info/index'))
const Password = LoadableComponent(() => import('../../routes/Setting/Password/index'))
const CreatorCenter = LoadableComponent(() => import('../../routes/Setting/CreatorCenter/index'))
const CCApplicationType = LoadableComponent(() => import('../../routes/Setting/CreatorCenter/ApplicationType/index'))
const CCApplication = LoadableComponent(() => import('../../routes/Setting/CreatorCenter/Application/index'))
const CertificationAndReport = LoadableComponent(() => import('../../routes/Setting/CertificationAndReport/index'))
const CARCertification = LoadableComponent(() => import('../../routes/Setting/CertificationAndReport/Certification/index'))
const CARReport = LoadableComponent(() => import('../../routes/Setting/CertificationAndReport/Report/index'))
const CARContact = LoadableComponent(() => import('../../routes/Setting/CertificationAndReport/Contact/index'))
const Agreement = LoadableComponent(() => import('../../routes/Setting/Agreement/index'))
const MinePHH = LoadableComponent(() => import('../../routes/MinePHH/index'))
const MinePHHOrderList = LoadableComponent(() => import('../../routes/MinePHH/OrderList/index'))
const MinePHHOrderDetail = LoadableComponent(() => import('../../routes/MinePHH/OrderDetail/index'))

const PHH = LoadableComponent(() => import('../../routes/PHH/index'))
const Customized = LoadableComponent(() => import('../../routes/PHH/Customized/index'))
const SubmitResult = LoadableComponent(() => import('../../routes/PHH/SubmitResult/index'))
const InCooperateOrder = LoadableComponent(() => import('../../routes/PHH/Customized/InCooperateOrder/index'))

const QHH = LoadableComponent(() => import('../../routes/QHH/index'))
const QHHWallet = LoadableComponent(() => import('../../routes/QHH/Wallet/index'))
const QHHWalletBill = LoadableComponent(() => import('../../routes/QHH/Wallet/Bill/index'))
const QHHWalletBillDetail = LoadableComponent(() => import('../../routes/QHH/Wallet/Bill/Detail/index'))
const QHHWalletRecharge = LoadableComponent(() => import('../../routes/QHH/Wallet/Recharge/index'))
const QHHWalletWitchdrawal = LoadableComponent(() => import('../../routes/QHH/Wallet/Withdrawal/index'))
const QHHMine = LoadableComponent(() => import('../../routes/QHH/Mine/index'))
const QHHMineEditInfo = LoadableComponent(() => import('../../routes/QHH/Mine/EditInfo/index'))

const OrderDetail = LoadableComponent(() => import('../../routes/QHH/OrderDetail/index'))
const QHHApplication = LoadableComponent(() => import('../../routes/QHH/Application/index'))
const Withdrawal = LoadableComponent(() => import('../../routes/Withdrawal/index'))
const CHH = LoadableComponent(() => import('../../routes/CHH/index'))
const Liuhaizi = LoadableComponent(() => import('../../routes/liuhaizi/index'))
const TopicDetail = LoadableComponent(() => import('../../routes/TopicDetail/index'))

const ShareCode = LoadableComponent(() => import('../../routes/ShareCode/index'))
const InShare = LoadableComponent(() => import('../../routes/ShareCode/InShare/index'))

@withRouter
class ContentMain extends React.Component {
    render() {
        return (
            <div style={{ background: '#000' }}>
                <Switch>
                    <Route exact path='/register' component={Register} />
                    <Route exact path='/register/interest' component={RegisterInterest} />

                    <Route exact path='/home' component={Playground} />
                    <Route exact path='/friend' component={Friend} />
                    <Route exact path='/message' component={Message} />
                    <Route exact path='/auction' component={Auction} />
                    <Route exact path='/sale/:id' component={Sale} />
                    <Route exact path='/commodity/:id' component={SourceMaterial} />

                    <Route exact path='/detail/:id' component={Detail} />
                    <Route exact path='/detail/:id/development' component={Development} />

                    <Route exact path='/saleOut/:id' component={SaleOut} />
                    <Route exact path='/report/:id' component={Report} />
                    <Route exact path='/me' component={Me} />
                    <Route exact path='/other/:id' component={Other} />

                    <Route exact path='/me/coin' component={MineCoin} />
                    <Route exact path='/me/order' component={MineOrder} />
                    <Route exact path='/me/portal' component={MinePortal} />
                    <Route exact path='/setting' component={Setting} />
                    <Route exact path='/setting/info' component={Info} />
                    <Route exact path='/setting/password' component={Password} />
                    <Route exact path='/setting/creatorcenter' component={CreatorCenter} />
                    <Route exact path='/setting/creatorcenter/:type' component={CCApplicationType} />
                    <Route exact path='/setting/creatorcenter/application/:type' component={CCApplication} />
                    <Route exact path='/setting/certificationandreport' component={CertificationAndReport} />
                    <Route exact path='/setting/certificationandreport/certification' component={CARCertification} />
                    <Route exact path='/setting/certificationandreport/report' component={CARReport} />
                    <Route exact path='/setting/certificationandreport/contact' component={CARContact} />
                    <Route exact path='/setting/agreement' component={Agreement} />
                    <Route exact path='/minephh' component={MinePHH} />
                    <Route exact path='/minephh/orderlist/:type' component={MinePHHOrderList} />
                    <Route exact path='/minephh/orderlist/:type/detail/:id' component={MinePHHOrderDetail} />


                    <Route exact path='/user_group/:id' component={UserGroup} />
                    <Route exact path='/answer' component={Answer} />
                    <Route exact path='/phh' component={PHH} />
                    <Route exact path='/phh/customized/:id' component={Customized} />
                    <Route exact path='/phh/customized/:id/submitresult' component={SubmitResult} />
                    <Route exact path='/phh/customized/incooperate/:id' component={InCooperateOrder} />

                    <Route exact path='/qhh' component={QHH} />
                    <Route exact path='/qhh/wallet' component={QHHWallet} />
                    <Route exact path='/qhh/wallet/bill' component={QHHWalletBill} />
                    <Route exact path='/qhh/wallet/bill/detail/:id' component={QHHWalletBillDetail} />
                    <Route exact path='/qhh/wallet/recharge' component={QHHWalletRecharge} />
                    <Route exact path='/qhh/wallet/withdrawal' component={QHHWalletWitchdrawal} />
                    <Route exact path='/qhh/mine' component={QHHMine} />
                    <Route exact path='/qhh/mine/editinfo' component={QHHMineEditInfo} />


                    <Route exact path='/qhh/orderdetail/:id' component={OrderDetail} />
                    <Route exact path='/qhh/application' component={QHHApplication} />
                    <Route exact path='/me/withdrawal' component={Withdrawal} />
                    <Route exact path='/chh' component={CHH} />
                    <Route exact path='/liuhaizi' component={Liuhaizi} />
                    <Route exact path='/topicdetail/:topic' component={TopicDetail} />

                    <Route exact path='/me/sharecode' component={ShareCode} />
                    <Route exact path='/inshare/:user' component={InShare} />


                    <Redirect exact from='/' to='/home' />
                </Switch>
            </div>
        )
    }
}

export default ContentMain
