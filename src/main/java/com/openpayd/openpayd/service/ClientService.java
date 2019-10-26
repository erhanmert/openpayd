package com.openpayd.openpayd.service;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.exception.ClientNotFoundException;

import java.util.List;

public interface ClientService {
    Client save(Client client);

    List<Client> findAll();

    Client findById(Long id) throws ClientNotFoundException;

    Client addAccount(Long clientId, Account account) throws ClientNotFoundException;
}
