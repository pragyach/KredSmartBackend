package com.kredsmart.dataaggregator.entity;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CoBrandEntity {

    private UUID referenceId;
    private UUID creditCardReferenceId;
    private CoBrand coBrand;
    private String metaData;
}
