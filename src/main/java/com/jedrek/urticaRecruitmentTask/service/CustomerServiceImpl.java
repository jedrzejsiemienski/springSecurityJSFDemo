package com.jedrek.urticaRecruitmentTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CityRepository cityRepo;
	
	public Customer addCustomer(String login, String password, String name, long cityId) throws NullPointerException{
		City city = cityRepo.findOne(cityId);
		if(city == null){
			throw new NullPointerException();
		}
		
		Customer customer = new Customer();
		customer.setLogin(login);
		customer.setLogin(password);
		customer.setName(name);
		customer.setCity(city);
		city.getCustomers().add(customer);
		
		customerRepo.save(customer);
		
		return customer;
	}
	
	public Customer saveCustomer(Customer customer){
		customerRepo.save(customer);
		return customer;
	}
	
}
