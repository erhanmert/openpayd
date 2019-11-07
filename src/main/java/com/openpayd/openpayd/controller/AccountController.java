package com.openpayd.openpayd.controller;

import com.openpayd.openpayd.dto.AccountDTO;
import com.openpayd.openpayd.exception.ClientNotFoundException;
import com.openpayd.openpayd.mapper.AccountMapper;
import com.openpayd.openpayd.service.AccountService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * All operations with a account will be routed by this controller.
 *
 * @author emert
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);


    /***
     *
     * @param clientId
     * @return
     * @throws ClientNotFoundException
     */
    @GetMapping("/{clientId}")
    public ResponseEntity<List<AccountDTO>> getClientAccounts(@PathVariable long clientId) throws ClientNotFoundException {
        return new ResponseEntity<>(accountService.findByClientId(clientId).stream().map(accountMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }


    /***
     *
     * @param accountDTO
     * @return
     * @throws ClientNotFoundException
     */
    @PostMapping(value = "/new")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO) throws ClientNotFoundException {
        return new ResponseEntity<>(accountMapper.toDto(accountService.save(accountDTO.getClientId(), accountMapper.toEntity(accountDTO))), HttpStatus.CREATED);
    }


}
