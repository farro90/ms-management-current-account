package com.nttdata.bc19.msmanagementcurrentaccount.service;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountBusiness;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountBusinessRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IManagementCurrentAccountBusinessService {

    Mono<CurrentAccountBusiness> create(CurrentAccountBusinessRequest currentAccountBusinessRequest);
    Mono<CurrentAccountBusiness> update(CurrentAccountBusiness currentAccountBusiness);
    Mono<Void>deleteById(String id);
    Mono<CurrentAccountBusiness> findById(String id);
    Flux<CurrentAccountBusiness> findAll();
    Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
