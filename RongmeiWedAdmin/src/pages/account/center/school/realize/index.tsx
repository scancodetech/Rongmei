import React, { Component } from 'react';
import developing from '@/assets/developing.png';
import schoolStyle from '@/pages/account/center/school/school.less';
import { Card, List } from 'antd';
import cover from '@/assets/product_3.jpg';

const { Meta } = Card;

class AccountCenterSchoolRealize extends Component<any> {

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
      // <div className={schoolStyle.realizeContainer}>
      //   <div className={schoolStyle.headTitle}>
      //     如何变现
      //   </div>
      //   <List
      //     grid={{ gutter: 16, column: 4 }}
      //     dataSource={[1, 2, 3]}
      //     renderItem={item => (
      //       <div>
      //         <Card
      //           bordered={false}
      //           hoverable={false}
      //           className={schoolStyle.imgCard}
      //           cover={
      //             <img className={schoolStyle.img} alt='如何变现封面' src={cover} />
      //           }
      //         >
      //           <Meta
      //             title={
      //               <div className={schoolStyle.manageTitle}>如何变现封面</div>
      //             }
      //             description={
      //               <div className={schoolStyle.manageCount}>1.3w 播放量</div>
      //             }
      //           ></Meta>
      //         </Card>
      //       </div>
      //     )}
      //   />
      // </div>
    );
  }
}

export default AccountCenterSchoolRealize
