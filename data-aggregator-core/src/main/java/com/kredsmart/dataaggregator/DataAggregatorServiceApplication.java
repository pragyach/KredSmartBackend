package com.kredsmart.dataaggregator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kredsmart.dataaggregator.bank.Bank;
import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import com.kredsmart.dataaggregator.contract.request.CustomerSpendAnalysisRequestV1;
import com.kredsmart.dataaggregator.creditcard.CreditCard;
import com.kredsmart.dataaggregator.entity.CreditCardEntity;
import com.kredsmart.dataaggregator.networkprovider.NetworkProvider;
import com.kredsmart.dataaggregator.repository.CoBrandRepository;
import com.kredsmart.dataaggregator.repository.CohortCoBrandMapRepository;
import com.kredsmart.dataaggregator.repository.CohortRepository;
import com.kredsmart.dataaggregator.repository.CreditCardRepository;
import com.kredsmart.dataaggregator.service.DataAggregatorService;
import com.kredsmart.dataaggregator.service.DataSegregationService;
import com.kredsmart.dataaggregator.service.TestingMockService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DataAggregatorServiceApplication {

    private final static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws Exception {

        TestingMockService testingMockService = initializeTestingMockService();

        initiateTestCases(testingMockService);

    }


    private static TestingMockService initializeTestingMockService() {

        TestingMockService testingMockService = initialiseTestingMockService();

        updateCohortCoBrandMapRepository(testingMockService);

        updateCreditCardEntities(testingMockService);

        return testingMockService;
    }

    private static void updateCohortCoBrandMapRepository(TestingMockService testingMockService) {
        testingMockService.updateCohortCoBrandMapRepository(Cohort.FUEL, List.of(CoBrand.INDIAN_OIL, CoBrand.BHARAT_PETROLEUM, CoBrand.SHELL_OIL));
        testingMockService.updateCohortCoBrandMapRepository(Cohort.TRAVEL_METRO, List.of(CoBrand.DELHI_METRO, CoBrand.JAIPUR_METRO, CoBrand.MUMBAI_METRO));
        testingMockService.updateCohortCoBrandMapRepository(Cohort.TRAVEL_CAB, List.of(CoBrand.UBER_TRAVEL, CoBrand.MERU_CAB, CoBrand.OLA_TRAVEL));
        testingMockService.updateCohortCoBrandMapRepository(Cohort.SHOPPING_ONLINE, List.of(CoBrand.AMAZON_SHOP, CoBrand.FLIPKART_SHOP, CoBrand.SHOPPERSTOP));
        testingMockService.updateCohortCoBrandMapRepository(Cohort.FOOD, List.of(CoBrand.AMAZON_EATS, CoBrand.SWIGGY_EATS, CoBrand.ZOMATO_EATS,
            CoBrand.UBER_EATS));
        testingMockService.updateCohortCoBrandMapRepository(Cohort.TRAVEL_FLIGHT, List.of(CoBrand.CLUB_INDIGO, CoBrand.CLUB_VISTARA, CoBrand.CLUB_SPICEJET));
    }

    private static TestingMockService initialiseTestingMockService() {

        CoBrandRepository coBrandRepository = new CoBrandRepository();
        CohortRepository cohortRepository = new CohortRepository();
        CohortCoBrandMapRepository cohortCoBrandMapRepository = new CohortCoBrandMapRepository();
        CreditCardRepository creditCardRepository = new CreditCardRepository();
        DataAggregatorService dataAggregatorService = new DataAggregatorService(coBrandRepository, cohortRepository, creditCardRepository,
            cohortCoBrandMapRepository);
        DataSegregationService dataSegregationService = new DataSegregationService(dataAggregatorService, creditCardRepository, cohortRepository,
            coBrandRepository);

        return new TestingMockService(coBrandRepository, cohortRepository, cohortCoBrandMapRepository,
            creditCardRepository, dataAggregatorService, dataSegregationService);
    }

    private static void updateCreditCardEntities(TestingMockService testingMockService) {

        CreditCardEntity indianOilHdfcCreditCard = CreditCardEntity.builder()
            .creditCard(CreditCard.INDIAN_OIL_HDFC)
            .referenceId(UUID.randomUUID())
            .bank(Bank.HDFC_BANK)
            .joiningFee(499.0)
            .annualRenewalFee(599.0)
            .networkProvider(NetworkProvider.VISA_PLATINUM)
            .build();

        testingMockService.updateMockRepository(indianOilHdfcCreditCard,
            List.of(Cohort.FUEL),
            List.of(CoBrand.INDIAN_OIL, CoBrand.BHARAT_PETROLEUM));

        CreditCardEntity swiggyHdfcCreditCard = CreditCardEntity.builder()
            .creditCard(CreditCard.SWIGGY_HDFC)
            .referenceId(UUID.randomUUID())
            .bank(Bank.HDFC_BANK)
            .joiningFee(399.0)
            .annualRenewalFee(599.0)
            .networkProvider(NetworkProvider.MASTERCARD)
            .build();

        testingMockService.updateMockRepository(swiggyHdfcCreditCard,
            List.of(Cohort.FOOD),
            List.of(CoBrand.SWIGGY_EATS));

        CreditCardEntity delhiMetroSbiCreditCard = CreditCardEntity.builder()
            .creditCard(CreditCard.DELHI_METRO_SBI)
            .referenceId(UUID.randomUUID())
            .bank(Bank.SBI)
            .joiningFee(899.0)
            .annualRenewalFee(999.0)
            .networkProvider(NetworkProvider.VISA)
            .build();

        testingMockService.updateMockRepository(delhiMetroSbiCreditCard,
            List.of(Cohort.TRAVEL_METRO),
            List.of(CoBrand.DELHI_METRO));

        CreditCardEntity mumbaiMetroSbiCreditCard = CreditCardEntity.builder()
            .creditCard(CreditCard.MUMBAI_METRO_SBI)
            .referenceId(UUID.randomUUID())
            .bank(Bank.SBI)
            .joiningFee(599.0)
            .annualRenewalFee(399.0)
            .networkProvider(NetworkProvider.MASTERCARD)
            .build();

        testingMockService.updateMockRepository(mumbaiMetroSbiCreditCard,
            List.of(Cohort.TRAVEL_METRO),
            List.of(CoBrand.MUMBAI_METRO));

        CreditCardEntity indianOilAxisCreditCard = CreditCardEntity.builder()
            .creditCard(CreditCard.INDIAN_OIL_AXIS_BANK_RUPAY)
            .referenceId(UUID.randomUUID())
            .bank(Bank.AXIS_BANK)
            .joiningFee(299.0)
            .annualRenewalFee(899.0)
            .networkProvider(NetworkProvider.MASTERCARD)
            .build();

        testingMockService.updateMockRepository(indianOilAxisCreditCard,
            List.of(Cohort.FUEL),
            List.of(CoBrand.INDIAN_OIL, CoBrand.SHELL_OIL));
    }

    private static void initiateTestCases(TestingMockService testingMockService) throws Exception {

        CustomerSpendAnalysisRequestV1 customerSpendAnalysisRequestV1 = createCustomerSpendAnalysisRequestV1();


        testingMockService.getAllCreditCards(customerSpendAnalysisRequestV1)
            .ifPresent(customerSpendAnalysisResponseV1 -> {
                try {
                    System.out.println(objectMapper.writeValueAsString(customerSpendAnalysisRequestV1));
                    System.out.println(objectMapper.writeValueAsString(customerSpendAnalysisResponseV1));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    private static CustomerSpendAnalysisRequestV1 createCustomerSpendAnalysisRequestV1() throws Exception {
        Map<String, List<String>> cohortToCoBrandMap = new HashMap<>();
        cohortToCoBrandMap.put(Cohort.FOOD.name(), List.of(CoBrand.SWIGGY_EATS.name()));
        cohortToCoBrandMap.put(Cohort.FUEL.name(), List.of(CoBrand.INDIAN_OIL.name(), CoBrand.BHARAT_PETROLEUM.name()));
        cohortToCoBrandMap.put(Cohort.TRAVEL_METRO.name(), List.of(CoBrand.MUMBAI_METRO.name(), CoBrand.DELHI_METRO.name()));

        return CustomerSpendAnalysisRequestV1.builder()
            .customerReferenceId(UUID.randomUUID())
            .cohortToCoBrandMap(cohortToCoBrandMap)
            .build();
    }
}
