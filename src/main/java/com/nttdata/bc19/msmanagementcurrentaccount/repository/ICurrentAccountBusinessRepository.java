package com.nttdata.bc19.msmanagementcurrentaccount.repository;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountBusiness;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICurrentAccountBusinessRepository extends ReactiveMongoRepository<CurrentAccountBusiness, String> {
    Flux<CurrentAccountBusiness> findByIdBusinessClient(String id);

    Mono<Long> countByIdBusinessClient(String id);

    Flux<CurrentAccountBusiness> findByIdBusinessClientAndIdPasiveProduct(String idBusinessClient, String idPasiveProduct);

    Mono<Long> countByIdBusinessClientAndIdPasiveProduct(String idBusinessClient, String idPasiveProduct);

    Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
