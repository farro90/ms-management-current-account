package com.nttdata.bc19.msmanagementcurrentaccount.repository;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICurrentAccountPersonRepository extends ReactiveMongoRepository<CurrentAccountPerson, String> {
    Flux<CurrentAccountPerson> findByIdPersonClient(String id);

    Mono<Long> countByIdPersonClient(String id);

    Flux<CurrentAccountPerson> findByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<Long> countByIdPersonClientAndIdPasiveProduct(String idPersonClient, String idPasiveProduct);

    Mono<CurrentAccountPerson> findByAccountNumber(String accountNumber);

    Mono<Boolean> existsByAccountNumber(String accountNumber);
}
