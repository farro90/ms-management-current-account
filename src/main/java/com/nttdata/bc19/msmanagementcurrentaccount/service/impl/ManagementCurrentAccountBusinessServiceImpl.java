package com.nttdata.bc19.msmanagementcurrentaccount.service.impl;

import com.nttdata.bc19.msmanagementcurrentaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountBusiness;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.BusinessClient;
import com.nttdata.bc19.msmanagementcurrentaccount.repository.ICurrentAccountBusinessRepository;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountBusinessRequest;
import com.nttdata.bc19.msmanagementcurrentaccount.service.IManagementCurrentAccountBusinessService;
import com.nttdata.bc19.msmanagementcurrentaccount.util.PasiveProductType;
import com.nttdata.bc19.msmanagementcurrentaccount.webclient.impl.ServiceWCImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ManagementCurrentAccountBusinessServiceImpl implements IManagementCurrentAccountBusinessService {

    @Autowired
    ICurrentAccountBusinessRepository currentAccountBusinessRepository;

    @Autowired
    private ServiceWCImpl clientServiceWC;

    @Override
    public Mono<CurrentAccountBusiness> create(CurrentAccountBusinessRequest currentAccountBusinessRequest) {
        return clientServiceWC.findBusinessClientById(currentAccountBusinessRequest.getIdBusinessClient())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(BusinessClientResponse ->
                        clientServiceWC.findPasiveProductById(currentAccountBusinessRequest.getIdPasiveProduct())
                                .switchIfEmpty(Mono.error(new Exception()))
                                .flatMap(currentAccountResponse ->
                                        clientServiceWC.countCreditCardByIdBusinessClient(currentAccountBusinessRequest.getIdBusinessClient())
                                                .switchIfEmpty(Mono.error(new Exception()))
                                                .flatMap(countCreditCardResponse ->
                                                        this.businessLogicCurrentAccountBusiness(BusinessClientResponse, currentAccountResponse, currentAccountBusinessRequest, countCreditCardResponse))
                                )
                );
    }

    @Override
    public Mono<CurrentAccountBusiness> update(CurrentAccountBusiness currentAccountBusiness) {
        currentAccountBusiness.setUpdatedAt(LocalDateTime.now());
        return currentAccountBusinessRepository.save(currentAccountBusiness);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return currentAccountBusinessRepository.deleteById(id);
    }

    @Override
    public Mono<CurrentAccountBusiness> findById(String id) {
        return currentAccountBusinessRepository.findById(id);
    }

    @Override
    public Flux<CurrentAccountBusiness> findAll() {
        return currentAccountBusinessRepository.findAll();
    }

    @Override
    public Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber) {
        return currentAccountBusinessRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<Boolean> existsByAccountNumber(String accountNumber) {
        return currentAccountBusinessRepository.existsByAccountNumber(accountNumber);
    }

    private Mono<CurrentAccountBusiness> businessLogicCurrentAccountBusiness(BusinessClient businessClient, PasiveProduct currentAccount, CurrentAccountBusinessRequest currentAccountBusinessRequest, Long creditCardCount){
        CurrentAccountBusiness currentAccountBusiness = new CurrentAccountBusiness();
        currentAccountBusiness.setId(new ObjectId().toString());
        currentAccountBusiness.setCreatedAt(LocalDateTime.now());
        currentAccountBusiness.setAmount(currentAccountBusinessRequest.getAmount());
        currentAccountBusiness.setAccountNumber(currentAccountBusinessRequest.getAccountNumber()); //Generate number account
        currentAccountBusiness.setLastTrasactionDate(LocalDateTime.now());
        currentAccountBusiness.setIdBusinessClient(currentAccountBusinessRequest.getIdBusinessClient());
        currentAccountBusiness.setIdPasiveProduct(currentAccountBusinessRequest.getIdPasiveProduct());
        currentAccountBusiness.setBusinessClient(businessClient);
        currentAccountBusiness.setPasiveProduct(currentAccount);
        currentAccountBusiness.setHolders(currentAccountBusinessRequest.getHolders());
        currentAccountBusiness.setSigners(currentAccountBusinessRequest.getSigners());
        currentAccountBusiness.setNumberMovements(0);
        currentAccountBusiness.setPYME(false);

        if(creditCardCount > 0){
            currentAccountBusiness.setPYME(true);
        }

        if(!currentAccount.getName().equals(PasiveProductType.CORRIENTE.name())){
            return Mono.error(new ModelNotFoundException("The account is not current."));
        }
        if(currentAccount.getAllowBusinessClient()){
            return Mono.error(new ModelNotFoundException("Type of account not allowed for business client."));
        }
        if(!currentAccountBusiness.getHolders().isEmpty()){
            return Mono.error(new ModelNotFoundException("Must enter at least one holder."));
        }
        if(currentAccount.getMinimumOpeningAmount() > currentAccountBusinessRequest.getAmount()) {
            return Mono.error(new ModelNotFoundException("The minimum amount for opening this account is greater than the amount deposited"));
        }
        else{
            return currentAccountBusinessRepository.save(currentAccountBusiness);
        }
    }
}
