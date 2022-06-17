import http from '../HttpService';

export class AccessServiceMock {
    async addAccess(accessAddParameters) {

    }

    async loadAccess() {
        return {
            accessItems: [
                {id: 1, name: "田浩", username: "123", password: "123"},
                {id: 2, name: "刘尧", username: "123", password: "123"},
                {id: 3, name: "蒙蒙", username: "123", password: "123"}
            ]
        }
    }

    async getAccessDetail(id) {
    }
}
