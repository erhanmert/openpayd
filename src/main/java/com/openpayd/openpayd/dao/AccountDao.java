package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Database Access Object for account table.
 * <p/>
 */
@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
    List<Account> findByClientEquals(Client client);
}
