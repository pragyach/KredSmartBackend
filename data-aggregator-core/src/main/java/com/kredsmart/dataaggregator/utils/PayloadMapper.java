package com.kredsmart.dataaggregator.utils;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.contract.response.CreditCardResponseV1;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import java.util.List;
import java.util.stream.Collectors;

public class PayloadMapper {

    public static CreditCardResponseV1 mapCreditCardDataToCreditCardsResponseV1(CreditCardEntity creditCardEntity, List<CoBrand> coBrandList,
        List<Cohort> cohortList) {

        List<String> coBrands = coBrandList.stream()
            .map(Enum::name)
            .collect(Collectors.toList());

        List<String> cohorts = cohortList.stream()
            .map(Enum::name)
            .collect(Collectors.toList());

        return CreditCardResponseV1.builder()
            .creditCardIdReferenceId(creditCardEntity.getReferenceId())
            .creditCard(creditCardEntity.getCreditCard().name())
            .joiningFee(creditCardEntity.getJoiningFee())
            .bank(creditCardEntity.getBank().name())
            .annualRenewalFee(creditCardEntity.getAnnualRenewalFee())
            .networkProvider(creditCardEntity.getNetworkProvider().name())
            .coBrands(coBrands)
            .metaData(creditCardEntity.getMetaData())
            .cohorts(cohorts)
            .build();
    }
}
