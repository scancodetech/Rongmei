import {httpCommon} from '../HttpService';

export class UserGroupServiceImpl {
    async getUserGroups() {
        return await httpCommon.get(
            '/user_group'
        )
    }

    async deleteUserGroup(userGroupId) {
        return await httpCommon.delete(
            `/user_group?userGroupId=${userGroupId}`
        )
    }

    async updateUserGroup(userGroupData) {
        return await httpCommon.post(
            `/user_group`, userGroupData
        )
    }

    async getUserGroup(userGroupId) {
        return await httpCommon.get(
            `/user_group/${userGroupId}`
        )
    }

    async updateUserGroupPrice(userGroupId, price) {
        return await httpCommon.get(
            `/user_group?userGroupId=${userGroupId}&price=${price}`
        )
    }

    async getUserGroupByUsername(username) {
        return await httpCommon.get(
            `/user_group?username=${username}`
        )
    }
}
