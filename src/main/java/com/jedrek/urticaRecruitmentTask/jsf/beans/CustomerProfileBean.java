package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

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

	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;

	private long cityId;
	
	private CurrentCustomerData currentCustomer;
	
	@PostConstruct
	private void init(){
		cityId = customerService.getDefaultCity().getId();
	}
	
	private void loadCurrentCustomer(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		currentCustomer = customerService.getCurrentCustomerData(auth.getName());
	}
	
	public String getCustomerName() {
		loadCurrentCustomer();
		return currentCustomer.name;
	}
	
	public long getCustomerId() {
		loadCurrentCustomer();
		return currentCustomer.id;
	}
	
	public void setCustomerName(String newName) {
		loadCurrentCustomer();
		customerService.changeCustomerName(currentCustomer.id, newName);
	}
	
	public String getCustomerCityName() {
		loadCurrentCustomer();
		return currentCustomer.cityName;
	}
	
	public long getCurrentCustomerCityId() {
		loadCurrentCustomer();
		return currentCustomer.cityId;
	}

	public void setCurrentCustomerCityId(long newCityId) {
		loadCurrentCustomer();
		customerService.changeCustomerCity(currentCustomer.id, newCityId);
	}

	public Map<String, Long> getCitiesMap(){
		return customerService.getCitiesMap();
	}

	public List<Customer> getCustomersForCity(){
		return customerService.getCustomersForCity(cityId);
	}
	
	public void deleteCustomer(AjaxBehaviorEvent e){
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
