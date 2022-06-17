import {httpCommon} from '../HttpService';

export class RoleServiceImpl {
    async getAllRole() {
        return await httpCommon.get(
            '/role/getAllRole'
        )
    }

    async addRole(requestData){
        return await httpCommon.post(
            'role/add', requestData
        )
    }
}
