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

import com.jedrek.urticaRecruitmentTask.model.CurrentCustomerData;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	private static final Logger logger = Logger.getLogger(CustomerProfileBean.class);
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;

	private long cityId;
	
	@PostConstruct
	private void init(){
		cityId = customerService.getDefaultCity().getId();
	}
	
	CurrentCustomerData currentCustomer;
	private void loadCurrentCustomer(boolean force){
		if(currentCustomer == null || force){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			currentCustomer = customerService.getCurrentCustomerData(auth.getName());			
		}
	}
	
	public void setCustomerName(String newName) {
		loadCurrentCustomer(false);
		customerService.changeCustomerName(currentCustomer.id, newName);
		loadCurrentCustomer(true);
	}
	
	public void setCurrentCustomerCityId(long newCityId) {
		loadCurrentCustomer(false);
		customerService.changeCustomerCity(currentCustomer.id, newCityId);
		loadCurrentCustomer(true);
	}
	
	public String getCustomerName() {
		loadCurrentCustomer(false);
		return currentCustomer.name;
	}
	
	public long getCustomerId() {
		loadCurrentCustomer(false);
		return currentCustomer.id;
	}
	
	public String getCustomerCityName() {
		loadCurrentCustomer(false);
		return currentCustomer.cityName;
	}
	
	public long getCurrentCustomerCityId() {
		loadCurrentCustomer(false);
		return currentCustomer.cityId;
	}

	public Map<String, Long> getCitiesMap(){
		return customerService.getCitiesMap();
	}

	public List<Customer> getCustomersForCity(){
		return customerService.getCustomersForCity(cityId);
	}
	
	public void deleteCustomer(AjaxBehaviorEvent e){
		logger.warn(String.format("Deleting customer"));
		customerService.deleteCustomer(
			(Long)e.getComponent().getAttributes().get("customerId"));
	}
	
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
}
