package com.nttdata.bc19.msmanagementcurrentaccount.service.impl;

import com.nttdata.bc19.msmanagementcurrentaccount.exception.ModelNotFoundException;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PersonClient;
import com.nttdata.bc19.msmanagementcurrentaccount.repository.ICurrentAccountPersonRepository;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountPersonRequest;
import com.nttdata.bc19.msmanagementcurrentaccount.service.IManagementCurrentAccountPersonService;
import com.nttdata.bc19.msmanagementcurrentaccount.util.PasiveProductType;
import com.nttdata.bc19.msmanagementcurrentaccount.webclient.impl.ServiceWCImpl;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ManagementCurrentAccountPersonServiceImpl implements IManagementCurrentAccountPersonService {

    @Autowired
    ICurrentAccountPersonRepository currentAccountPersonRepository;

    @Autowired
    private ServiceWCImpl clientServiceWC;

    @Override
    public Mono<CurrentAccountPerson> create(CurrentAccountPersonRequest currentAccountPersonRequest) {
        return clientServiceWC.findPersonClientById(currentAccountPersonRequest.getIdPersonClient())
                .switchIfEmpty(Mono.error(new Exception()))
                .flatMap(personClientResponse ->
                        clientServiceWC.findPasiveProductById(currentAccountPersonRequest.getIdPasiveProduct())
                                .switchIfEmpty(Mono.error(new Exception()))
                                .flatMap(currentAccountResponse ->
                                        currentAccountPersonRepository.countByIdPersonClientAndIdPasiveProduct(currentAccountPersonRequest.getIdPersonClient(), currentAccountPersonRequest.getIdPasiveProduct())
                                                .switchIfEmpty(Mono.error(new Exception()))
                                                .flatMap(CurrentAccountCountResponse -> this.businessLogicCurrentAccountPerson(personClientResponse, currentAccountResponse, currentAccountPersonRequest, CurrentAccountCountResponse))
                                )
                        );
    }

    @Override
    public Mono<CurrentAccountPerson> update(CurrentAccountPerson currentAccountPerson) {
        currentAccountPerson.setUpdatedAt(LocalDateTime.now());
        return currentAccountPersonRepository.save(currentAccountPerson);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return currentAccountPersonRepository.deleteById(id);
    }

    @Override
    public Mono<CurrentAccountPerson> findById(String id) {
        return currentAccountPersonRepository.findById(id);
    }

    @Override
    public Flux<CurrentAccountPerson> findAll() {
        return currentAccountPersonRepository.findAll();
    }

    @Override
    public Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber) {
        return currentAccountPersonRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<Boolean> existsByAccountNumber(String accountNumber) {
        return currentAccountPersonRepository.existsByAccountNumber(accountNumber);
    }

    private Mono<CurrentAccountPerson> businessLogicCurrentAccountPerson(PersonClient personClient, PasiveProduct currentAccount, CurrentAccountPersonRequest currentAccountPersonRequest, Long currentAccountCount){
        CurrentAccountPerson currentAccountPerson = new CurrentAccountPerson();
        currentAccountPerson.setId(new ObjectId().toString());
        currentAccountPerson.setCreatedAt(LocalDateTime.now());
        currentAccountPerson.setAmount(currentAccountPersonRequest.getAmount());
        currentAccountPerson.setAccountNumber(currentAccountPersonRequest.getAccountNumber()); //Generate number account
        currentAccountPerson.setLastTrasactionDate(LocalDateTime.now());
        currentAccountPerson.setIdPersonClient(currentAccountPersonRequest.getIdPersonClient());
        currentAccountPerson.setIdPasiveProduct(currentAccountPersonRequest.getIdPasiveProduct());
        currentAccountPerson.setPersonClient(personClient);
        currentAccountPerson.setPasiveProduct(currentAccount);
        currentAccountPerson.setNumberMovements(0);

        boolean existsClientPersonPasiveProduct = currentAccountCount > 0 ? true : false;

        if(!currentAccount.getName().equals(PasiveProductType.CORRIENTE.name())){
            return Mono.error(new ModelNotFoundException("The account is not current."));
        }
        if(!currentAccount.getAllowPersonClient()) {
            return Mono.error(new ModelNotFoundException("Type of account not allowed for person client"));
        }
        if(existsClientPersonPasiveProduct) {
            return Mono.error(new ModelNotFoundException("The customer already has an account"));
        }
        if(currentAccount.getMinimumOpeningAmount() > currentAccountPersonRequest.getAmount()) {
            return Mono.error(new ModelNotFoundException("The minimum amount for opening an account is greater than the amount deposited"));
        }
        else{
            return currentAccountPersonRepository.save(currentAccountPerson);
        }
    }
}
