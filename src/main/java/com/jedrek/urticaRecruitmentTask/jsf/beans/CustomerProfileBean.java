package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
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
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;
import com.sun.faces.mgbean.ManagedBeanInfo.MapEntry;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	private final static Logger logger = Logger.getLogger(CustomerProfileBean.class);	
	
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
	
	@Transactional
	public void deleteCustomer(AjaxBehaviorEvent e){
		Long id = (Long)e.getComponent().getAttributes().get("customerId");
		customerRepository.delete(id);
	}
	
	@Transactional
	public void setCustomerName(String newName) {
		//logger.debug("\n\n\nSetting name to " + newName + "\n\n\n");
		loadCurrentCustomer();
		currentCustomer.setName(newName);
		customerRepository.save(currentCustomer);
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
	public void setCurrentCustomerCityId(long cityId) {
		loadCurrentCustomer();
		currentCustomer.getCity().getCustomers().remove(currentCustomer);
		
		City newCity = cityRepository.findOne(cityId);
		newCity.getCustomers().add(currentCustomer);
		currentCustomer.setCity(newCity);
		customerRepository.save(currentCustomer);
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
	}
	
	@Transactional
	public List<Customer> getCustomersForCity(){
		//logger.warn("\n\n\n Updating users for city \n\n\n");
		City city = cityRepository.findOne(cityId);
		return city.getCustomers().stream()
			.sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
			.collect(Collectors.toList());
	}


	
}
