package com.nttdata.bc19.msmanagementcurrentaccount.service;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountPersonRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IManagementCurrentAccountPersonService {

    Mono<CurrentAccountPerson> create(CurrentAccountPersonRequest currentAccountPersonRequest);
    Mono<CurrentAccountPerson> update(CurrentAccountPerson currentAccountPerson);
    Mono<Void>deleteById(String id);
    Mono<CurrentAccountPerson> findById(String id);
    Flux<CurrentAccountPerson> findAll();
    Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber);
    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
