import {tccCall} from '../HttpService';

export class TCCServiceImpl {
  async getTCC(key){
    return await tccCall.get(`/tcc/key?key=${key}`);
  }
}
