package com.nttdata.bc19.msmanagementcurrentaccount.model.responseWC;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
public class CreditCardBusiness {
    private String id;
    private String creditCardNumber;
    private double creditLine;
    private double amountConsumed;
    private double minimumPayment;
    private int AnnualCommission;
    private int cutoffDate;
    private int payLimitDate;
    private LocalDateTime openingDate;
    private LocalDateTime deliveryDate;
    private String idBusinessClient;
    private String idActiveProduct;
    private BusinessClient businessClient;
    private ActiveProduct activeProduct;
}
