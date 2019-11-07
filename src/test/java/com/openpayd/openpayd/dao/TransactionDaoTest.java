package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionDaoTest {

    @Mock
    private TransactionDao transactionDao;

    @Mock
    private Account debitAccount;

    @Mock
    private Account creditAccount;

    @Spy
    private List<Transaction> transactions = new ArrayList<>();


    @Test
    public void saveTest() {
        Transaction transaction = new Transaction();
        transaction.setCreditAccount(creditAccount);
        transaction.setDebitAccount(debitAccount);
        transaction.setAmount(100d);
        when(transactionDao.save(any(Transaction.class))).thenReturn(transaction);
        Transaction transactionResponse = transactionDao.save(transaction);

        Assert.assertEquals(transactionResponse.getAmount(), 100d, 0.00);
    }

    @Test
    public void findByCreditAccountEqualsOrDebitAccountEqualsTest() {
        Transaction transaction = new Transaction();
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transaction.setAmount(100d);
        transactions.add(transaction);
        when(transactionDao.findByCreditAccountEqualsOrDebitAccountEquals(any(Account.class), any(Account.class))).thenReturn(transactions);
        List<Transaction> transactionResponse = transactionDao.findByCreditAccountEqualsOrDebitAccountEquals(debitAccount, debitAccount);

        Assert.assertEquals(transactionResponse.size(), 1);
        Assert.assertEquals(transactionResponse.get(0).getAmount(), 100d, 0.00);

    }

}