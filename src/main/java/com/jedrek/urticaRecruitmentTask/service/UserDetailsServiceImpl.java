package com.jedrek.urticaRecruitmentTask.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.jedrek.urticaRecruitmentTask.model.Customer;
import com.jedrek.urticaRecruitmentTask.repos.CustomerRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {  
            Customer customer = customerRepo.findByLogin(login);  
            if (customer == null) {  
            	throw new UsernameNotFoundException("User not found");
            }  
            return new org.springframework.security.core.userdetails.User(customer.getLogin(), customer.getPassword(), getAuthorities(customer));  
        }  
        catch (Exception e){  
            throw new UsernameNotFoundException("User not found");  
        }
	}

    private Set<GrantedAuthority> getAuthorities(Customer customer){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;  
    }  
}
