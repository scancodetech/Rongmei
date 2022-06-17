import React, {Component} from 'react';

import helpStyles from '@/pages/account/center/help/help.less';

class AccountCenterHelpContact extends Component<any> {

    render() {
        return (
            <div className={helpStyles.contactContainer}>
                <div className={helpStyles.headTitle}>联系我们</div>
                <div className={helpStyles.contactContent}>
                    <div className={helpStyles.contactInfo}>
                        如在使用过程中遇到任何问题，或有更多意见/建议，欢迎发送邮件至 surevil@foxmail.com 。
                    </div>
                </div>
            </div>
        );
    }
}

export default AccountCenterHelpContact
