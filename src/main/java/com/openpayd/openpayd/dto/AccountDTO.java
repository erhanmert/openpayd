package com.openpayd.openpayd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openpayd.openpayd.domainvalue.AccountType;
import com.openpayd.openpayd.domainvalue.BalanceStatus;

import java.time.LocalDateTime;

public class AccountDTO {

    @JsonIgnore
    private Long id;

    private AccountType accountType;

    private double balance;

    private BalanceStatus balanceStatus;

    private LocalDateTime creationDate;

    private Long clientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BalanceStatus getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(BalanceStatus balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
