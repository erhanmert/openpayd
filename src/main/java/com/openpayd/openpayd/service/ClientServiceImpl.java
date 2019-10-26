package com.openpayd.openpayd.service;


import com.openpayd.openpayd.dao.ClientDao;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.exception.ClientNotFoundException;
import com.openpayd.openpayd.mapper.ClientMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some client specific things.
 *
 * @author emert
 */
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDao clientDao;
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Autowired
    public ClientServiceImpl(ClientDao clientDao) {
        this.clientDao = clientDao;
    }


    /***
     *
     * @param client
     * @return
     */
    @Override
    @Transactional
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    /***
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Client findById(Long id) throws ClientNotFoundException{
        Optional<Client>  client = clientDao.findById(id);
        if(!client.isPresent()){
            throw new ClientNotFoundException("Could not find entity with id: " + id);
        }
        return client.get();
    }

    /***
     *
     * @param clientId
     * @param account
     * @return
     */

    @Override
    @Transactional
    public Client addAccount(Long clientId, Account account) throws ClientNotFoundException {
        Client client = findById(clientId);
        account.setClient(client);
        client.getAccountList().add(account);
        return clientDao.save(client);
    }

}
