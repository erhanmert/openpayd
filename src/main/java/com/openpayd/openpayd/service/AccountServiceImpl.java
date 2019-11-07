package com.openpayd.openpayd.service;


import com.openpayd.openpayd.dao.AccountDao;
import com.openpayd.openpayd.dao.ClientDao;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import com.openpayd.openpayd.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some account specific things.
 *
 * @author emert
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private AccountDao accountDao;


    /**
     * Creates a new account.
     *
     * @param clientId
     * @return
     * @throws ClientNotFoundException if a client does not exists with the given clientId
     */

    @Override
    @Transactional
    public Account save(Long clientId, Account account) throws ClientNotFoundException {
        Client client = clientDao.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException("Could not find entity with id: " + clientId));

        account.setClient(client);
        return accountDao.save(account);
    }

    /**
     * Selects a account by clientId.
     *
     * @param clientId
     * @return found accounts
     * @throws ClientNotFoundException if a client does not exists with the given clientId
     */
    @Override
    public List<Account> findByClientId(Long clientId) throws ClientNotFoundException {
        Client client = clientDao.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException("Could not find entity with id: " + clientId));

        return accountDao.findByClientEquals(client);
    }

    /**
     * Selects a account by id.
     *
     * @param id
     * @return found account
     * @throws AccountNotFoundException if a account does not exists with the given id
     */
    @Override
    public Account findById(Long id) throws AccountNotFoundException {
        return accountDao.findById(id).orElseThrow(() -> new AccountNotFoundException(("Could not find entity with id: " + id)));
    }
}
