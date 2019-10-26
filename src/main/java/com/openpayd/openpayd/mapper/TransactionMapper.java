package com.openpayd.openpayd.mapper;

import com.openpayd.openpayd.dto.TransactionDTO;
import com.openpayd.openpayd.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper extends TemplateMapper<Transaction, TransactionDTO> {

    @Mapping(source = "debitAccount.id", target = "debitAccountId")
    @Mapping(source = "creditAccount.id", target = "creditAccountId")
    @Override
    TransactionDTO toDto(Transaction transaction);
}
