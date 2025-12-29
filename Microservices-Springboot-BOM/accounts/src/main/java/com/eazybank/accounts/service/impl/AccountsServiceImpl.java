package com.eazybank.accounts.service.impl;

import com.eazybank.accounts.constants.AccountsConstants;
import com.eazybank.accounts.dto.AccountsDto;
import com.eazybank.accounts.dto.AccountsEventDto;
import com.eazybank.accounts.dto.AccountsMsgDto;
import com.eazybank.accounts.dto.CustomerDto;
import com.eazybank.accounts.entity.Accounts;
import com.eazybank.accounts.entity.Customer;
import com.eazybank.accounts.exception.CustomerAlreadyExistsException;
import com.eazybank.accounts.exception.ResourceNotFoundException;
import com.eazybank.accounts.mapper.AccountsMapper;
import com.eazybank.accounts.mapper.CustomerMapper;
import com.eazybank.accounts.repository.AccountsRepository;
import com.eazybank.accounts.repository.CustomerRepository;
import com.eazybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private StreamBridge streamBridge;

    private static final Logger logger= LoggerFactory.getLogger(AccountsServiceImpl.class);

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());

       Optional<Customer> optionalCustomer= customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number"+customerDto.getMobileNumber());
        }
//        customer.setCreatedAt(LocalDateTime.now());
//        customer.setCreatedBy("Anonymous");
        Customer savedCustomer=customerRepository.save(customer);

        Accounts savedAccount=accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);
        publishAccountEvent(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        logger.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        logger.info("Is the Communication request successfully triggered ? : {}", result);
    }

    private void publishAccountEvent(Accounts account, Customer customer) {
        // Create the DTO using the new record structure
        AccountsEventDto eventDto = new AccountsEventDto(
                account.getAccountNumber(),
                "ACCOUNT_CREATED",
                customer.getMobileNumber(),
                customer.getEmail(),
                Instant.now() // Use java.time.Instant for a precise, standardized timestamp
        );

        logger.info("Publishing ACCOUNT_CREATED event to Kafka: {}", eventDto);

        // Use the binding name defined for the Kafka output channel in application.yml
        // The binding name is 'publishAccountEvent-out-0'
        var result = streamBridge.send("publishAccountEvent-out-0", eventDto);
        logger.info("Is the Kafka event successfully published? : {}", result);
    }



    private Accounts createNewAccount(Customer customer){
        Accounts newAccount=new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumbeer=1000000000L+new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumbeer);
        newAccount.setAccountType(AccountsConstants.SAVINGS);

        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//       newAccount.setCreatedAt(LocalDateTime.now());
//       newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","Mobilenumber",mobileNumber)
        );

        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(

                ()->new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );

        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account","Account Number",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accountsRepository.save(accounts);
            Long customerId=accounts.getCustomerId();
            Customer customer=customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("Customer","CustomerID",customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated=true;

        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted=false;
        Customer customer=customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)

        );
        if(customer!=null){

            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            customerRepository.deleteById(customer.getCustomerId());

            isDeleted=true;
        }
        return isDeleted;
    }



    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
    }



}
