package com.openpayd.openpayd.mapper;

import com.openpayd.openpayd.dto.ClientDTO;
import com.openpayd.openpayd.entity.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper extends TemplateMapper<Client, ClientDTO> {

}
