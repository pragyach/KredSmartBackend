package com.kredsmart.dataaggregator.repository;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.entity.CoBrandEntity;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CoBrandRepository {


    private final HashMap<CoBrand, List<CoBrandEntity>> coBrandEntityMap = new HashMap<>();
    private final HashMap<UUID, List<CoBrandEntity>> creditCardRefIdCoBrandEntityMap = new HashMap<>();


    public void updateCoBrandEntityMap(CreditCardEntity creditCardEntity, List<CoBrand> coBrandList) {

        coBrandList.forEach(coBrand -> {
                CoBrandEntity coBrandEntity = CoBrandEntity.builder()
                    .referenceId(UUID.randomUUID())
                    .creditCardReferenceId(creditCardEntity.getReferenceId())
                    .coBrand(coBrand)
                    .metaData(UUID.randomUUID() + " coBrandMetaData")
                    .build();
                if (!coBrandEntityMap.containsKey(coBrand)) {
                    coBrandEntityMap.put(coBrand, new ArrayList<>(List.of(coBrandEntity)));
                } else {
                    coBrandEntityMap.get(coBrand).add(coBrandEntity);
                }
                if (!creditCardRefIdCoBrandEntityMap.containsKey(creditCardEntity.getReferenceId())) {
                    creditCardRefIdCoBrandEntityMap.put(creditCardEntity.getReferenceId(), new ArrayList<>(List.of(coBrandEntity)));
                } else {
                    creditCardRefIdCoBrandEntityMap.get(creditCardEntity.getReferenceId()).add(coBrandEntity);
                }
            }
        );
    }

    public List<CoBrandEntity> fetchByCoBrand(CoBrand coBrand) {
        if (!coBrandEntityMap.containsKey(coBrand)) {
            return List.of();
        } else {
            return coBrandEntityMap.get(coBrand);
        }
    }

    public List<CoBrandEntity> fetchByCreditCardReferenceId(UUID creditCardReferenceId) {
        if (!creditCardRefIdCoBrandEntityMap.containsKey(creditCardReferenceId)) {
            return List.of();
        } else {
            return creditCardRefIdCoBrandEntityMap.get(creditCardReferenceId);
        }
    }

}
