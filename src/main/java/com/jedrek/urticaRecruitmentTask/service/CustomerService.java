package com.jedrek.urticaRecruitmentTask.service;

import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CustomerService {
	Customer addCustomer(String login, String password, String name, long cityId);
	Customer saveCustomer(Customer customer);
	
	void deleteCustomer(long customerId); 
	
	Customer changeCustomerCity(long customerId, long newCityId);
	Customer changeCustomerName(long customerId, String newName);
	Customer getBasicCustomerDataForLogin(String login);
	
	boolean loginExists(String login);
}
