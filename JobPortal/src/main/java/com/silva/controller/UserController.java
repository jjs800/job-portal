package com.silva.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.silva.model.User;
import com.silva.service.UserServiceImpl;


@Controller
public class UserController {
	@Autowired
	 private UserServiceImpl userService;
	 
	 @RequestMapping(value= {"/user/applicant/login"}, method=RequestMethod.GET)
	 public ModelAndView login() {
	  ModelAndView model = new ModelAndView();	  
	  model.setViewName("user/applicant/login");
	  return model;
	 }
	 
	 @RequestMapping(value= {"/user/employer/login"}, method=RequestMethod.GET)
	 public ModelAndView employerLogin() {
	  ModelAndView model = new ModelAndView();	  
	  model.setViewName("user/employer/login");
	  return model;
	 }
	 
	 @RequestMapping(value= {"/user/applicant/signup"}, method=RequestMethod.GET)
	 public ModelAndView signup() {
	  ModelAndView model = new ModelAndView();
	  User user = new User();
	  model.addObject("user", user);
	  model.setViewName("user/applicant/signup");
	  
	  return model;
	 }
	 
	 @RequestMapping(value= {"/user/employer/signup"}, method=RequestMethod.GET)
	 public ModelAndView employerSignup() {
	  ModelAndView model = new ModelAndView();
	  User user = new User();
	  model.addObject("user", user);
	  model.setViewName("user/employer/signup");
	  
	  return model;
	 }
	 
	 @RequestMapping(value= {"user/applicant/signup"}, method=RequestMethod.POST)
	 public ModelAndView createUser(@Valid User user, BindingResult bindingResult) {
	  ModelAndView model = new ModelAndView();
	  User userExists = userService.findUserByEmail(user.getEmail());
	  
	  if(userExists != null) {
	   bindingResult.rejectValue("email", "error.user", "This email already exists!");
	  }
	  if(bindingResult.hasErrors()) {
	   model.setViewName("user/applicant/signup");
	  } else {
	   userService.saveUser(user);
	   model.addObject("msg", "User has been registered successfully!");
	   model.addObject("user", new User());
	   model.setViewName("user/applicant/signup");
	  }
	  
	  return model;
	 }
	 
	 @RequestMapping(value= {"user/employer/signup"}, method=RequestMethod.POST)
	 public ModelAndView createEmployerUser(@Valid User user, BindingResult bindingResult) {
	  ModelAndView model = new ModelAndView();
	  User userExists = userService.findUserByEmail(user.getEmail());
	  
	  if(userExists != null) {
	   bindingResult.rejectValue("email", "error.user", "This email already exists!");
	  }
	  if(bindingResult.hasErrors()) {
	   model.setViewName("user/employer/signup");
	  } else {
	   userService.saveEmployerUser(user);
	   model.addObject("msg", "Employer has been registered successfully!");
	   model.addObject("user", new User());
	   model.setViewName("user/employer/signup");
	  }
	  
	  return model;
	 }
	 
	 @RequestMapping(value= {"/user/applicant/home"}, method=RequestMethod.GET)
	 public ModelAndView home() {
	  ModelAndView model = new ModelAndView();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  User user = userService.findUserByEmail(auth.getName());
	  
	  model.addObject("userName", user.getFirstname() + " " + user.getLastname());
	  model.setViewName("user/applicant/home");
	  return model;
	 }
	 
	 @RequestMapping(value= {"/user/employer/home"}, method=RequestMethod.GET)
	 public ModelAndView employerHome() {
	  ModelAndView model = new ModelAndView();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  User user = userService.findUserByEmail(auth.getName());
	  
	  model.addObject("userName", user.getFirstname() + " " + user.getLastname());
	  model.setViewName("user/employer/home");
	  return model;
	 }
	 
	 @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
	 public ModelAndView accessDenied() {
	  ModelAndView model = new ModelAndView();
	  model.setViewName("errors/access_denied");
	  return model;
	 }
	 
	 @RequestMapping(value="/user/applicant/logout", method = RequestMethod.GET)
	 public ModelAndView logoutPage() {
	 	ModelAndView model = new ModelAndView();
	  	model.setViewName("redirect:/user/applicant/login?logout");
	  	return model;	 
	 }
	 
	 @RequestMapping(value="/user/employer/logout", method = RequestMethod.GET)
	 public ModelAndView employerLogoutPage() {
	 	ModelAndView model = new ModelAndView();
	  	model.setViewName("redirect:/user/employer/login?logout");
	  	return model;	 
	 }

}