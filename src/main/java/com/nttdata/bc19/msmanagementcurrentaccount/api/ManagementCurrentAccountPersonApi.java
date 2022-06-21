package com.nttdata.bc19.msmanagementcurrentaccount.api;

import com.nttdata.bc19.msmanagementcurrentaccount.model.CurrentAccountPerson;
import com.nttdata.bc19.msmanagementcurrentaccount.request.CurrentAccountPersonRequest;
import com.nttdata.bc19.msmanagementcurrentaccount.service.IManagementCurrentAccountPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/current-account/person")
public class ManagementCurrentAccountPersonApi {

    @Autowired
    private IManagementCurrentAccountPersonService managementCurrentAccountPersonService;

    @PostMapping()
    public Mono<CurrentAccountPerson> create(@RequestBody CurrentAccountPersonRequest savingAccountPersonRequest){
        return managementCurrentAccountPersonService.create(savingAccountPersonRequest);
    }

    @PutMapping()
    public Mono<CurrentAccountPerson> update(@RequestBody CurrentAccountPerson savingAccountPerson){
        return managementCurrentAccountPersonService.update(savingAccountPerson);
    }

    @GetMapping()
    public Flux<CurrentAccountPerson> findAll(){
        return managementCurrentAccountPersonService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CurrentAccountPerson> findById(@PathVariable String id){ return managementCurrentAccountPersonService.findById(id); }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteSA(@PathVariable String id){
        return managementCurrentAccountPersonService.deleteById(id);
    }

    @GetMapping("/findByAccountNumber/{accountNumber}")
    public Mono<CurrentAccountPerson> findByAccountNumber(@PathVariable String accountNumber){ return managementCurrentAccountPersonService.findByAccountNumber(accountNumber); }
}
