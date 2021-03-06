import React, { Component } from 'react';

import { connect, history } from 'umi';
import styles from "@/pages/welcome/welcome.less";
import { Button, Carousel, Col, message, Modal, Radio, Row, Space } from 'antd';
import { getTCC } from "@/services/tcc";
import image1 from '@/assets/beforeLogin_1.png';
import image2 from '@/assets/beforeLogin_2.png';
import image3 from '@/assets/beforeLogin_3.png';
import image4 from '@/assets/logo/yzgt.png';
import image5 from '@/assets/logo/jymw.png';
import image6 from '@/assets/logo/qh.png';
import image7 from '@/assets/logo/hh.png';
import image8 from '@/assets/logo/shjt.png';
import image9 from '@/assets/logo/zjdx.png';
import image10 from '@/assets/logo/bjdx.png';
import image11 from '@/assets/logo/bjyd.png';
import image12 from '@/assets/logo/sz.png';
import image13 from '@/assets/logo/zz.png';
import image14 from '@/assets/logo/sc.png';
import image15 from '@/assets/logo/dwjm.png';
import image16 from '@/assets/logo/tj.png';
import image17 from '@/assets/logo/zkd.png';
import image18 from '@/assets/logo/fd.png';
import image19 from '@/assets/logo/hn.png';
import image20 from '@/assets/logo/wh.png';
import image21 from '@/assets/logo/zycj.png';
import image22 from '@/assets/logo/nj.png';
import image23 from '@/assets/logo/bjhk.png';
import image24 from '@/assets/logo/whlg.png';
import image25 from '@/assets/logo/hzkj.png';
import image26 from '@/assets/logo/zs.png';
import image27 from '@/assets/logo/nk.png';
import image28 from '@/assets/logo/qd.png';
import image29 from '@/assets/logo/bjdx.png';
import image30 from '@/assets/logo/xg.png';
import image31 from '@/assets/logo/shdwjm.png';
import image32 from '@/assets/logo/rd.png';
import image33 from '@/assets/logo/xgkj.png';
import image34 from '@/assets/logo/yyk.png';
import image35 from '@/assets/logo/361.png';
import image36 from '@/assets/logo/kk.png';
import image37 from '@/assets/logo/nxdc.png';
import image38 from '@/assets/logo/xxd.png';
import image39 from '@/assets/logo/tl.png';
import image40 from '@/assets/logo/hfp.png';
import image41 from '@/assets/logo/sclm.png';
import image42 from '@/assets/logo/hfp.png';
import image43 from '@/assets/logo/sclm.png';
import image44 from '@/assets/logo/ksf.png';
import image45 from '@/assets/logo/dsn.png';
import image46 from '@/assets/logo/jn.png';
import image47 from '@/assets/logo/tx.png';
import image48 from '@/assets/logo/vivo.png';
import image49 from '@/assets/logo/jxb.png';
import image50 from '@/assets/logo/mt.png';
import image51 from '@/assets/logo/yw.png';
import image52 from '@/assets/logo/wyy.png';
import image53 from '@/assets/logo/mjmm.png';
import image54 from '@/assets/logo/yqsl.png';
import image55 from '@/assets/logo/wyyx.png';
import image56 from '@/assets/logo/xlwb.png';
import image57 from '@/assets/logo/bz.png';

import ApplyForm from '@/components/Apply';
import enterprise from '@/assets/enterprise.png';
import { ConnectState } from '@/models/connect';

class Welcome extends Component<any> {

  state = {
    type: '',
    events: [],
    roles: [],
    selectedRole: '',
    isModalShow: false,
  }

  async componentDidMount(): Promise<void> {
    let eventRes = await getTCC('rongmei.pic.event');
    let applyRoleRes = await getTCC('dimension.pic.applyrole');
    this.setState({
      events: eval(eventRes.tccTuple.value),
      roles: eval(applyRoleRes.tccTuple.value)
    }, () => console.log(this.state.roles))
  }

  handleBannerClick(type: String) {
    switch (type) {
      case 'apply': {
        const { dispatch } = this.props;
        let token = localStorage.getItem('token');
        if (!token || token.length === 0) {
          message.error('??????????????????????????????');
          if (dispatch) {
            dispatch({
              type: 'user/changeLoginShow',
              payload: {
                isShowLogin: true
              }
            });
          }
        } else {
          this.setState({
            isModalShow: true
          })
        }
      }
      case 'none': {
        return;
      }
      default:
        return;
    }
  }

  onRoleChange = e => {
    this.setState({
      selectedRole: e.target.value,
    });
  };

