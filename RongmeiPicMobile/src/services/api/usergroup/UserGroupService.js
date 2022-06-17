import {http} from '../HttpService';

export class UserGroupServiceImpl {
    async getUserGroups(userGroupData) {
        return await http.get(
            '/user_group', userGroupData
        )
    }

    async getUserGroup(userGroupId) {
        return await http.get(
            `/user_group/${userGroupId}`
        )
    }
}
