package com.kredsmart.dataaggregator.repository;

import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.entity.CohortEntity;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CohortRepository {


    private final HashMap<Cohort, List<CohortEntity>> cohortEntityMap = new HashMap<>();
    private final HashMap<UUID, List<CohortEntity>> creditCardRefIdCohortEntityMap = new HashMap<>();


    public void updateCohortEntityMap(CreditCardEntity creditCardEntity, List<Cohort> cohortList) {

        cohortList.forEach(cohort -> {
                CohortEntity cohortEntity = CohortEntity.builder()
                    .referenceId(UUID.randomUUID())
                    .creditCardReferenceId(creditCardEntity.getReferenceId())
                    .cohort(cohort)
                    .metaData(UUID.randomUUID() + " cohortMetaData")
                    .build();
                if (!cohortEntityMap.containsKey(cohort)) {
                    cohortEntityMap.put(cohort, new ArrayList<>(List.of(cohortEntity)));
                } else {
                    cohortEntityMap.get(cohort).add(cohortEntity);
                }
                if (!creditCardRefIdCohortEntityMap.containsKey(creditCardEntity.getReferenceId())) {
                    creditCardRefIdCohortEntityMap.put(creditCardEntity.getReferenceId(), new ArrayList<>(List.of(cohortEntity)));
                } else {
                    creditCardRefIdCohortEntityMap.get(creditCardEntity.getReferenceId()).add(cohortEntity);
                }
            }
        );
    }

    public List<CohortEntity> fetchByCohort(Cohort cohort) {
        if (cohortEntityMap.containsKey(cohort)) {
            return cohortEntityMap.get(cohort);
        }
        return List.of();
    }

    public List<CohortEntity> fetchByCreditCardReferenceId(UUID creditCardReferenceId) {
        if (creditCardRefIdCohortEntityMap.containsKey(creditCardReferenceId)) {
            return creditCardRefIdCohortEntityMap.get(creditCardReferenceId);
        }
        return List.of();
    }

}
