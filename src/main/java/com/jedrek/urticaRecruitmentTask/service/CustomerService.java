package com.jedrek.urticaRecruitmentTask.service;

import java.util.List;
import java.util.Map;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.CurrentCustomerData;
import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CustomerService {
	Customer addCustomer(String login, String password, String name, long cityId);
	Customer saveCustomer(Customer customer);
	
	void deleteCustomer(long customerId); 
	
	Customer changeCustomerCity(long customerId, long newCityId);
	Customer changeCustomerName(long customerId, String newName);
	CurrentCustomerData getCurrentCustomerData(String login);
	
	boolean loginExists(String login);
}
