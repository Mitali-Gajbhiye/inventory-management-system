package com.learn.IMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.learn.IMS.model.User;
import com.learn.IMS.repository.userRepository;
import com.learn.IMS.service.userService;

@Controller
public class AuthController {
	
	@Autowired
	private userService userServ;
	
	@Autowired
	private userRepository usrRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	 @GetMapping("/register")
	 public String showRegisterForm(Model m) {
	     m.addAttribute("user", new User());
	     return "authentication/register";
	 }

	 @PostMapping("/register")
	 public String register(@ModelAttribute("user") User user) {
		 user.setPassword(passwordEncoder.encode(user.getPassword()));
		 user.setRole("Role_User");
		 userServ.saveUser(user);
		
		 return "redirect:/login";
	 }
	

}
