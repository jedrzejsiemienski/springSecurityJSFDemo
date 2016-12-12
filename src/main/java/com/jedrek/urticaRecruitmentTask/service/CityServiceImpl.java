package com.jedrek.urticaRecruitmentTask.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepo;
	
	@Transactional
	@Override
	public List<Customer> getCustomersForCity(long cityId){
		City city = cityRepo.findOne(cityId);
		return city.getCustomers().stream()
			.sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
			.collect(Collectors.toList());
	}
		
	
	@Transactional
	@Override
	public Map<String, Long> getCitiesMap(){
		return StreamSupport.stream(cityRepo.findAll().spliterator(), false)
				.collect(Collectors.toMap(City::getName, City::getId));
	}
	
	@Transactional
	@Override
	public City getDefaultCity(){
		return cityRepo.findAll().iterator().next();
	}

}
