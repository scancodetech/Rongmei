import Axios from 'axios';

Axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';

export const HttpMethod = {
    GET: 'GET',
    POST: 'POST',
    DELETE: 'DELETE',
    PATCH: 'PATCH',
    PUT: 'PUT'
};

// export const API_URL = "http://localhost:3000";
export const API_URL = "http://39.102.36.169:3000";
export const COMMON_URL = "http://39.102.36.169:6789";

export class HttpService {
    _instance;

    constructor(baseUrl) {
        this._instance = Axios.create({
            baseURL: baseUrl
        });
    }

    async get(path, params) {
        try {
            let res = await this._instance.get(path, {
                params: params,
                headers: {
                    'authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            if (res.status < 200 || res.status >= 400) {
                throw new Error(`${res.status}`);
            }
            return res.data;
        } catch (e) {
            throw e;
        }
    }

    async post(path, params) {
        try {
            let res = await this._instance.post(path, params, {
                headers: {
                    'authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            if (res.status < 200 || res.status >= 400) {
                throw new Error(`${res.status}`);
            }
            return res.data;
        } catch (e) {
            throw e;
        }
    }

    async put(path, params) {
        try {
            let res = await this._instance.put(path, params, {
                headers: {
                    'authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            if (res.status < 200 || res.status >= 400) {
                throw new Error(`${res.status}`);
            }
            return res.data;
        } catch (e) {
            throw e;
        }
    }

    async delete(path, params) {
        try {
            let res = await this._instance.delete(path, {
                headers: {
                    'authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            if (res.status < 200 || res.status >= 400) {
                throw new Error(`${res.status}`);
            }
            return res.data;
        } catch (e) {
            throw e;
        }
    }
}

export const http = new HttpService(API_URL);
export const httpCommon = new HttpService(COMMON_URL);
