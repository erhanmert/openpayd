package com.openpayd.openpayd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpayd.openpayd.dao.ClientDao;
import com.openpayd.openpayd.dto.AccountDTO;
import com.openpayd.openpayd.entity.Account;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.mapper.AccountMapper;
import com.openpayd.openpayd.service.AccountService;
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
@ContextConfiguration(classes = {AccountService.class})
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Mock
    private Client client;

    @Mock
    private ClientDao clientDao;

    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
    }

    @Test
    public void getClientAccountsTest() throws Exception {
        Account account1 = new Account();
        account1.setBalance(100d);
        Account account2 = new Account();
        account2.setBalance(100d);
        Client client = new Client();
        client.setId(1L);
        client.setName("eser");
        account1.setClient(client);
        account2.setClient(client);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account1);
        accountList.add(account2);

        when(accountService.findByClientId(1L)).thenReturn(accountList);

        mockMvc.perform(get("/account/{clientId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

    }

    @Test
    public void addAccountTest() throws Exception {

        AccountDTO accountDto = new AccountDTO();
        accountDto.setClientId(1L);
        accountDto.setBalance(100d);


        when(accountService.save(eq(1L), any(Account.class))).thenReturn(accountMapper.toEntity(accountDto));
        when(clientDao.findById(1L)).thenReturn(java.util.Optional.ofNullable(client));

        mockMvc.perform(post("/account/new")
                .content(asJsonString(accountDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance", is(100d)));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}