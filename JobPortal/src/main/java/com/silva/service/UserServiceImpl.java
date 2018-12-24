package com.silva.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.silva.service.UserService;
import com.silva.model.JobPosting;
import com.silva.model.Role;
import com.silva.model.User;
import com.silva.repository.JobPostingRepository;
import com.silva.repository.RoleRepository;
import com.silva.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RoleRepository roleRespository;
	 
	 @Autowired
	 private JobPostingRepository jobPostingRepository;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	 @Override
	 public User findUserByEmail(String email) {
	  return userRepository.findByEmail(email);
	 }
	
	 @Override
	 public void saveUser(User user) {
	  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	  user.setActive(1);	  
	  Role userRole = roleRespository.findByRole("APPLICANT");
	  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
	 }
	 
	 @Override
	 public void saveEmployerUser(User user) {
	  user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	  user.setActive(2);
	  Role userRole = roleRespository.findByRole("EMPLOYER");
	  user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
	  userRepository.save(user);
	 }
	 
	@Override
	public ResponseEntity<JobPosting> getJobPosting(@PathVariable Long id) {
		return jobPostingRepository.findById(id)
				.map(jobposting -> ResponseEntity.ok().body(jobposting))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@Override
	public Iterable<JobPosting> getJobPostings() {
		return jobPostingRepository.findAll();
	}
	
	@Override
	public Iterable<JobPosting> getJobPostingsByEmployerId(@PathVariable int empId) {
		return jobPostingRepository.findJobPostingsByEmployerId(empId);
	}
	
	@Override
	public ResponseEntity<JobPosting> createJobPosting(@RequestBody JobPosting jobposting) {
		logger.info("Received job post: job id: " + jobposting.getId() + ", title: " + jobposting.getTitle() + ", description: " + jobposting.getDescription());
		JobPosting newJobPosting = jobPostingRepository.save(jobposting);
		try {
			return ResponseEntity.created(new URI("/user/employer/jobposting/" + newJobPosting.getId())).body(newJobPosting);
		} catch (URISyntaxException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}	
	
}
