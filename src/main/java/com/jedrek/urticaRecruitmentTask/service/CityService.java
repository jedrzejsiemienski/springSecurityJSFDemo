package com.jedrek.urticaRecruitmentTask.service;

import java.util.List;
import java.util.Map;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CityService {
	Map<String, Long> getCitiesMap();
	List<Customer> getSortedCustomersForCity(long cityId);
	City getDefaultCity();
}
