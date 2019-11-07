package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Client;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class ClientDaoTest {

    @Mock
    private ClientDao clientDao;

    @Test
    public void saveTest() {
        Client client = new Client();
        client.setName("name");
        client.setId(1L);
        when(clientDao.save(any(Client.class))).thenReturn(client);

        Client clientResponse = clientDao.save(client);
        Assert.assertEquals("name", clientResponse.getName());
    }

    @Test
    public void findAllTest() {
        Client client1 = new Client();
        client1.setName("eser");
        client1.setId(1L);

        Client client2 = new Client();
        client2.setName("erhan");
        client2.setId(2L);

        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);
        when(clientDao.findAll()).thenReturn(clientList);

        List<Client> clientResponse = clientDao.findAll();
        Assert.assertEquals(2, clientResponse.size());
    }

}