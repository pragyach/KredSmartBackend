package com.kredsmart.dataaggregator.contract.response;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CustomerSpendAnalysisResponseV1 {

    @NonNull
    private UUID customerReferenceId;

    @NonNull
    private Map<String, Map<String, List<CreditCardResponseV1>>> eligibleCreditCards;

}
