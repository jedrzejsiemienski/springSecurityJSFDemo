package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

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
	private long cityId;
	private boolean loginExists = false;
	
	public String createCustomer(){
		if(customerService.loginExists(login)){
			setLoginExists(true);
			return "register.xhtml";
		}
		customerService.addCustomer(login, password, name, cityId);
		return "login.xhtml";
	}

	public Map<String, Long> getCitiesMap(){
		return StreamSupport.stream(cityRepository.findAll().spliterator(), false)
				.collect(Collectors.toMap(City::getName, City::getId));
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

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}

	public boolean getLoginExists() {
		return loginExists;
	}

	public void setLoginExists(boolean loginExists) {
		this.loginExists = loginExists;
	}
	
}
