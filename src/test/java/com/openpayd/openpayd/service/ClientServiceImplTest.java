package com.openpayd.openpayd.service;

import com.openpayd.openpayd.dao.ClientDao;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClientServiceImplTest {

    @TestConfiguration
    static class ClientServiceImplTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientServiceImpl();
        }
    }

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientDao clientDao;

    @Mock
    private Client client;

    @Mock
    private Account account;

    @Spy
    private List<Account> accountList = new ArrayList<>();


    @Test
    public void save() {
        Client client = new Client();
        client.setName("eser");
        when(clientService.save(any(Client.class))).thenReturn(client);
        Client clientResp = clientService.save(client);
        Assert.assertEquals("eser", clientResp.getName());
    }

    @Test
    public void findAll() {
        List<Client> clientList = new ArrayList<>();
        Client client1 = new Client();
        Client client2 = new Client();
        clientList.add(client1);
        clientList.add(client2);
        when(clientDao.findAll()).thenReturn(clientList);
        Assert.assertEquals(clientService.findAll().size(), 2);
    }

    @Test
    public void findByIdTest() throws ClientNotFoundException {
        when(client.getName()).thenReturn("eser");
        when(client.getId()).thenReturn(1L);
        when(clientDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(client));
        Assert.assertEquals(clientService.findById(1L).getName(), "eser");
    }

    @Test(expected = ClientNotFoundException.class)
    public void findByIdNotFoundTest() throws ClientNotFoundException {
        when(client.getName()).thenReturn("eser");
        when(client.getId()).thenReturn(1L);
        when(clientDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(client));
        client = clientService.findById(2L);
    }

    @Test
    public void addAccountTest() throws ClientNotFoundException {
        when(clientDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(client));
        when(account.getBalance()).thenReturn(Double.valueOf(100));
        when(client.getAccountList()).thenReturn(accountList);
        when(clientService.addAccount(1L, account)).thenReturn(client);

        Assert.assertEquals(1, client.getAccountList().size());
        Assert.assertEquals(client.getAccountList().get(0).getBalance(), 100d, 0.00);
    }

    @Test(expected = ClientNotFoundException.class)
    public void addAccountClientNotFoundTest() throws ClientNotFoundException {
        when(clientDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(client));
        when(clientService.addAccount(2L, account)).thenReturn(client);

    }
}