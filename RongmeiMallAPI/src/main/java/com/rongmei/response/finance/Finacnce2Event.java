package com.rongmei.response.finance;

import java.math.BigDecimal;

public class Finacnce2Event extends FinanceF {


    private BigDecimal price;

    public Finacnce2Event(BigDecimal price) {
        this.price = price;
    }


    public Finacnce2Event() {

    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
