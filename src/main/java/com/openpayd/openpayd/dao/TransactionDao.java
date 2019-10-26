package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Database Access Object for transaction table.
 * <p/>
 */
@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCreditAccountEqualsOrDebitAccountEquals(Account account1,Account account2);
}
