package com.nttdata.bc19.msmanagementcurrentaccount.request;

import lombok.Data;

import java.util.List;

@Data
public class CurrentAccountBusinessRequest {
    private double amount;
    private String accountNumber;
    private String idBusinessClient;
    private String idPasiveProduct;
    private List<String> holders;
    private List<String> signers;
}
