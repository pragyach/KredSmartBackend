package com.kredsmart.dataaggregator.entity;

import com.kredsmart.dataaggregator.bank.Bank;
import com.kredsmart.dataaggregator.creditcard.CreditCard;
import com.kredsmart.dataaggregator.networkprovider.NetworkProvider;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardEntity {

    private UUID referenceId;
    private CreditCard creditCard;
    private Bank bank;
    private Double joiningFee;
    private Double annualRenewalFee;
    private NetworkProvider networkProvider;
    private String metaData;

}
