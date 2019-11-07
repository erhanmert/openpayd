package com.openpayd.openpayd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpayd.openpayd.dto.TransactionDTO;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Transaction;
import com.openpayd.openpayd.mapper.TransactionMapper;
import com.openpayd.openpayd.service.AccountService;
import com.openpayd.openpayd.service.TransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TransactionService.class})
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @Mock
    private Account account;

    @Mock
    private Transaction transaction;

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(transactionService)).build();
    }

    @Test
    public void transferTest() throws Exception {

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setDebitAccountId(1L);
        transactionDTO.setCreditAccountId(2L);
        transactionDTO.setAmount(100d);
        transactionDTO.setId(100L);

        Account debitAccount = new Account();
        Account creditAccount = new Account();
        when(accountService.findById(1L)).thenReturn(debitAccount);
        when(accountService.findById(2L)).thenReturn(creditAccount);
        transaction = transactionMapper.toEntity(transactionDTO);
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);


        when(transactionService.transfer(eq(1L), eq(2L), any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transaction/transfer")
                .content(asJsonString(transactionDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", is(transaction.getAmount())));

    }

    @Test
    public void getTransactionsTest() throws Exception {

        Transaction transaction1 = new Transaction();
        transaction1.setCreditAccount(account);
        transaction1.setAmount(20d);

        Transaction transaction2 = new Transaction();
        transaction2.setDebitAccount(account);
        transaction2.setAmount(30d);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);


        when(transactionService.getTransactions(1L)).thenReturn(transactionList);

        mockMvc.perform(get("/transaction/{accountId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}