package com.openpayd.openpayd.controller;

import com.openpayd.openpayd.dto.TransactionDTO;
import com.openpayd.openpayd.exception.AccountNotFoundException;
import com.openpayd.openpayd.mapper.TransactionMapper;
import com.openpayd.openpayd.service.TransactionService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All operations with a transaction will be routed by this controller.
 *
 * @author emert
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);


    /**
     * @param transactionDTO
     * @return
     * @throws AccountNotFoundException
     */
    @PostMapping(value = "/transfer")
    public ResponseEntity<TransactionDTO> transfer(@RequestBody TransactionDTO transactionDTO) throws AccountNotFoundException {
        return new ResponseEntity<>(transactionMapper.toDto(transactionService.transfer(transactionDTO.getDebitAccountId(), transactionDTO.getCreditAccountId(), transactionMapper.toEntity(transactionDTO))), HttpStatus.OK);
    }

    /**
     * @param accountId
     * @return
     * @throws AccountNotFoundException
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@PathVariable long accountId) throws AccountNotFoundException {
        return new ResponseEntity<>(transactionService.getTransactions(accountId).stream().map(transactionMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

}