  render() {
    return (
      <div className={styles.welcomeContainer}>
        <Modal
          width={'600px'}
          bodyStyle={{ padding: 'auto 30px', display: 'flex', flexDirection: 'column', }}
          title={(<div style={{ textAlign: 'center' }}>????????????</div>)}
          visible={this.state.isModalShow}
          centered
          footer={null}
          onOk={() => {
            this.setState({
              isModalShow: false
            })
          }}
          onCancel={() => {
            this.setState({
              isModalShow: false
            })
          }}
        >
          {
            this.state.roles.map((roles) => (
              <div>
                <h3>{roles.type}</h3>
                <Radio.Group
                  style={{ width: '100%' }}
                  value={this.state.selectedRole}
                  onChange={(e) => {
                    this.onRoleChange(e)
                  }}>
                  {
                    roles.list.map((list) => (
                      <Row style={{ margin: '10px auto' }}>
                        <Col span={6}>
                          <h5 style={{ fontSize: 14, textAlign: 'center', }}>{list.title}:</h5>
                        </Col>
                        <Col span={18}>
                          <Row
                            align='top'
                          >
                            {list.typeList.map((item) => (
                              <Col style={{ margin: '0 0 10px 0' }} span={8}>
                                <Radio value={`${roles.type}/${list.title}/${item}`} key={`${roles.type}/${list.title}/${item}`}>{item}</Radio>
                              </Col>
                            ))}
                          </Row>
                        </Col>
                      </Row>
                    ))
                  }
                </Radio.Group>
              </div>

            ))
          }
          <div>
            <h3>???????????????</h3>
            <p style={{ color: '#1c1c1c', textAlign: 'center' }}>????????????</p>
          </div>
          <Button
            ghost={this.state.selectedRole.length === 0}
            disabled={this.state.selectedRole.length === 0}
            style={{ margin: '20px auto', fontWeight: 'bold', alignSelf: 'center', color: '#ffffff', backgroundColor: '#fe2341', border: '0px', borderRadius: '10px', fontSize: '18px', width: '120px', height: '40px' }}
            onClick={() => {
              history.push(`/account/center/applyRoles/${this.state.selectedRole}`);
            }}
          >
            ??????
          </Button>
        </Modal>
        <div className={styles.head}>
          <Carousel
            className={styles.banner}
            // autoplay
            dotPosition="bottom">
            {this.state.events.map((val, index) => (
              <div className={styles.bannerItem} key={index}>

                <img className={styles.bannerImg} alt="" src={val.coverUrl} onClick={() => { this.handleBannerClick(val.type) }} />
                {
                  val.type === 'apply' ?
                    <div style={{ width: '100vw', position: 'absolute', bottom: '130px', display: 'flex', flexDirection: 'column' }}>
                      <Button className={styles.bannerBtn} onClick={() => { this.handleBannerClick(val.type) }}>????????????</Button>
                    </div> : null
                }
              </div>
            ))}
          </Carousel>
        </div>
        <div className={styles.advertise}>
          <div className={styles.mainTitle}>
            ????????????
                    </div>
          <div className={styles.contents}>
            <div className={styles.contentsColumn}>
              <div className={styles.text}>
                <div className={styles.textTitle}>
                  ?????????????????????
                </div>
                <div className={styles.textContent}>
                  ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                </div>
              </div>
              <img className={styles.img} src={image1} />
            </div>
            <div className={styles.contentsColumn}>
              <img className={styles.img} src={image2} />
              <div className={styles.text}>
                <div className={styles.textTitle}>
                  ????????????????????????
                </div>
                <div className={styles.textContent}>
                  ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
                </div>
              </div>
            </div>
            <div className={styles.contentsColumn}>
              <div className={styles.text}>
                <div className={styles.textTitle}>
                  ???????????????
                                </div>
                <div className={styles.textContent}>
                  ?????????????????????????????????????????????????????????????????????????????????????????????
                                </div>
              </div>
              <img className={styles.img} src={image3} />
            </div>
          </div>
        </div>
        <div className={styles.enterprise}>
          <Space>
            <div style={{ fontSize: 50, }}>????????????</div>
          </Space>
          <Row gutter={[16, 16]} justify='start'>
            <Col span={12} offset={1}>
              <div style={{ fontSize: 20, fontWeight: 600, color: '#FE2341', textAlign: 'start' }}>Managment Side</div>
            </Col>
          </Row>
          <Row gutter={[16, 48]} justify='start'>
            <Col span={2} offset={1} style={{ textAlign: 'start' }}>
              <Space direction='vertical' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image4} />
                </Space>
              </Space>
            </Col>
            <Col span={4} offset={0}>
              <Space direction='vertical' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image5} />
                </Space>
              </Space>
            </Col>
          </Row>
          <Row gutter={[48, 16]} justify='start'>
            <Col span={12} offset={1}>
              <div style={{ fontSize: 20, fontWeight: 600, color: '#FE2341', textAlign: 'start' }}>???????????????????????????????????????????????????</div>
              <div style={{ fontSize: 20, fontWeight: 600, color: '#FE2341', textAlign: 'start' }}>????????????</div>
              {/* ?????????????????? */}
            </Col>
          </Row>
          <Row gutter={[16, 48]}>
            {/* ????????? */}
            <Col span={4} offset={1} style={{ textAlign: 'start' }}>
              <Space direction='vertical' size='large' align='start'>
                <Space size='large' >
                  <img style={{ width: 'auto', height: '100%' }} src={image6} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image7} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image8} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image9} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image26} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image27} />
                </Space>
              </Space>
            </Col>

            {/* ????????? */}
            <Col span={4} style={{ textAlign: 'start' }}>
              <Space direction='vertical' size='large' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image10} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image11} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image12} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image13} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image28} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image29} />
                </Space>
              </Space>
            </Col>

            {/* ????????? */}
            <Col span={5} style={{ textAlign: 'start' }}>
              <Space direction='vertical' size='large' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image14} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image15} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image16} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image17} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image30} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image31} />
                </Space>
              </Space>
            </Col>

            {/* ????????? */}
            <Col span={4} style={{ textAlign: 'start' }}>
              <Space direction='vertical' size='large' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image18} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image19} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image20} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image21} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image32} />
                </Space>
              </Space>
            </Col>

            {/* ????????? */}
            <Col span={5} style={{ textAlign: 'start' }}>
              <Space direction='vertical' size='large' align='start'>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image22} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image23} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image24} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image25} />
                </Space>
                <Space size='large'>
                  <img style={{ width: 'auto', height: '100%' }} src={image33} />
                </Space>
              </Space>
            </Col>
          </Row>
          <Row gutter={[48, 16]} justify='start'>
            <Col span={12} offset={1}>
              <div style={{ fontSize: 20, fontWeight: 600, color: '#FE2341', textAlign: 'start' }}>Cooperative Enterprise</div>
            </Col>
          </Row>
          <Row gutter={[16, 48]} justify='start'>
            <Col span={23} offset={1}>
              <Space align='center' size='large' wrap style={{ textAlign: 'start' }}>
                <img style={{ width: 'auto', height: '100%' }} src={image34} />
                <img style={{ width: 'auto', height: '100%' }} src={image35} />
                <img style={{ width: 'auto', height: '100%' }} src={image36} />
                <img style={{ width: 'auto', height: '100%' }} src={image37} />
                <img style={{ width: 'auto', height: '100%' }} src={image38} />
                <img style={{ width: 'auto', height: '100%' }} src={image39} />
                <img style={{ width: 'auto', height: '100%' }} src={image40} />
                <img style={{ width: 'auto', height: '100%' }} src={image41} />
                <img style={{ width: 'auto', height: '100%' }} src={image42} />
                <img style={{ width: 'auto', height: '100%' }} src={image43} />
                <img style={{ width: 'auto', height: '100%' }} src={image44} />
                <img style={{ width: 'auto', height: '100%' }} src={image45} />
                <img style={{ width: 'auto', height: '100%' }} src={image46} />
                <img style={{ width: 'auto', height: '100%' }} src={image47} />
                <img style={{ width: 'auto', height: '100%' }} src={image48} />
                <img style={{ width: 'auto', height: '100%' }} src={image49} />
                <img style={{ width: 'auto', height: '100%' }} src={image50} />
                <img style={{ width: 'auto', height: '100%' }} src={image51} />
                <img style={{ width: 'auto', height: '100%' }} src={image52} />
                <img style={{ width: 'auto', height: '100%' }} src={image53} />
                <img style={{ width: 'auto', height: '100%' }} src={image54} />
                <img style={{ width: 'auto', height: '100%' }} src={image55} />
                <img style={{ width: 'auto', height: '100%' }} src={image56} />
                <img style={{ width: 'auto', height: '100%' }} src={image57} />
              </Space>
            </Col>
          </Row>
        </div>
      </div >);
  }
}

export default connect(({ user, userInfo }: ConnectState) => ({
  user,
  userInfo,
}))(Welcome);
