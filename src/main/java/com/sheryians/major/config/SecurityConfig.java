package com.sheryians.major.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sheryians.major.services.CustomUserDetailService;
//This configuration file is the first to get executed on application start up
// The below functions are executed just on application startup and are never executed again, instead after that , whenever the user logs in 
// these functions are treated as just rules which must be followed, so are just verified but not executed.
@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public GoogleOAuthSuccessHandler googleOAuthSuccessHandler;
	
	@Autowired
	public CustomUserDetailService customUserDetailService;

	//Authorization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    System.out.println("1");
	    http.authorizeRequests()
	        .antMatchers("/", "/shop/**", "/register").permitAll() // Allow access to these URLs without authentication
	        .antMatchers("/admin/**").hasRole("ADMIN") // Require ADMIN role for URLs starting with "/admin/"
	        .anyRequest().authenticated() // Require authentication for any other URL
	        .and()
	        .formLogin()
	            .loginPage("/login") // Specify the custom login page URL
	            .permitAll() // Allow access to the login page without authentication
	            .failureUrl("/login?error=true") // Redirect to this URL if login fails
	            .defaultSuccessUrl("/") // Redirect to this URL after successful login
	            .usernameParameter("email") // Set the parameter name for the username field in the login form
	            .passwordParameter("password") // Set the parameter name for the password field in the login form
	        .and()
	        .oauth2Login()
	            .loginPage("/login") // Specify the custom login page URL for OAuth2 login
	            .successHandler(googleOAuthSuccessHandler) // Handle successful OAuth2 login
	        .and()
	        .logout()
	            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Specify the URL to trigger logout
	            .logoutSuccessUrl("/login") // Redirect to this URL after successful logout
	            .invalidateHttpSession(true) // Invalidate the HttpSession during logout
	            .deleteCookies("JSESSIONID") // Delete the "JSESSIONID" cookie during logout
	        .and()
	        .exceptionHandling() // Handle exceptions related to security
	        .and()
	        .csrf().disable(); // Disable Cross-Site Request Forgery (CSRF) protection
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		System.out.println("2");

		return new BCryptPasswordEncoder();
	}
	//Autthentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("3");

		auth.userDetailsService(customUserDetailService); 
	}
	//Below method says that these URL's do not need authentication and authorization as they are static , so ignore them
	@Override
	public void configure(WebSecurity web) throws Exception {
		System.out.println("4");

		web.ignoring().antMatchers("/resources/**","/static/**","/productImages/**","/css/**","/js/**") ;
	}
	
       
}
