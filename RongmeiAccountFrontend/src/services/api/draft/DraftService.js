import {httpMall} from '../HttpService';

export class DraftServiceImpl {
    async reviewDraft(relationId, draftType, isApprove, msg) {
        return await httpMall.post(
            `/draft`, {
                relationId,
                draftType,// 0:commodity 1:sale 2:blindbox
                isApprove,
                msg
            }
        )
    }

    async getDraftCommodity() {
        return await httpMall.get(
            `/draft/commodity`
        )
    }

    async getDraftCommodityDetail(relationId) {
        return await httpMall.get(
            `/draft/commodity/${relationId}`
        )
    }

    async getDraftSale() {
        return await httpMall.get(
            `/draft/sale`
        )
    }

    async getDraftSaleDetail(relationId) {
        return await httpMall.get(
            `/draft/sale/${relationId}`
        )
    }

    async getDraftBlindBoxSale() {
        return await httpMall.get(
            `/draft/blindboxsale`
        )
    }

    async getDraftBlindBoxSaleDetail(relationId) {
        return await httpMall.get(
            `/draft/blindboxsale/${relationId}`
        )
    }

    async getDraftProposal() {
        return await httpMall.get(
            `/draft/proposal`
        )
    }

    async getDraftProposalDetail(relationId) {
        return await httpMall.get(
            `/draft/proposal/${relationId}`
        )
    }

    async getDraftGroupShopping() {
        return await httpMall.get(
            `/draft/groupshopping`
        )
    }

    async getDraftGroupShoppingDetail(relationId) {
        return await httpMall.get(
            `/draft/groupshopping/${relationId}`
        )
    }

    async getDraftChild() {
        return await httpMall.get(
            `/draft/child`
        )
    }

    async getDraftChildDetail(relationId) {
        return await httpMall.get(
            `/draft/child/${relationId}`
        )
    }
}
