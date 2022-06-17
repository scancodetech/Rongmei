import {Tag} from 'antd';
import React, {useEffect} from 'react';
import {connect, ConnectProps} from 'umi';
import {ConnectState} from '@/models/connect';
import Avatar from './AvatarDropdown';
// import HeaderSearch from '../HeaderSearch';
import styles from './index.less';
import HeaderSearch from "@/components/HeaderSearch";
import {getTCC} from "@/services/tcc";

export type SiderTheme = 'light' | 'dark';

export interface GlobalHeaderRightProps extends Partial<ConnectProps> {
  theme?: SiderTheme;
  layout: 'sidemenu' | 'topmenu';
  history: any;
}

const ENVTagColor = {
  dev: 'orange',
  test: 'green',
  pre: '#87d068',
};

const GlobalHeaderRight: React.SFC<GlobalHeaderRightProps> = (props) => {
  const {theme, layout, history} = props;
  const [keywords, setKeywords] = React.useState([]);
  const [key, setKey] = React.useState("");
  let className = styles.right;

  if (theme === 'dark' && layout === 'topmenu') {
    className = `${styles.right}  ${styles.dark}`;
  }

  return (
    <div className={className}>
      <Avatar/>
      {REACT_APP_ENV && (
        <span>
          <Tag color={ENVTagColor[REACT_APP_ENV]}>{REACT_APP_ENV}</Tag>
        </span>
      )}
    </div>
  );
};

export default connect(({settings}: ConnectState) => ({
  theme: settings.navTheme,
  layout: settings.layout,
}))(GlobalHeaderRight);
