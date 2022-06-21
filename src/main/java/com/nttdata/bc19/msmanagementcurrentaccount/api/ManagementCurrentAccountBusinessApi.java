package com.nttdata.bc19.msmanagementcurrentaccount.api;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountBusiness;
import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountBusinessRequest;
import com.nttdata.bc19.msmanagementcurrentaccount.service.IManagementCurrentAccountBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/current-account/business")
public class ManagementCurrentAccountBusinessApi {

    @Autowired
    private IManagementCurrentAccountBusinessService managementCurrentAccountBusinessService;

    @PostMapping()
    public Mono<CurrentAccountBusiness> create(@RequestBody CurrentAccountBusinessRequest currentAccountBusinessRequest){
        return managementCurrentAccountBusinessService.create(currentAccountBusinessRequest);
    }

    @PutMapping()
    public Mono<CurrentAccountBusiness> update(@RequestBody CurrentAccountBusiness currentAccountBusiness){
        return managementCurrentAccountBusinessService.update(currentAccountBusiness);
    }

    @GetMapping()
    public Flux<CurrentAccountBusiness> findAll(){
        return managementCurrentAccountBusinessService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CurrentAccountBusiness> findById(@PathVariable String id){ return managementCurrentAccountBusinessService.findById(id); }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteSA(@PathVariable String id){
        return managementCurrentAccountBusinessService.deleteById(id);
    }

    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<CurrentAccountPerson> findByAccountNumber(@PathVariable String accountNumber){ return managementCurrentAccountBusinessService.findByAccountNumber(accountNumber); }
}
