package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
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
public class AccountDaoTest {

    @Mock
    private AccountDao accountDao;

    @Mock
    private Client client;

    @Spy
    List<Account> accounts = new ArrayList<>();

    @Test
    public void saveTest() {
        Account account = new Account();
        account.setBalance(100d);
        account.setId(1L);
        account.setClient(client);
        when(accountDao.save(any(Account.class))).thenReturn(account);
        when(client.getName()).thenReturn("eser");

        Account accountResponse = accountDao.save(account);
        Assert.assertEquals(100d, accountResponse.getBalance(), 0.00);
        Assert.assertEquals(account.getClient().getName(), "eser");
    }

    @Test
    public void findByClientTest() {
        Account account1 = new Account();
        account1.setClient(client);
        account1.setBalance(100d);

        Account account2 = new Account();
        account2.setClient(client);
        account2.setBalance(150d);

        accounts.add(account1);
        accounts.add(account2);

        when(accountDao.findByClientEquals(any(Client.class))).thenReturn(accounts);
        List<Account> accountList = accountDao.findByClientEquals(client);
        Assert.assertEquals(accountList.size(), 2);
    }

}