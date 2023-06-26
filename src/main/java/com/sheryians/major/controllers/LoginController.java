package com.sheryians.major.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sheryians.major.entities.Roles;
import com.sheryians.major.entities.User;
import com.sheryians.major.global.GlobalData;
import com.sheryians.major.repositories.RoleRepository;
import com.sheryians.major.repositories.UserRepository;

@Controller
public class LoginController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	String login()
	{
		GlobalData.cart.clear();
		return "login";
	}
	@GetMapping("/register")
	String register()
	{
		return"register";
	}
	
	@PostMapping("/register")
	String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException
	{
		String password = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(password));
		List<Roles>roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getEmail(), user.getPassword());
		return"redirect:/";
	}
}
