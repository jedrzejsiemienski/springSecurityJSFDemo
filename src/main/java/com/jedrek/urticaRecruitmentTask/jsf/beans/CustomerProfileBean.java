package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	@Autowired
	CityRepository cityRepository;
	
	private final static Logger logger = Logger.getLogger(CustomerProfileBean.class);	
	
	private long cityId = 10;
	
	public String getCustomerName() {
	      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      return auth.getName();
	}

	public Map<String, Long> getCitiesMap(){
		return StreamSupport.stream(cityRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(City::getName, City::getId));
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
		logger.debug(String.format("\n\nCity id changed to %s\n\n", cityId));
	}
	
	@Transactional
	public List<Customer> getCustomersForCity(){
		logger.debug(String.format("\n\nGetting customers for city id %s\n\n", cityId));
		City city = cityRepository.findOne(cityId);
		return city.getCustomers().stream().collect(Collectors.toList());
	}


	
}
