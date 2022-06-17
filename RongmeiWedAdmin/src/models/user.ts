import {history, Reducer, Effect} from 'umi';

import {setAuthority} from '@/utils/authority';
import {login, loginWithPassword, sendCaptcha, register, getUserGroupByUsername} from "@/services/user";
import {message} from "antd";

export interface StateType {
    status?: 'ok' | 'error';
    loginType?: 'account' | "phone";
    isShowLogin: boolean;
}

export interface LoginModelType {
    namespace: string;
    state: StateType;
    effects: {
        login: Effect;
        captcha: Effect;
        register: Effect;
        logout: Effect;
    };
    reducers: {
        changeLoginStatus: Reducer<StateType>;
        changeLoginShow: Reducer<StateType>;
    };
}

const Model: LoginModelType = {
    namespace: 'user',
    state: {
        status: undefined,
    },

    effects: {
        * login({payload}, {call, put}) {
            try {
                let accountResponse = yield call(login, payload.phone, payload.captcha);
                if (accountResponse.token) {
                    yield setAuthority(accountResponse.token);
                    let groupResponse = yield call(getUserGroupByUsername, payload.phone);
                    if(groupResponse.userGroupItems.length>0) {
                        yield localStorage.setItem('userGroupId', groupResponse.userGroupItems[0].id);
                        yield put({
                            type: 'changeLoginStatus',
                            payload: accountResponse,
                        });
                    }else{
                        localStorage.setItem('userGroupId', '');
                        message.error('用户暂不在组内')
                    }
                } else {
                    localStorage.setItem('token', '');
                    message.error('用户名验证码错误')
                }
            } catch (e) {
                localStorage.setItem('token', '');
                localStorage.setItem('userGroupId', '');
                message.error('用户名验证码错误或用户暂不在组内')
            }
        },

        * captcha({payload}, {call, put}) {
            const response = yield call(sendCaptcha, payload.phone);
            yield put({
                type: 'changeLoginStatus',
                payload: response,
            });
        },

        * register({payload}, {call, put}) {
            let response = yield call(login, payload.phone, payload.captcha);
            response = yield call(register, payload);
            yield put({
                type: 'changeLoginStatus',
                payload: response,
            });
            // Register successfully
            if (response.token) {
                yield setAuthority(response.token);
                message.success('注册成功！');
            }
        },

        logout() {
            localStorage.clear();
        },
    },

    reducers: {
        changeLoginStatus(state, {payload}) {
            return {
                ...state,
                status: payload.status
            };
        },
        changeLoginShow(state, {payload}) {
            return {
                ...state,
                isShowLogin: payload.isShowLogin
            };
        },
    },
};

export default Model;
