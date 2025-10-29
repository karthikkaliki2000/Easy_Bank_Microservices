package com.eazybank.accounts.service;

import com.eazybank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     *
     * @param customerDto-CustomerDto Object receiving
     */
    void createAccount(CustomerDto customerDto);
}
