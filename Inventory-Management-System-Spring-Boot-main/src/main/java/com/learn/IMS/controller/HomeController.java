package com.learn.IMS.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.learn.IMS.model.Vendor;
import com.learn.IMS.repository.BorrowerRepository;
import com.learn.IMS.service.IVendorService;
import com.learn.IMS.service.ItemService;
import com.learn.IMS.service.userService;

@Controller
public class HomeController {
	
	@Autowired
	private userService userService;
	
	@Autowired
	private ItemService itemServ;
	
	@Autowired
	private BorrowerRepository borrowerRepo;
	
	@Autowired
	private IVendorService vendorServ;
	
	 @GetMapping("/")
	    public String Index(){
	        return "index";
	    }
	 
	 @GetMapping("/login")
	 public String login() {
		 return "authentication/login";
	 }
	 
	 @GetMapping("/dashboard")
	 public String dashboard(Model model, Principal principal) {
	     String username = principal.getName();
	     
	     if (userService.hasRole(username, "ADMIN")) {
	       //  model.addAttribute("totalProducts", productService.count());
	         model.addAttribute("totalOrders", itemServ.count());
	         model.addAttribute("totalUsers", userService.count());
	     } else {
	         model.addAttribute("myOrders",
	                 borrowerRepo.findByUserName(username).orElseThrow(() ->
	                         new RuntimeException("No orders found for user: " + username)
	                 )
	         );
	     }
	     return "index";
	 }

	
	 
	 @GetMapping("/vendorsName")
	 public String showVendor(Model m) {
	     m.addAttribute("vendor", new Vendor());
	     return "authentication/vendor";
	 }

	 @PostMapping("/vendorsName")
	 public String saveVendor(@ModelAttribute("vendor") Vendor vendor) {
		 vendorServ.saveVendor(vendor);
		
		 return "redirect:/dashboard";
	 }
	 
	 
}
