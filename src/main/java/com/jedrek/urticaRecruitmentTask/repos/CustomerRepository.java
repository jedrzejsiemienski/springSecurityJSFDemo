package com.jedrek.urticaRecruitmentTask.repos;

import org.springframework.data.repository.CrudRepository;
import com.jedrek.urticaRecruitmentTask.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	Customer findByLogin(String uid);	
}