package com.openpayd.openpayd.mapper;

import com.openpayd.openpayd.dto.AccountDTO;
import com.openpayd.openpayd.entity.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper extends TemplateMapper<Account, AccountDTO> {

}
