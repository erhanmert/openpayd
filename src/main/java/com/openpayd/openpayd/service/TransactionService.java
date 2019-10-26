package com.openpayd.openpayd.service;

import com.openpayd.openpayd.entity.Transaction;
import com.openpayd.openpayd.exception.AccountNotFoundException;

import java.util.List;

public interface TransactionService {
    Transaction transfer(Long debitAccountId, Long creditAccountId, Transaction transaction) throws AccountNotFoundException;

    List<Transaction> getTransactions(Long accountId) throws AccountNotFoundException;
}
