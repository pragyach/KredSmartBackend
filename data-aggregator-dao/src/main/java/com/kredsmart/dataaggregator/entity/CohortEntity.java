package com.kredsmart.dataaggregator.entity;

import com.kredsmart.dataaggregator.cohert.Cohort;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CohortEntity {

    private UUID referenceId;
    private UUID creditCardReferenceId;
    private Cohort cohort;
    private String metaData;
}
