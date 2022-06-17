package com.rongmei.response.finance;

import com.rongmei.response.Response;

public class FinanceByPersonAccount extends Response {

    private Finacnce2UserAccount finacnce2UserAccount;

    public FinanceByPersonAccount(Finacnce2UserAccount finacnce2UserAccount) {
        this.finacnce2UserAccount = finacnce2UserAccount;
    }

    public Finacnce2UserAccount getFinacnce2UserAccount() {
        return finacnce2UserAccount;
    }

    public void setFinacnce2UserAccount(Finacnce2UserAccount finacnce2UserAccount) {
        this.finacnce2UserAccount = finacnce2UserAccount;
    }
}
