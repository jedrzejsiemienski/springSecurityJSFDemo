package com.jedrek.urticaRecruitmentTask.test.repos;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class TestCustomerRepo {
	
	@Autowired
	CityRepository cityRepo;

	@Autowired
	CustomerRepository customerRepo;
	
	@Test(expected=DataIntegrityViolationException.class)
	@Transactional
	public void testCreate_notUniqueLoginShouldThrow(){
		Customer c = new Customer();
		c.setLogin("login");
	
		customerRepo.save(c);
		customerRepo.save(c);
	}
}

