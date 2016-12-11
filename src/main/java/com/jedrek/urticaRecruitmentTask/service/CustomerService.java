package com.jedrek.urticaRecruitmentTask.service;

import java.util.List;
import java.util.Map;

import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CustomerService {
	Customer addCustomer(String login, String password, String name, long cityId);
	Customer saveCustomer(Customer customer);
	
	void deleteCustomer(long customerId); 
	
	Customer changeCustomerCity(long customerId, long newCityId);
	Customer changeCustomerName(long customerId, String newName);
	
	boolean loginExists(String login);
	
	Map<String, Long> getCitiesMap();
	List<Customer> getCustomersForCity(long cityId);
}
