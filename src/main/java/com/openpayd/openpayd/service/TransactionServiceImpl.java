package com.openpayd.openpayd.service;


import com.openpayd.openpayd.dao.TransactionDao;
import com.openpayd.openpayd.domainvalue.BalanceStatus;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Transaction;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some transaction specific things.
 *
 * @author emert
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountService accountService;

//    @Autowired
//    public TransactionServiceImpl(AccountService accountService) {
//        this.accountService = accountService;
//    }

    /***
     *
     * @param debitAccountId
     * @param creditAccountId
     * @param transaction
     * @return
     * @throws AccountNotFoundException
     */
    @Override
    @Transactional
    public Transaction transfer(Long debitAccountId, Long creditAccountId, Transaction transaction) throws AccountNotFoundException {
        Account debitAccount = accountService.findById(debitAccountId);
        Account creditAccount = accountService.findById(creditAccountId);

        if (debitAccount.getBalanceStatus().equals(BalanceStatus.DR)) {
            debitAccount.setBalance(debitAccount.getBalance() + transaction.getAmount());
        } else {
            double newAmount = debitAccount.getBalance() - transaction.getAmount();
            debitAccount = calculator(debitAccount, newAmount);
        }

        if (creditAccount.getBalanceStatus().equals(BalanceStatus.CR)) {
            creditAccount.setBalance(creditAccount.getBalance() + transaction.getAmount());
        } else {
            double newAmount = (creditAccount.getBalance() * -1) + transaction.getAmount();
            creditAccount = calculator(creditAccount, newAmount);
        }
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);

        return transactionDao.save(transaction);
    }

    private Account calculator(Account account, double newAmount) {
        if (newAmount > 0) {
            account.setBalanceStatus(BalanceStatus.CR);
            account.setBalance(newAmount);
        } else {
            account.setBalanceStatus(BalanceStatus.DR);
            account.setBalance(Math.abs(newAmount));
        }

        return account;
    }


    /***
     *
     * @param accountId
     * @return
     * @throws AccountNotFoundException
     */
    @Override
    public List<Transaction> getTransactions(Long accountId) throws AccountNotFoundException {
        Account account = accountService.findById(accountId);
        return transactionDao.findByCreditAccountEqualsOrDebitAccountEquals(account, account);
    }
}
