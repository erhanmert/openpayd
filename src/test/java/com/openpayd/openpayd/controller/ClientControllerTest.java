package com.openpayd.openpayd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpayd.openpayd.entity.Client;
import com.openpayd.openpayd.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ClientService.class})
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Mock
    private Client client;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService)).build();
    }

    @Test
    public void saveTest() throws Exception {
        Client client = new Client();
        client.setName("erhan");

        when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/client/new")
                .content(asJsonString(client))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(client.getName())));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllClientsTest() throws Exception {
        Client client1 = new Client();
        client1.setName("eser");
        Client client2 = new Client();
        client2.setName("aziz");
        List<Client> clientList = new ArrayList<>();
        clientList.add(client1);
        clientList.add(client2);

        when(clientService.findAll()).thenReturn(clientList);

        mockMvc.perform(get("/client")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getClientTest() throws Exception {
        when(client.getName()).thenReturn("eser");
        when(client.getId()).thenReturn(1L);
        when(clientService.findById(1L)).thenReturn(client);

        mockMvc.perform(get("/client/{clientId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(client.getName())));
    }
}