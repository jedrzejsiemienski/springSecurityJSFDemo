package com.jedrek.urticaRecruitmentTask.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;
import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@RequestScoped
public class CustomerCreatorBean {

	private final static Logger logger = Logger.getLogger(CustomerCreatorBean.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CityRepository cityRepository;
	
	private String login;
	private String password;
	private String name;
	
	public String createCustomer(){
		logger.debug(
			String.format("Starting customer creation with creds: login=%s password=%s name=%s", login, password, name));
		
		Iterable<City> cities = cityRepository.findAll();
		customerService.addCustomer(login, password, name, cities.iterator().next().getId());
		logger.debug("Customer created .............");
		
		return "login.xhtml";
	}

	public String printMsgFromSpring() {
		Iterable<City> cities = cityRepository.findAll();
		StringBuilder result = new StringBuilder();
		for(City city : cities){
			result.append(city.getName());
			result.append(" ");
		}
		return result.toString();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
