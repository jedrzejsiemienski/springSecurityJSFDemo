package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;

	private long cityId;
	
	private Customer currentCustomer;
	private City currentCustomerCity;
	
	@PostConstruct
	private void init(){
		cityId = cityRepository.findAll().iterator().next().getId();
	}
	
	@Transactional
	private void loadCurrentCustomer(){
		//logger.debug(String.format("\n\nGetting current customer\n\n", cityId));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		currentCustomer = customerRepository.findByLogin(auth.getName());
		currentCustomerCity = currentCustomer.getCity();
	}
	
	public String getCustomerName() {
		loadCurrentCustomer();
		return currentCustomer.getName();
	}
	
	public long getCustomerId() {
		loadCurrentCustomer();
		return currentCustomer.getId();
	}
	
	public void deleteCustomer(AjaxBehaviorEvent e){
		customerService.deleteCustomer(
			(Long)e.getComponent().getAttributes().get("customerId"));
	}
	
	@Transactional
	public void setCustomerName(String newName) {
		//logger.debug("\n\n\nSetting name to " + newName + "\n\n\n");
		loadCurrentCustomer();
		customerService.changeCustomerName(currentCustomer.getId(), newName);
	}
	
	@Transactional
	public String getCustomerCityName() {
		loadCurrentCustomer();
		return currentCustomerCity.getName();
	}
	
	@Transactional
	public long getCurrentCustomerCityId() {
		loadCurrentCustomer();
		return currentCustomerCity.getId();
	}

	@Transactional
	public void setCurrentCustomerCityId(long newCityId) {
		loadCurrentCustomer();
		customerService.changeCustomerCity(currentCustomer.getId(), newCityId);
	}

	public Map<String, Long> getCitiesMap(){
		return customerService.getCitiesMap();
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	public List<Customer> getCustomersForCity(){
		return customerService.getCustomersForCity(cityId);
	}
	
}
