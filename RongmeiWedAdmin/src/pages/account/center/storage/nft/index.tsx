import React, {Component} from 'react';

import nftStyles from "@/pages/account/center/storage/nft/nft.less";
import helpStyles from "@/pages/account/center/help/help.less";
import {List, Modal, Button} from "antd";
import {getThings} from "@/services/auction";

class AccountStorageNFT extends Component<any> {
    state = {
        things: [],
        isModalVisible: false,
        confirmationLetterUrl: '',
        name: ''
    }

    async componentDidMount() {
        this.getThings();
    }

    async getThings() {
        let res = await getThings();
        this.setState({
            things: res.thingItems
        })
    }

    showModal(thing: any) {
        this.setState({
            isModalVisible: true,
            confirmationLetterUrl: thing.confirmationLetterUrl,
            name: thing.name
        })
    }

    closeModal() {
        this.setState({
            isModalVisible: false
        })
    }

    download = (url) => {
        window.open(url);
    }

    render() {
        return (
            <div className={nftStyles.musicContainer}>
                <div className={helpStyles.headTitle}>竞品存证</div>
                <div className={helpStyles.contactContent}>
                    <Modal title={this.state.name} visible={this.state.isModalVisible} onOk={() => this.closeModal()}
                           onCancel={() => this.closeModal()}>
                        <div style={{textAlign: 'center'}}>
                            <img style={{width: 300, marginBottom: 20}}
                                 src={this.state.confirmationLetterUrl}/>
                            <br/>
                            <Button type="primary"
                                    onClick={() => this.download(this.state.confirmationLetterUrl)}>下载</Button>
                        </div>
                    </Modal>
                    <List
                        itemLayout="horizontal"
                        dataSource={this.state.things}
                        renderItem={item => (
                            <List.Item
                                actions={[<a onClick={() => this.showModal(item)} key="load-storage-result">查看存证结果</a>]}
                            >
                                <List.Item.Meta
                                    avatar={<img
                                        style={{
                                            width: 100,
                                            height: 100
                                        }}
                                        src={item.url}/>}
                                    title={<a href="https://ant.design">{item.name}</a>}
                                    description={item.description}
                                />
                            </List.Item>
                        )}
                    />
                </div>
            </div>
        );
    }
}

export default AccountStorageNFT
