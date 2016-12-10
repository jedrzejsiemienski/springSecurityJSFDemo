package com.jedrek.urticaRecruitmentTask.jsf.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jedrek.urticaRecruitmentTask.model.City;
import com.jedrek.urticaRecruitmentTask.repos.CityRepository;

@Component
@ManagedBean
@SessionScoped
public class CustomerBean {

	@Autowired
	CityRepository cityRepository;
	
	public String printMsgFromSpring() {
		Iterable<City> cities = cityRepository.findAll();
		StringBuilder result = new StringBuilder();
		for(City city : cities){
			result.append(city.getName());
			result.append(" ");
		}
		return result.toString();
	}
	
}
