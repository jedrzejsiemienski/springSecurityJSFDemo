package com.jedrek.urticaRecruitmentTask.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jedrek.urticaRecruitmentTask.dao.AdminDAO;
import com.jedrek.urticaRecruitmentTask.model.Admin;
import com.jedrek.urticaRecruitmentTask.repos.AdminRepository;


@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAdminDAO {
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
	AdminRepository adminRepo;
	
	@Test
	@Transactional
	public void testSave(){
		Admin a = new Admin();
		a.setEmail("admin@strona.pl");
		adminDAO.save(a);
	}
	
	@Test
	@Transactional
	public void testSaveRepo(){
		assertNotNull(adminRepo);
		
		Admin a = new Admin();
		a.setEmail("admin@strona2s.pl");
		adminRepo.save(a);
		
	}

}

