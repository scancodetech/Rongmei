package com.rongmei.response.finance;

import com.rongmei.response.Response;

import java.util.List;

public class FinanceResponse extends Response {

    private List<FinanceF> returnList;
    private Integer page;
    private Integer size;
    private String income;

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    private String expenditure;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public FinanceResponse() {
    }

    public FinanceResponse(List<FinanceF> returnList, Integer page, Integer size,String income,String expenditure) {
        this.returnList = returnList;
        this.page = page;
        this.size = size;
        this.income = income;
        this.expenditure = expenditure;
    }



    public List<FinanceF> getReturnList() {
        return returnList;
    }

    public void setReturnList(List<FinanceF> returnList) {
        this.returnList = returnList;
    }
}
