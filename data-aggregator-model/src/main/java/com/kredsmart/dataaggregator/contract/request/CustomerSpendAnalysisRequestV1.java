package com.kredsmart.dataaggregator.contract.request;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CustomerSpendAnalysisRequestV1 {

    @NonNull
    private UUID customerReferenceId;

    @NonNull
    private Map<String, List<String>> cohortToCoBrandMap;

}
