package com.jedrek.urticaRecruitmentTask.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	@Autowired
	CustomerService customerService;

	Customer currentCustomer;
	private Customer loadCurrentCustomer(boolean force){
		if(currentCustomer == null || force){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			currentCustomer = customerService.getBasicCustomerDataForLogin(auth.getName());			
		}
		return currentCustomer;
	}
	
	public void setCustomerName(String newName) {
		customerService.changeCustomerName(loadCurrentCustomer(false).getId(), newName);
		loadCurrentCustomer(true);
	}
	
	public void setCustomerCityId(long newCityId) {
		customerService.changeCustomerCity(loadCurrentCustomer(false).getId(), newCityId);
		loadCurrentCustomer(true);
	}
	
	public String getCustomerName() {
		return loadCurrentCustomer(false).getName();
	}
	
	public long getCustomerId() {
		return loadCurrentCustomer(false).getId();
	}
	
	public String getCustomerCityName() {
		return loadCurrentCustomer(false).getCity().getName();
	}
	
	public long getCustomerCityId() {
		return loadCurrentCustomer(false).getCity().getId();
	}
}
