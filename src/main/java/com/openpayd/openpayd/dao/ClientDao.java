package com.openpayd.openpayd.dao;

import com.openpayd.openpayd.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Database Access Object for client table.
 * <p/>
 */
@Repository
public interface ClientDao extends JpaRepository<Client, Long> {

}
