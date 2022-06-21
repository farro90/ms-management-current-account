package com.nttdata.bc19.msmanagementcurrentaccount.model;

import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.BusinessClient;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PasiveProduct;
import com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC.PersonClient;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class CurrentAccountBusiness extends BaseModel{
    private double amount;
    private String accountNumber;
    private LocalDateTime LastTrasactionDate;
    private String idBusinessClient;
    private String idPasiveProduct;
    private BusinessClient businessClient;
    private PasiveProduct pasiveProduct;
    private List<String> holders;
    private List<String> signers;
    private int numberMovements;
    private boolean isPYME;
}
