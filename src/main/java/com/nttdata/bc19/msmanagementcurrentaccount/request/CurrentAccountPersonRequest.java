package com.nttdata.bc19.msmanagementcurrentaccount.request;

import lombok.Data;

@Data
public class CurrentAccountPersonRequest {
    private double amount;
    private String accountNumber;
    private String idPersonClient;
    private String idPasiveProduct;
}
