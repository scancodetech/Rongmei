import React, { Component } from 'react';
import developing from '@/assets/developing.png';
import helpStyles from '@/pages/account/center/help/help.less';

class AccountCenterHelpAutonomy extends Component<any> {

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
    );
  }
}

export default AccountCenterHelpAutonomy
