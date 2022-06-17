import React, { Component } from 'react';

import { message, Switch, AutoComplete, Space, Input, } from 'antd';
import { SearchOutlined } from '@ant-design/icons'
import commodityStyles from '@/pages/account/center/commodity/commodity.less';
import { getMineCertification } from "@/services/apply";

class AccountCenterCommoditySetting extends Component<any> {

    state = {
        limit: 10,
        total: 0,
        isAddMark: false
    }

    async checkCertificationType(type: string) {
        const certificationRes = await getMineCertification(type);
        if (!certificationRes.isUserCertificationChecked || !certificationRes.isUserMasterpieceChecked) {
            message.error("暂无此区域权限，正在前往验证")
            this.props.history.push(`/account/center/applyRoles/内容创作者/${type}/0`)
        }
    }

    async componentDidMount(): Promise<void> {
        await this.checkCertificationType("素材类");
    }

    onSwitchChange() {

    }

    render() {

        return (
            <div className={commodityStyles.settingContainer}>
                <div className={commodityStyles.headTitle}>素材设置</div>
                <div className={commodityStyles.option}>
                    <Space size='small'>
                        <AutoComplete
                            // className={auctionStyles.wrapperchange}
                            dropdownClassName="certain-category-search-dropdown"
                            dropdownMatchSelectWidth={false}
                            dropdownStyle={{ width: 240 }}
                            style={{ width: 240 }}
                            // value={this.state.thingId.toString()}
                            options={[{ value: '1' }, { value: '2' }]}

                            placeholder="搜索以选择合适的素材"
                        >
                            <Input suffix={<SearchOutlined />} />
                        </AutoComplete>
                    </Space>
                    <Space size='small'>
                        <Switch defaultChecked onChange={this.onSwitchChange} style={{ alignSelf: 'center', marginLeft: 20 }} />
                        <div className={commodityStyles.optionText} style={{ alignSelf: 'center' }}>添加水印</div>
                    </Space>
                </div>
                <Space size='middle'>
                    <Space size='middle'>
                        <Switch defaultChecked onChange={this.onSwitchChange} style={{ alignSelf: 'center', marginLeft: 260 }} />
                        <div className={commodityStyles.optionText} style={{ alignSelf: 'center' }}>一键全部添加</div>
                    </Space>
                </Space>

            </div>
        );
    }
}

export default AccountCenterCommoditySetting
