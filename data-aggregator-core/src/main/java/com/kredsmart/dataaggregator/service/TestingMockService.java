package com.kredsmart.dataaggregator.service;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.contract.request.CustomerSpendAnalysisRequestV1;
import com.kredsmart.dataaggregator.contract.response.CreditCardResponseV1;
import com.kredsmart.dataaggregator.contract.response.CustomerSpendAnalysisResponseV1;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import com.kredsmart.dataaggregator.repository.CoBrandRepository;
import com.kredsmart.dataaggregator.repository.CohortCoBrandMapRepository;
import com.kredsmart.dataaggregator.repository.CohortRepository;
import com.kredsmart.dataaggregator.repository.CreditCardRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestingMockService {

    private final CoBrandRepository coBrandRepository;
    private final CohortRepository cohortRepository;
    private final CohortCoBrandMapRepository cohortCoBrandMapRepository;
    private final CreditCardRepository creditCardRepository;
    private final DataAggregatorService dataAggregatorService;
    private final DataSegregationService dataSegregationService;

    public TestingMockService(CoBrandRepository coBrandRepository, CohortRepository cohortRepository,
        CohortCoBrandMapRepository cohortCoBrandMapRepository, CreditCardRepository creditCardRepository, DataAggregatorService dataAggregatorService,
        DataSegregationService dataSegregationService) {
        this.coBrandRepository = coBrandRepository;
        this.cohortRepository = cohortRepository;
        this.cohortCoBrandMapRepository = cohortCoBrandMapRepository;
        this.creditCardRepository = creditCardRepository;
        this.dataAggregatorService = dataAggregatorService;
        this.dataSegregationService = dataSegregationService;
    }

    public Optional<CustomerSpendAnalysisResponseV1> getAllCreditCards(CustomerSpendAnalysisRequestV1 customerSpendAnalysisRequestV1) {
        return dataSegregationService.fetchCreditCards(customerSpendAnalysisRequestV1);
    }

    public Optional<CreditCardResponseV1> fetchCreditCardByRefId(UUID creditCardRefId) {
        return dataSegregationService.fetchCreditCardData(creditCardRefId);
    }

    public void updateMockRepository(CreditCardEntity creditCardEntity, List<Cohort> cohortList, List<CoBrand> coBrandList) {
        creditCardRepository.updateMockCreditCardRepo(creditCardEntity);
        cohortRepository.updateCohortEntityMap(creditCardEntity, cohortList);
        coBrandRepository.updateCoBrandEntityMap(creditCardEntity, coBrandList);
    }

    public void updateCohortCoBrandMapRepository(Cohort cohort, List<CoBrand> coBrandList) {
        cohortCoBrandMapRepository.updateCohortCoBrandMap(cohort, coBrandList);
    }

    public List<Cohort> fetchAllCohortByCoBrand(CoBrand coBrand) {
        return cohortCoBrandMapRepository.fetchAllCohortByCoBrand(coBrand);
    }

    public List<CoBrand> fetchAllCoBrandByCohort(Cohort cohort) {
        return cohortCoBrandMapRepository.fetchAllCoBrandByCohort(cohort);
    }
}
