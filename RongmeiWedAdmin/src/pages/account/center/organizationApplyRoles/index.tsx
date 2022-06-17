import React, { useState, useEffect } from 'react';
import { getServiceBaseUrl } from '@/services/DNS';
import { COMMON_SERVICE } from '@/services/config';
import moment from 'moment';
import styles from './organizationApplyRoles.less';
import { ConnectState } from '@/models/connect';
import { history, connect } from 'umi';
import { applyArtworks, applyInfos, getMineCertification } from '@/services/apply';
import { getTCC } from "@/services/tcc";
import { func } from 'prop-types';


function OrganizationApplyRoles() {
    const [step, setStep] = useState(0);

    switch (step) {
        case 0: return firstStepRender();
        case 1: return secondStepRender();
    }

    function firstStepRender() {
        useEffect(() => {

        })
        return (
            <div className={styles.page}>
                <h1>跨次元创作服务平台——机构申请</h1>
            </div>
        )
    }
    function secondStepRender() {
        return null;
    }

}
export default connect(({ user, userInfo }: ConnectState) => ({
    user,
    userInfo,
}))(OrganizationApplyRoles);