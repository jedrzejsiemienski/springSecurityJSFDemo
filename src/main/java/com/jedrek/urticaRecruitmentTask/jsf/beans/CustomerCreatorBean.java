package com.jedrek.urticaRecruitmentTask.jsf.beans;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.service.CustomerService;

@Component
@ManagedBean
@RequestScoped
public class CustomerCreatorBean {

	@Autowired
	CustomerService customerService;

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
		return customerService.getCitiesMap();
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
