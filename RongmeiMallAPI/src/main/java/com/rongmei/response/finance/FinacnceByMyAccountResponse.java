package com.rongmei.response.finance;

import com.rongmei.response.Response;

import java.util.List;

public class FinacnceByMyAccountResponse extends Response {
    private List<Finacnce2UserAccount> financeList;
    private double income;
    private double expenditure;

    public List<Finacnce2UserAccount> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(List<Finacnce2UserAccount> financeList) {
        this.financeList = financeList;
    }

    public FinacnceByMyAccountResponse(List<Finacnce2UserAccount> financeList) {
        this.financeList = financeList;
        double income = 0.0;
        double expenditure = 0.0;
        for (Finacnce2UserAccount finacnce2UserAccount : financeList) {
            if(finacnce2UserAccount.getType() == 0){
                income += finacnce2UserAccount.getCount();
            }else {
                expenditure += finacnce2UserAccount.getCount();
            }
        }
        this.income = income;
        this.expenditure = expenditure;
    }
}
