package com.kredsmart.dataaggregator.service;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import com.kredsmart.dataaggregator.repository.CoBrandRepository;
import com.kredsmart.dataaggregator.repository.CohortCoBrandMapRepository;
import com.kredsmart.dataaggregator.repository.CohortRepository;
import com.kredsmart.dataaggregator.repository.CreditCardRepository;
import java.util.ArrayList;
import java.util.List;

public class DataAggregatorService {

    private final CoBrandRepository coBrandRepository;
    private final CohortRepository cohortRepository;
    private final CreditCardRepository creditCardRepository;

    private final CohortCoBrandMapRepository cohortCoBrandMapRepository;

    public DataAggregatorService(CoBrandRepository coBrandRepository, CohortRepository cohortRepository, CreditCardRepository creditCardRepository,
        CohortCoBrandMapRepository cohortCoBrandMapRepository) {
        this.coBrandRepository = coBrandRepository;
        this.cohortRepository = cohortRepository;
        this.creditCardRepository = creditCardRepository;
        this.cohortCoBrandMapRepository = cohortCoBrandMapRepository;
    }


    public List<CreditCardEntity> getAllCreditCardByCoBrandAndCohort(List<CoBrand> coBrandList, List<Cohort> cohortList) {
        List<CreditCardEntity> creditCardEntityList = new ArrayList<>();
        creditCardEntityList.addAll(getAllCreditCardByCoBrand(coBrandList));
        creditCardEntityList.addAll(getAllCreditCardByCoHort(cohortList));
        return creditCardEntityList;
    }

    public List<CoBrand> getAllCoBrandForGivenCohort(Cohort cohort) {
        return cohortCoBrandMapRepository.fetchAllCoBrandByCohort(cohort);
    }

    public List<Cohort> getAllCohortForGivenCoBrand(CoBrand coBrand) {
        return cohortCoBrandMapRepository.fetchAllCohortByCoBrand(coBrand);
    }

    //    @Transactional
    public List<CreditCardEntity> getAllCreditCardByCoBrand(List<CoBrand> coBrandList) {

        List<CreditCardEntity> creditCardEntityList = new ArrayList<>();

        coBrandList.forEach(coBrand -> {
            coBrandRepository.fetchByCoBrand(coBrand)
                .forEach(coBrandEntity -> {
                    creditCardRepository.fetchCreditCardByReferenceId(coBrandEntity.getCreditCardReferenceId())
                        .ifPresent(creditCardEntityList::add);
                });
        });
        return creditCardEntityList;
    }

    //    @Transactional
    public List<CreditCardEntity> getAllCreditCardByCoHort(List<Cohort> cohortList) {

        List<CreditCardEntity> creditCardEntityList = new ArrayList<>();

        cohortList.forEach(cohort -> {
            cohortRepository.fetchByCohort(cohort)
                .forEach(cohortEntity -> {
                    creditCardRepository.fetchCreditCardByReferenceId(cohortEntity.getCreditCardReferenceId())
                        .ifPresent(creditCardEntityList::add);
                });
        });
        return creditCardEntityList;
    }
}
