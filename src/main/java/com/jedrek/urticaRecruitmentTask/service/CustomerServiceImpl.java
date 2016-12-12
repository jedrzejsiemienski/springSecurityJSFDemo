package com.jedrek.urticaRecruitmentTask.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.CurrentCustomerData;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CityRepository cityRepo;
	
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@Transactional
	@Override
	public Customer addCustomer(String login, String password, String name, long cityId) throws NullPointerException{
		City city = cityRepo.findOne(cityId);
		if(city == null){
			throw new NullPointerException();
		}
		
		Customer customer = new Customer();
		customer.setLogin(login);
		customer.setPassword(passwordEncoder.encode(password));
		customer.setName(name);
		customer.setCity(city);
		city.getCustomers().add(customer);
		
		customerRepo.save(customer);
		
		return customer;
	}
	
	@Transactional
	@Override
	public Customer saveCustomer(Customer customer){
		customerRepo.save(customer);
		return customer;
	}
	
	@Transactional
	@Override
	public void deleteCustomer(long customerId){
		customerRepo.delete(customerId);
	}
	
	@Transactional
	@Override
	public Customer changeCustomerCity(long customerId, long newCityId){
		Customer customer = customerRepo.findOne(customerId);
		customer.getCity().getCustomers().remove(customer);
		City newCity = cityRepo.findOne(newCityId);
		newCity.getCustomers().add(customer);
		customer.setCity(newCity);
		customerRepo.save(customer);
		return customer;
	}
	
	@Transactional
	@Override
	public Customer changeCustomerName(long customerId, String newName){
		Customer customer = customerRepo.findOne(customerId);
		customer.setName(newName);
		customerRepo.save(customer);
		return customer;
	}
	
	@Transactional
	@Override
	public CurrentCustomerData getCurrentCustomerData(String login){
		Customer customer = customerRepo.findByLogin(login);
		CurrentCustomerData result = new CurrentCustomerData();
		result.id = customer.getId();
		result.name = customer.getName();
		result.cityId = customer.getCity().getId();
		result.cityName = customer.getCity().getName();
		return result;
	}
	
	@Transactional
	@Override
	public boolean loginExists(String login){
		return customerRepo.findByLogin(login) != null;
	}
	
	@Transactional
	@Override
	public List<Customer> getCustomersForCity(long cityId){
		City city = cityRepo.findOne(cityId);
		return city.getCustomers().stream()
			.sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
			.collect(Collectors.toList());
	}
		
	
	@Transactional
	@Override
	public Map<String, Long> getCitiesMap(){
		return StreamSupport.stream(cityRepo.findAll().spliterator(), false)
				.collect(Collectors.toMap(City::getName, City::getId));
	}
	
	@Transactional
	@Override
	public City getDefaultCity(){
		return cityRepo.findAll().iterator().next();
	}
	
}
