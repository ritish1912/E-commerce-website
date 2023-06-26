package com.sheryians.major.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.RedirectStrategy;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sheryians.major.entities.Roles;
import com.sheryians.major.entities.User;
import com.sheryians.major.repositories.RoleRepository;
import com.sheryians.major.repositories.UserRepository;

@Component
public class GoogleOAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	public RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	// Here the authentication is the token that the google api responds with
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("-------Oauth-------");
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		if (!userRepository.findByEmail(email).isPresent()) {
			
			User user = new User();
			user.setEmail(email);
			user.setfName(token.getPrincipal().getAttributes().get("given_name").toString());
			user.setlName(token.getPrincipal().getAttributes().get("family_name").toString());
			List<Roles> roles = new ArrayList<>();
			Roles role = roleRepository.findById(2).get();
			roles.add(role);
			user.setRoles(roles);
			userRepository.save(user);
		}
		response.sendRedirect("/");

	}

}
