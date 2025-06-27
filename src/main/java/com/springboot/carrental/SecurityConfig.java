package com.springboot.carrental;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf((csrf)->csrf.disable())
		.authorizeHttpRequests(authorize->authorize
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/api/user/registerUser").permitAll()
				.requestMatchers("/api/user/getAllUsers").hasAnyAuthority("MANAGER")
				.requestMatchers("/api/car/add/{lenderId}/{branchId}").hasAnyAuthority("LENDER","MANAGER")
				.requestMatchers("/api/car/update/{id}").hasAnyAuthority("MANAGER", "LENDER")
				.requestMatchers("/api/car/searchByKeyword/{keyword}").permitAll()
				.requestMatchers("/api/car/getAllAvailable").permitAll()
				.requestMatchers("/api/reservation/getForCustomer/{customerId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/customer/add").permitAll()
				.requestMatchers("/api/compayAccount/checkBalance").hasAuthority("MANAGER")
				.requestMatchers("/api/customer/getByLogin").hasAuthority("CUSTOMER")
				.requestMatchers("/api/lender/add").permitAll()
				.requestMatchers("/api/reservation/add/{customerId}/{carId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/reservation/getByLogin").authenticated()
				.requestMatchers("/api/user/token").permitAll()
				.requestMatchers("/api/user/details").permitAll()
				.requestMatchers("/api/rental/getByLogin").hasAuthority("CUSTOMER")
				.requestMatchers("/api/reservation/getCarStats").permitAll()
				.requestMatchers("/api/branch/add").hasAuthority("MANAGER")
				.requestMatchers("/api/payment/getByRentalId").permitAll()
				.requestMatchers("/api/payment/addPayment/{rentalId}").permitAll()
				.requestMatchers("/api/rental/getByRentalId/{rentalId}").permitAll()
				.requestMatchers("/api/car/searchByBranch/{branchName}").permitAll()
				.requestMatchers("/api/manager/add/{branchId}").permitAll()
				.requestMatchers("/api/return/process/{rentalId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/rental/getByCustomerId/{customerId}").hasAuthority("MANAGER")
				.requestMatchers("/api/car/updateByManager/{carId}").hasAuthority("MANAGER")
				.requestMatchers("/api/customer/update/status/{customerId}").hasAuthority("MANAGER")
				.requestMatchers("/api/car/stats/{carId}").permitAll()
				.requestMatchers("/api/manager/getByLogin").authenticated()
				.requestMatchers("/api/car/getall").permitAll()
				.requestMatchers("/api/reservation/getall").hasAuthority("MANAGER")
				.requestMatchers("/api/paycheck/payToLender/{LenderId}").hasAuthority("MANAGER")
				.requestMatchers("/api/paycheck/getAll").hasAuthority("MANAGER")
				.requestMatchers("/api/paycheck/payToLender/{rentalId}").hasAuthority("MANAGER")
				.requestMatchers("/api/paycheck/getByRentalId/{rentalId}").hasAuthority("MANAGER")
				.requestMatchers("/api/return/process/{rentalId}").hasAuthority("CUSTOMER")
				.requestMatchers("/api/car/getCarsByLender").hasAuthority("LENDER")
				.requestMatchers("/api/branch/getall").permitAll()
				.requestMatchers("/api/lender/getByLogin").authenticated()
				.requestMatchers("/api/reserrvations/getByLenderId/{lenderId}").hasAuthority("LENDER")
				
				.anyRequest()
//				.permitAll())//should be removed after development
				.authenticated())
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.httpBasic(Customizer.withDefaults()); //<- this activated http basic technique
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthmanager(AuthenticationConfiguration auth) throws Exception{
		return auth.getAuthenticationManager();
	}
	

}
