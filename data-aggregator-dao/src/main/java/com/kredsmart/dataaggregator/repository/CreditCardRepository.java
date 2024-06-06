package com.kredsmart.dataaggregator.repository;

import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CreditCardRepository {

    //TODO-> referenceId, creditCard unique index
    private final HashMap<UUID, CreditCardEntity> creditCardEntityMap = new HashMap<>();

    public void updateMockCreditCardRepo(CreditCardEntity creditCardEntity) {
        creditCardEntityMap.put(creditCardEntity.getReferenceId(), creditCardEntity);
    }

    public Optional<CreditCardEntity> fetchCreditCardByReferenceId(UUID creditCardIdReferenceId) {
        if (creditCardEntityMap.containsKey(creditCardIdReferenceId)) {
            return Optional.of(creditCardEntityMap.get(creditCardIdReferenceId));
        }
        return Optional.empty();
    }

    public List<CreditCardEntity> fetchAllByReferenceId(List<UUID> referenceIdList) {
        List<CreditCardEntity> creditCardEntityList = new ArrayList<>();
        referenceIdList.forEach(referenceId -> {
            if (creditCardEntityMap.containsKey(referenceId)) {
                creditCardEntityList.add(creditCardEntityMap.get(referenceId));
            }
        });
        return creditCardEntityList;
    }


}
