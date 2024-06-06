package com.kredsmart.dataaggregator.repository;

import com.kredsmart.dataaggregator.cobrand.CoBrand;
import com.kredsmart.dataaggregator.cohert.Cohort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CohortCoBrandMapRepository {


    private final HashMap<CoBrand, List<Cohort>> coBrandCohortListMap = new HashMap<>();
    private final HashMap<Cohort, List<CoBrand>> cohortCoBrandListMap = new HashMap<>();


    public void updateCohortCoBrandMap(Cohort cohort, List<CoBrand> coBrandList) {
        coBrandList.forEach(coBrand -> {

            if (coBrandCohortListMap.containsKey(coBrand)) {
                coBrandCohortListMap.get(coBrand).add(cohort);
            } else {
                coBrandCohortListMap.put(coBrand, new ArrayList<>(List.of(cohort)));
            }

            if (cohortCoBrandListMap.containsKey(cohort)) {
                cohortCoBrandListMap.get(cohort).add(coBrand);
            } else {
                cohortCoBrandListMap.put(cohort, new ArrayList<>(List.of(coBrand)));
            }
        });
    }

    public List<Cohort> fetchAllCohortByCoBrand(CoBrand coBrand) {
        if (!coBrandCohortListMap.containsKey(coBrand)) {
            return List.of();
        }
        return coBrandCohortListMap.get(coBrand);
    }

    public List<CoBrand> fetchAllCoBrandByCohort(Cohort cohort) {
        if (!cohortCoBrandListMap.containsKey(cohort)) {
            return List.of();
        } else {
            return cohortCoBrandListMap.get(cohort);
        }
    }
}
