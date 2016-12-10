package com.jedrek.urticaRecruitmentTask.test.repos;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.model.Admin;
import com.jedrek.urticaRecruitmentTask.repos.AdminRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestAdminRepository {
	
	@Autowired
	AdminRepository adminRepo;
	
	@Test
	@Transactional
	public void testCreate(){
		Admin a = new Admin();
		a.setEmail("email@firma.pl");
		a = adminRepo.save(a);
		
		Admin b = adminRepo.findOne(a.getId());
		assertNotNull(b);
		assertTrue(b.getEmail().equals(a.getEmail()));
	}
}
