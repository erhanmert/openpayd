package com.openpayd.openpayd.service;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import com.openpayd.openpayd.exception.ClientNotFoundException;

import java.util.List;

public interface AccountService {
    Account save(Long clientId, Account account) throws ClientNotFoundException;

    List<Account> findByClientId(Long clientId) throws ClientNotFoundException;

    Account findById(Long id) throws AccountNotFoundException;
}
