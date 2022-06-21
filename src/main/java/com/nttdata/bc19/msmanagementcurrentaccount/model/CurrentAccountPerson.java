package com.nttdata.bc19.msmanagementcurrentaccount.model;

import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PersonClient;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class CurrentAccountPerson extends BaseModel{
    private double amount;
    private String accountNumber;
    private LocalDateTime LastTrasactionDate;
    private String idPersonClient;
    private String idPasiveProduct;
    private PersonClient personClient;
    private PasiveProduct pasiveProduct;
    private int numberMovements;
}
