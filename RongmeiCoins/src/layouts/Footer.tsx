import React from "react";
import {Row, Col} from "antd";
import footerStyles from "./Footer.less";
import {isPC} from "@/utils/utils";

const Footer: React.FC<any> = () => {
    return isPC() ? (
        <div className={footerStyles.footerPlaceHolder}>
            <div className={footerStyles.footerContainer}>
                <div>
                    <Row gutter={{xs: 8, sm: 16, md: 24, lg: 32}}>
                        <Col className="gutter-row" span={6}>
              <span style={{marginLeft: 100, marginTop: 20, height: 200, float: 'left'}}>
                <p className={footerStyles.footerItemTitleLarge}>跨次元</p>
                <p className={footerStyles.footerItemContent}>国际领先的</p>
                <p className={footerStyles.footerItemContent}>内容数字资产交易平台</p>
              </span>
                        </Col>
                        <Col className="gutter-row" span={6}>
              <span style={{marginLeft: 100, marginTop: 20, height: 200, float: 'left'}}>
                <p className={footerStyles.footerItemTitleSmall}>展示前台</p>
                <a href="http://www.dimension.pub" target='_blank'
                   className={footerStyles.footerItemContent}>跨次元数字艺术</a>
              </span>
                        </Col>
                        <Col className="gutter-row" span={6}>
              <span style={{marginLeft: 50, marginTop: 20, height: 200, float: 'left'}}>
                <p className={footerStyles.footerItemTitleSmall}>联系我们</p>
                  {/* <p className={footerStyles.footerItemContent}>张凌哲</p> */}
                  {/* <p className={footerStyles.footerItemContent}>18851822162</p> */}
                  <p className={footerStyles.footerItemContent}>surevil@foxmail.com</p>
              </span>
                        </Col>
                        <Col className="gutter-row" span={6}>
              <span style={{marginLeft: 100, marginTop: 20, height: 200, float: 'left'}}>
                <p className={footerStyles.footerItemTitleSmall}>平台协议</p>
                <a href="" className={footerStyles.footerItemContent}>隐私协议</a>
                <br/>
                <a href="" className={footerStyles.footerItemContent}>社区守则</a>
                <br/>
                  {/* <a href="http://rongmeitech.com/mall" className={footerStyles.footerItemContent}>融梅业务商城</a> */}
              </span>
                        </Col>
                    </Row>
                </div>
                <div style={{width: '100%', float: 'left'}}>
          <span style={{marginLeft: 100, marginBottom: 20, float: 'left'}}>
            <div style={{color: '#85888a', fontSize: 12, textAlign: "center"}}>
              ©2020 南京崇新数字科技有限公司 苏 ICP 备 20045842 号
            </div>
          </span>
                </div>
            </div>
        </div>
    ) : null;
};
export default Footer
