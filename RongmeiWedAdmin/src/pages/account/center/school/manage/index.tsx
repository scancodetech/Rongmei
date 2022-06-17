import React, { Component } from 'react';
import developing from '@/assets/developing.png';
import schoolStyle from '@/pages/account/center/school/school.less';
import { Card, List, Tabs } from 'antd';
import cover from '@/assets/product_3.jpg';

const { Meta } = Card;

const { TabPane } = Tabs;

class AccountCenterSchoolManage extends Component<any> {

  onTabChange() {

  }

  render() {
    return (
      <div>
      <div style={{
        height: '300px',
        width: '300px',
        margin: '150px auto 100px',
        backgroundImage: `url(${developing})`,
        backgroundSize: 'cover',
      }}>
      </div>
    </div >
      // <div className={schoolStyle.manageContainer}>
      //   <div className={schoolStyle.headTitle}>
      //     内容资产创作者如何经营
      //   </div>
      //   <div className={schoolStyle.manageTabs}>
      //     <Tabs defaultActiveKey="1" onChange={this.onTabChange}>
      //       <TabPane tab="运营指南" key="1">
      //         <List
      //           grid={{ gutter: 16, column: 4 }}
      //           dataSource={[1, 2, 3]}
      //           renderItem={item => (
      //             <div>
      //               <Card
      //                 bordered={false}
      //                 hoverable={false}
      //                 className={schoolStyle.imgCard}
      //                 cover={
      //                   <img className={schoolStyle.img} alt='运营指南封面' src={cover} />
      //                 }
      //               >
      //                 <Meta
      //                   title={
      //                     <div className={schoolStyle.manageTitle}>运营指南标题</div>
      //                   }
      //                   description={
      //                     <div className={schoolStyle.manageCount}>1.3w 播放量</div>
      //                   }
      //                 ></Meta>
      //               </Card>
      //             </div>
      //           )}
      //         />
      //       </TabPane>
      //       <TabPane tab="创作指南" key="2">
      //         <List
      //           grid={{ gutter: 16, column: 4 }}
      //           dataSource={[1, 2, 3]}
      //           renderItem={item => (
      //             <div>
      //               <Card
      //                 bordered={false}
      //                 hoverable={false}
      //                 className={schoolStyle.imgCard}
      //                 cover={
      //                   <img className={schoolStyle.img} alt='创作指南封面' src={cover} />
      //                 }
      //               >
      //                 <Meta
      //                   title={
      //                     <div className={schoolStyle.manageTitle}>创作指南标题</div>
      //                   }
      //                   description={
      //                     <div className={schoolStyle.manageCount}>1.3w 播放量</div>
      //                   }
      //                 ></Meta>
      //               </Card>
      //             </div>
      //           )}
      //         />
      //       </TabPane>
      //     </Tabs>
      //   </div>
      // </div>

    );
  }
}

export default AccountCenterSchoolManage
