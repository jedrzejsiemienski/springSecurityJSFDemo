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
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	private static final Logger logger = Logger.getLogger(CustomerProfileBean.class);
	
	@Autowired
	CustomerService customerService;

	CurrentCustomerData currentCustomer;
	private CurrentCustomerData loadCurrentCustomer(boolean force){
		if(currentCustomer == null || force){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			currentCustomer = customerService.getCurrentCustomerData(auth.getName());			
		}
		return currentCustomer;
	}
	
	public void setCustomerName(String newName) {
		customerService.changeCustomerName(loadCurrentCustomer(false).id, newName);
		loadCurrentCustomer(true);
	}
	
	public void setCustomerCityId(long newCityId) {
		customerService.changeCustomerCity(loadCurrentCustomer(false).id, newCityId);
		loadCurrentCustomer(true);
	}
	
	public String getCustomerName() {
		return loadCurrentCustomer(false).name;
	}
	
	public long getCustomerId() {
		return loadCurrentCustomer(false).id;
	}
	
	public String getCustomerCityName() {
		return loadCurrentCustomer(false).cityName;
	}
	
	public long getCustomerCityId() {
		return loadCurrentCustomer(false).cityId;
	}
}
