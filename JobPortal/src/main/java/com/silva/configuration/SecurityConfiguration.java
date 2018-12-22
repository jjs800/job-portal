package com.silva.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class SecurityConfiguration {
	
	 @Configuration
	 @Order(3)
	 @EnableWebSecurity
	 public static class EmployerSecurityConfig extends WebSecurityConfigurerAdapter {
		 @Autowired
		 private BCryptPasswordEncoder bCryptPasswordEncoder;
		 
		 @Autowired
		 private DataSource dataSource;
		 
		 private final String USERS_QUERY = "select email, password, active from user where email=?";
		 private final String ROLES_QUERY = "select u.email, r.role from user u inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where u.email=?";

		 
		 
		 @Override
		 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.jdbcAuthentication()
		   .usersByUsernameQuery(USERS_QUERY)
		   .authoritiesByUsernameQuery(ROLES_QUERY)
		   .dataSource(dataSource)
		   .passwordEncoder(bCryptPasswordEncoder);
		 }
		 
		 @Override
		 protected void configure(HttpSecurity http) throws Exception{
			 http
			   .antMatcher("/user/employer/**")
			   .authorizeRequests()
			   .antMatchers("/user/employer/login").permitAll()
			   .antMatchers("/user/employer/signup").permitAll()
			   .antMatchers("/user/employer/home/**").hasAuthority("EMPLOYER").anyRequest()
			   .authenticated().and().csrf().disable()
			   .formLogin().loginPage("/user/employer/login.html").loginProcessingUrl("/user/employer/login").failureUrl("/user/employer/login?error=true")
			   .defaultSuccessUrl("/user/employer/home")
			   .usernameParameter("email")
			   .passwordParameter("password")
			   .and().logout()
			   .logoutUrl("/user/employer/logout")
			   .invalidateHttpSession(true)
			   .logoutSuccessUrl("/user/employer/login")
			   .and().rememberMe()
			   .tokenRepository(employerPersistentTokenRepository())
			   .tokenValiditySeconds(60*60)
			   .and().exceptionHandling().accessDeniedPage("/access_denied");
		 }
		 
		 
		 @Bean
		 public PersistentTokenRepository employerPersistentTokenRepository() {
		  JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		  db.setDataSource(dataSource);
		  
		  return db;
		 }
		 
		 
	 }
	 
	 
	 
	 @Configuration
	 @EnableWebSecurity
	 public static class ApplicantSecurityConfig extends WebSecurityConfigurerAdapter {
		 @Autowired
		 private BCryptPasswordEncoder bCryptPasswordEncoder;
		 
		 @Autowired
		 private DataSource dataSource;
		 
		 private final String USERS_QUERY = "select email, password, active from user where email=?";
		 private final String ROLES_QUERY = "select u.email, r.role from user u inner join user_role ur on (u.id = ur.user_id) inner join role r on (ur.role_id=r.role_id) where u.email=?";
		 		 
		 @Override
		 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		  auth.jdbcAuthentication()
		   .usersByUsernameQuery(USERS_QUERY)
		   .authoritiesByUsernameQuery(ROLES_QUERY)
		   .dataSource(dataSource)
		   .passwordEncoder(bCryptPasswordEncoder);
		 }
		 
		 @Override
		 protected void configure(HttpSecurity http) throws Exception{
		  http
		   .antMatcher("/user/applicant/**")
		   .authorizeRequests()
		   .antMatchers("/user/applicant/login").permitAll()
		   .antMatchers("/user/applicant/signup").permitAll()
		   .antMatchers("/user/applicant/home/**").hasAuthority("APPLICANT").anyRequest()
		   .authenticated().and().csrf().disable()
		   .formLogin().loginPage("/user/applicant/login.html").loginProcessingUrl("/user/applicant/login").failureUrl("/user/applicant/login?error=true")
		   .defaultSuccessUrl("/user/applicant/home")
		   .usernameParameter("email")
		   .passwordParameter("password")
		   .and().logout()
		   .logoutUrl("/user/applicant/logout")
		   .invalidateHttpSession(true)
		   .logoutSuccessUrl("/user/applicant/login")
		   .and().rememberMe()
		   .tokenRepository(applicantPersistentTokenRepository())
		   .tokenValiditySeconds(60*60)
		   .and().exceptionHandling().accessDeniedPage("/access_denied");
		 }
		 
		 @Bean
		 public PersistentTokenRepository applicantPersistentTokenRepository() {
		  JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		  db.setDataSource(dataSource);
		  
		  return db;
		 }		 
		 
	 }
	 
	 
	 
}