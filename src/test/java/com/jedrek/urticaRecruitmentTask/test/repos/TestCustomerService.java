package com.jedrek.urticaRecruitmentTask.test.repos;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestCustomerService {
	
	@Autowired
	CityRepository cityRepo;

	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	CustomerService customerService;
	
	@Test
	@Transactional
	public void testCreate(){
		City wro = new City();
		wro.setName("Wrocław");
		long cityId = cityRepo.save(wro).getId();
		
		Customer c1 = customerService.addCustomer("l1", "p1", "c1", cityId);
		Customer c2 = customerService.addCustomer("l2", "p2", "c2", cityId);
		
		c1 = customerRepo.findOne(c1.getId());
		c2 = customerRepo.findOne(c1.getId());
		
		assertTrue(c1.getCity().getName().equals(wro.getName()));
		assertTrue(c2.getCity().getName().equals(wro.getName()));
		
		assertEquals(2, cityRepo.findOne(cityId).getCustomers().size());
	}
	
	@Test(expected=NullPointerException.class)
	@Transactional
	public void testCreate_nullCity(){
		customerService.addCustomer("l1", "p1", "c1", 0);
	}
}
