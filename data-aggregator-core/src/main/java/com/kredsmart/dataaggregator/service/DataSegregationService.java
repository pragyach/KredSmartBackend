package com.kredsmart.dataaggregator.service;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.contract.request.CustomerSpendAnalysisRequestV1;
import com.kredsmart.dataaggregator.contract.response.CreditCardResponseV1;
import com.kredsmart.dataaggregator.contract.response.CustomerSpendAnalysisResponseV1;
import com.kredsmart.dataaggregator.entity.CoBrandEntity;
import com.kredsmart.dataaggregator.entity.CohortEntity;
import com.kredsmart.dataaggregator.repository.CoBrandRepository;
import com.kredsmart.dataaggregator.repository.CohortRepository;
import com.kredsmart.dataaggregator.repository.CreditCardRepository;
import com.kredsmart.dataaggregator.utils.PayloadMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class DataSegregationService {

    private final DataAggregatorService dataAggregatorService;
    private final CreditCardRepository creditCardRepository;
    private final CohortRepository cohortRepository;
    private final CoBrandRepository coBrandRepository;

    public DataSegregationService(DataAggregatorService dataAggregatorService, CreditCardRepository creditCardRepository,
        CohortRepository cohortRepository, CoBrandRepository coBrandRepository) {
        this.dataAggregatorService = dataAggregatorService;
        this.creditCardRepository = creditCardRepository;
        this.cohortRepository = cohortRepository;
        this.coBrandRepository = coBrandRepository;
    }


    public Optional<CustomerSpendAnalysisResponseV1> fetchCreditCards(CustomerSpendAnalysisRequestV1 customerSpendAnalysisRequest) {

        UUID customerId = customerSpendAnalysisRequest.getCustomerReferenceId();
        Map<String, List<String>> cohortToCoBrandMapList = customerSpendAnalysisRequest.getCohortToCoBrandMap();

        Map<String, Map<String, List<CreditCardResponseV1>>> eligibleCreditCards = new HashMap<>();

        for (Map.Entry<String, List<String>> entry : cohortToCoBrandMapList.entrySet()) {
            Cohort cohortEnum = Cohort.valueOf(entry.getKey());

            if (!eligibleCreditCards.containsKey(cohortEnum.name())) {
                List<CreditCardResponseV1> cohortSuggestedCreditCard = new ArrayList<>();

                    dataAggregatorService.getAllCreditCardByCoHort(List.of(cohortEnum))
                    .forEach(creditCardEntity -> {
                            fetchCreditCardData(creditCardEntity.getReferenceId())
                                .ifPresent(cohortSuggestedCreditCard::add);
                        });
                eligibleCreditCards.put(cohortEnum.name(), Map.of("COHORT_CARDS", cohortSuggestedCreditCard));
            }

            entry.getValue().forEach(coBrand -> {
                CoBrand coBrandEnum = CoBrand.valueOf(coBrand);
                Map<String, List<CreditCardResponseV1>> coBrandMap = eligibleCreditCards.get(cohortEnum.name());

                if (coBrandMap != null && !coBrandMap.containsKey(coBrand)) {
                    List<CreditCardResponseV1> coBrandSuggestedCreditCard = new ArrayList<>();

                    dataAggregatorService.getAllCreditCardByCoBrand(List.of(coBrandEnum))
                        .forEach(creditCardEntity -> {
                            fetchCreditCardData(creditCardEntity.getReferenceId())
                                .ifPresent(coBrandSuggestedCreditCard::add);
                        });

                    Map<String, List<CreditCardResponseV1>> updatedCoBrandMap = new HashMap<>(coBrandMap);
                    updatedCoBrandMap.put(coBrand, new ArrayList<>(coBrandSuggestedCreditCard));
                    eligibleCreditCards.put(cohortEnum.name(), updatedCoBrandMap);
                }
            });

        }

        CustomerSpendAnalysisResponseV1 customerSpendAnalysisResponseV1 = CustomerSpendAnalysisResponseV1.builder()
            .customerReferenceId(customerId)
            .eligibleCreditCards(eligibleCreditCards)
            .build();

        return Optional.of(customerSpendAnalysisResponseV1);

    }

    public Optional<CreditCardResponseV1> fetchCreditCardData(UUID creditCardIdReferenceId) {

        return creditCardRepository.fetchCreditCardByReferenceId(creditCardIdReferenceId)
            .map(creditCardEntity -> {
                List<CoBrandEntity> coBrandEntityList = coBrandRepository.fetchByCreditCardReferenceId(creditCardIdReferenceId);
                List<CohortEntity> cohortEntityList = cohortRepository.fetchByCreditCardReferenceId(creditCardIdReferenceId);

                List<CoBrand> coBrandList = coBrandEntityList.stream()
                    .map(CoBrandEntity::getCoBrand)
                    .collect(Collectors.toList());

                List<Cohort> cohortList = cohortEntityList.stream()
                    .map(CohortEntity::getCohort)
                    .collect(Collectors.toList());

                return PayloadMapper.mapCreditCardDataToCreditCardsResponseV1(creditCardEntity, coBrandList, cohortList);
            });

    }
}
