package com.jedrek.urticaRecruitmentTask.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.model.BasicCustomerData;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@SessionScoped
public class CustomerProfileBean {

	@Autowired
	CustomerService customerService;

	BasicCustomerData currentCustomer;
	private BasicCustomerData loadCurrentCustomer(boolean force){
		if(currentCustomer == null || force){
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			currentCustomer = customerService.getBasicCustomerDataForLogin(auth.getName());			
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
