package com.openpayd.openpayd.controller;

import com.openpayd.openpayd.dto.ClientDTO;
import com.openpayd.openpayd.exception.ClientNotFoundException;
import com.openpayd.openpayd.mapper.ClientMapper;
import com.openpayd.openpayd.service.ClientService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All operations with a client will be routed by this controller.
 *
 * @author emert
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    /**
     * @param clientDTO
     * @return
     */
    @PostMapping(value = "/new")
    public ResponseEntity<ClientDTO> save(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientMapper.toDto(clientService.save(clientMapper.toEntity(clientDTO))), HttpStatus.OK);
    }


    /***
     *
     * @return
     * @throws EntityNotFoundException
     */
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() throws EntityNotFoundException {
        return new ResponseEntity<>(clientService.findAll().stream().map(clientMapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    /***
     *
     * @param clientId
     * @return
     * @throws EntityNotFoundException
     */

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable long clientId) throws ClientNotFoundException {
        return new ResponseEntity<>(clientMapper.toDto(clientService.findById(clientId)), HttpStatus.OK);
    }
}
