import React, { Component } from 'react';

import { ConnectProps } from "@@/plugin-dva/connect";
import { connect, Link } from "umi";
import { List, Card } from 'antd';
import {ConnectState} from "@/models/connect";
import { getTCC } from "@/services/tcc";
import activityStyles from "./activity.less";
import formatDate from "@/utils/utils";
import product_1 from "../../assets/product_1.jpg";
import product_2 from "../../assets/product_2.jpg";
import product_3 from "../../assets/product_3.jpg";

interface ActivityProps extends Partial<ConnectProps> {
  loading: boolean;
  location: any;
}

class Activity extends Component<ActivityProps> {

  state = {
    events: []
  }

  async componentDidMount(): void {
    let res = await getTCC('rongmei.pic.event')
    this.setState({
      events: eval(res.tccTuple.value)
    })
  }

  render() {
    return (
      <div className={activityStyles.activityContainer}>
        <div className={activityStyles.headTitle}>
          活动
        </div>
        <div className={activityStyles.activityList}>
          <List 
          grid={{ gutter: 24, xxl:2, xl:2, lg:2, md:2, sm:1, xs:1}}
          dataSource={this.state.events}
          renderItem={item => (
            <List.Item>
              <Link
                  to={`/activity`}>
                  <Card className={activityStyles.card} hoverable
                    cover={
                      <div className={activityStyles.cardCover}>
                        <img className={activityStyles.cardImg} alt="" src={item.coverUrl} />
                      </div>
                    }>
                    <Card.Meta
                      title={
                        <div className={activityStyles.info}>
                          <div>
                            <span>{item.title}</span>
                          </div>
                         </div>
                      }
                      description={
                        <div>
                          <div>{item.description}</div>
                          {/* <div>{formatDate(item.createDate)}</div> */}
                        </div>
                      }
                      />
                  </Card>
                </Link>
              
            </List.Item>
          )}
          />
        </div>
      </div>
    );
  }
}

export default connect(
  ({ loading }: ConnectState) =>
    ({
      loading: loading.effects["resource/searchScripts"],
    }),
)

  (Activity);