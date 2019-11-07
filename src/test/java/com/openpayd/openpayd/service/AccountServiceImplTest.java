package com.openpayd.openpayd.service;

import com.openpayd.openpayd.dao.AccountDao;
import com.openpayd.openpayd.dao.ClientDao;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import com.openpayd.openpayd.exception.ClientNotFoundException;
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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    @TestConfiguration
    static class AccountServiceImplTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountServiceImpl();
        }
    }

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountDao accountDao;

    @MockBean
    private ClientDao clientDao;

    @Mock
    private Client client;

    @Mock
    private Account account;

    @Spy
    private List<Account> accountList = new ArrayList<>();


    @Test
    public void saveTest() throws ClientNotFoundException {
        Account account = new Account();
        account.setBalance(100d);
        account.setClient(client);
        when(clientDao.findById(2L)).thenReturn(java.util.Optional.ofNullable(client));
        when(accountDao.save(account)).thenReturn(account);
        when(accountService.save(2L, account)).thenReturn(account);
        Account accountResponse = accountService.save(2L, account);
        Assert.assertEquals(100d, accountResponse.getBalance(), 0.00);
    }

    @Test
    public void findByClientIdTest() throws ClientNotFoundException {
        Account account1 = new Account();
        account1.setBalance(100d);
        account1.setClient(client);
        Account account2 = new Account();
        account2.setBalance(200d);
        account2.setClient(client);
        accountList.add(account1);
        accountList.add(account2);
        when(client.getId()).thenReturn(2L);

        when(clientDao.findById(2L)).thenReturn(java.util.Optional.ofNullable(client));

        when(accountService.findByClientId(2L)).thenReturn(accountList);
        List<Account> responseAccount = accountService.findByClientId(2L);
        Assert.assertEquals(responseAccount.size(), 2);
        Assert.assertEquals(responseAccount.get(0).getClient().getId().longValue(), 2L);
    }

    @Test
    public void findByIdTest() throws ClientNotFoundException, AccountNotFoundException {
        when(account.getBalance()).thenReturn(100d);
        when(account.getId()).thenReturn(1L);
        when(account.getClient()).thenReturn(client);
        when(client.getId()).thenReturn(2L);
        when(accountDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(account));
        Account responseAccount = accountService.findById(1L);
        Assert.assertEquals(responseAccount.getBalance(), 100d, 0.00);
        Assert.assertEquals(responseAccount.getClient().getId().longValue(), 2L);
    }

    @Test(expected = AccountNotFoundException.class)
    public void findByIdNotFoundTest() throws AccountNotFoundException {
        accountService.findById(3L);
    }

}