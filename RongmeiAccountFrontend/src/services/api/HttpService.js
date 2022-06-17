import Axios from 'axios';

Axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';

export const HttpMethod = {
    GET: 'GET',
    POST: 'POST',
    DELETE: 'DELETE',
    PATCH: 'PATCH',
    PUT: 'PUT'
};
export const TCC_SERVICE = "rongmei.tcc.api";
export const COMMON_SERVICE = "rongmei.account.api";
export const MALL_SERVICE = "rongmei.mall.api";

function packDev(service) {
    return `${service.split(".").join("_")}_dev`;
}

function packProd(service) {
    return `${service.split(".").join("_")}_prod`;
}

export class DNSService {
    async get(service) {
        const currHost = window.location.host;
        let serviceName = service;
        if (currHost.indexOf("rongmeitech.com") >= 0 || currHost.indexOf("http://39.102.36.169") >= 0 || currHost.indexOf("localhost") >= 0) {
            serviceName = packDev(service);
        } else if (currHost.indexOf("localhost") >= 0 || currHost.indexOf("dimension.pub") >= 0 || currHost.indexOf("81.70.102.195") >= 0) {
            serviceName = packProd(service);
        }
        try {
            return `https://api.dimension.pub/${serviceName}`;
        } catch (e) {
            throw e;
        }
    }
}

export const httpDNS = new DNSService();

export class HttpService {
    service;

    constructor(service) {
        this.service = service;
    }

    async get(path, params) {
        try {
            const _instance = Axios.create({
                baseURL: await httpDNS.get(this.service)
            });
            let res = await _instance.get(path, {
                params: params,
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
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
            const _instance = Axios.create({
                baseURL: await httpDNS.get(this.service)
            });
            let res = await _instance.post(path, params, {
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
            const _instance = Axios.create({
                baseURL: await httpDNS.get(this.service)
            });
            let res = await _instance.put(path, params, {
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
            const _instance = Axios.create({
                baseURL: await httpDNS.get(this.service)
            });
            let res = await _instance.delete(path, {
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

export const httpCommon = new HttpService(COMMON_SERVICE);
export const httpMall = new HttpService(MALL_SERVICE);
export const tccCall = new HttpService(TCC_SERVICE);
