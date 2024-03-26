package com.lambda;

import java.math.BigDecimal;

public class BankResponse {
    private BigDecimal quota;//monto
    private Integer term;//plazo
    private BigDecimal rate;//tasa

    private BigDecimal quotaWithAccount;
    private BigDecimal rateWithAccount; //restar 0.2 a la tasa de interes
    private Integer termWithAccount;

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getQuotaWithAccount() {
        return quotaWithAccount;
    }

    public void setQuotaWithAccount(BigDecimal quotaWithAccount) {
        this.quotaWithAccount = quotaWithAccount;
    }

    public BigDecimal getRateWithAccount() {
        return rateWithAccount;
    }

    public void setRateWithAccount(BigDecimal rateWithAccount) {
        this.rateWithAccount = rateWithAccount;
    }

    public Integer getTermWithAccount() {
        return termWithAccount;
    }

    public void setTermWithAccount(Integer termWithAccount) {
        this.termWithAccount = termWithAccount;
    }
}
