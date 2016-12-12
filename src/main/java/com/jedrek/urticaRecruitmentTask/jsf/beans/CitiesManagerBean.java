package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.service.CityService;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CitiesManagerBean {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CityService cityService;
	
	private long cityId;
	
	@PostConstruct
	private void init(){
		cityId = cityService.getDefaultCity().getId();
	}
	
	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	public Map<String, Long> getCitiesMap(){
		return cityService.getCitiesMap();
	}

	public List<Customer> getCustomersForCity(){
		return cityService.getCustomersForCity(cityId);
	}
	
	public void deleteCustomer(AjaxBehaviorEvent e){
		customerService.deleteCustomer(
			(Long)e.getComponent().getAttributes().get("customerId"));
	}
}
