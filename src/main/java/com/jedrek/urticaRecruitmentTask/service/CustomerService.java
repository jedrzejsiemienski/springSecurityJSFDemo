package com.jedrek.urticaRecruitmentTask.service;

import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CustomerService {
	Customer addCustomer(String login, String password, String name, long cityId);
	Customer saveCustomer(Customer customer);
	boolean loginExists(String login);
}
