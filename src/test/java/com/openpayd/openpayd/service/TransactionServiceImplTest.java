package com.openpayd.openpayd.service;

import com.openpayd.openpayd.dao.TransactionDao;
import com.openpayd.openpayd.domainvalue.BalanceStatus;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Transaction;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {

    @TestConfiguration
    static class TransactionServiceImplTestContextConfiguration {

        @Bean
        public TransactionService transactionService() {
            return new TransactionServiceImpl();
        }
    }

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionDao transactionDao;

    @Mock
    private Account account;

    @Spy
    private Transaction transaction = new Transaction();

    @Spy
    private List<Transaction> transactionList = new ArrayList<>();


    @Test
    public void getTransactionsTest() throws AccountNotFoundException {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100d);
        transaction1.setCreditAccount(account);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(100d);
        transaction2.setDebitAccount(account);

        transactionList.add(transaction1);
        transactionList.add(transaction2);
        when(accountService.findById(2L)).thenReturn(account);
        when(account.getId()).thenReturn(1L);
        when(transactionDao.findByCreditAccountEqualsOrDebitAccountEquals(any(Account.class), any(Account.class))).thenReturn(transactionList);
        List<Transaction> transactionResp = transactionService.getTransactions(2L);
        Assert.assertEquals(transactionResp.size(), 2L);
    }

    @Test
    public void transferTest() throws AccountNotFoundException {

        Account debitAccount = new Account();
        debitAccount.setId(1L);
        debitAccount.setBalance(100d);
        debitAccount.setBalanceStatus(BalanceStatus.CR);

        Account creditAccount = new Account();
        creditAccount.setId(1L);
        creditAccount.setBalance(100d);
        creditAccount.setBalanceStatus(BalanceStatus.CR);

        when(transaction.getAmount()).thenReturn(20d);

        when(accountService.findById(1L)).thenReturn(debitAccount);
        when(accountService.findById(2L)).thenReturn(creditAccount);

        when(transactionDao.save(any(Transaction.class))).thenReturn(transaction);
        Transaction transactionResp = transactionService.transfer(1L, 2L, transaction);
        Assert.assertEquals(transactionResp.getCreditAccount().getBalance(), 120d, 0.00);
        Assert.assertEquals(transactionResp.getDebitAccount().getBalance(), 80d, 0.00);
    }

    @Test(expected = AccountNotFoundException.class)
    public void transferAccountNotFoundTest() throws AccountNotFoundException {
        when(accountService.findById(3L)).thenThrow(AccountNotFoundException.class);
        transactionService.transfer(3L, 4L, transaction);

    }
}