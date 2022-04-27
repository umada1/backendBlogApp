package com.backend.blogapp.tokenAuth;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.blogapp.filter.RequestFilter;

@EnableWebSecurity
public class Configuration extends WebSecurityConfigurerAdapter{
	@Autowired
	private DetailsOfUsers details; 
	
	@Autowired
	private RequestFilter reqFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.userDetailsService(details);
	}
	
	@Override
	protected void configure(HttpSecurity h) throws Exception {
		h.csrf().disable().authorizeRequests()
		.antMatchers("/api/register").permitAll()
		.antMatchers("/api/auth").permitAll()
		.anyRequest().authenticated().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	h.addFilterBefore(reqFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
	 return NoOpPasswordEncoder.getInstance();
	}

	
	@Bean 
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
}
