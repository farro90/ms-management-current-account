package com.nttdata.bc19.msmanagementcurrentaccount.webclient;

import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IServiceWC {
    Mono<PersonClient> findPersonClientById(String id);

    Mono<BusinessClient> findBusinessClientById(String id);

    Mono<PasiveProduct> findPasiveProductById(String id);

    Mono<ActiveProduct> findActiveProductById(String id);

    Flux<CreditCardBusiness> findCreditCardByIdBusinessClient(String idBusinessClient);
    Mono<Long> countCreditCardByIdBusinessClient(String idBusinessClient);
}
