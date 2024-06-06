package com.kredsmart.dataaggregator.contract.response;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CreditCardResponseV1 {

    @NonNull
    private UUID creditCardIdReferenceId;
    @NonNull
    private String creditCard;
    @NonNull
    private Double joiningFee;
    @NonNull
    private Double annualRenewalFee;
    @NonNull
    private String networkProvider;
    @NonNull
    private String bank;
    @NonNull
    private List<String> cohorts;
    @NonNull
    private List<String> coBrands;
    private String metaData;

}
