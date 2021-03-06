package com.cg.iter.authenticationservice.conifg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cg.iter.authenticationservice.filter.JwtFilter;
import com.cg.iter.authenticationservice.service.UserDetailsServiceImpl;
import com.cg.iter.authenticationservice.util.AuthEntryPointJwt;


/****************************************************************************************************************************************
 * Class Name : SecutiryConfig <br>
 * Description : 1. Configure authentication manager builder to setup authentication.
 * 				 2. Configure authentication manager to authenticate logged in user.
 * 				 3. Configure PasswordEncoder to encode and decode password.
 * 				 4. Configure Http security to authorize url request. <br>
 * 

 ****************************************************************************************************************************************/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecutiryConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private JwtFilter jwtFilter;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
    }
	

	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 
		 
		 http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/app/auth/**").permitAll()
			.antMatchers("/v2/**").permitAll()
			.anyRequest().authenticated();
		 
	        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	        
	    }
	 
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/v2/api-docs",
	                                   "/configuration/ui",
	                                   "/swagger-resources/**",
	                                   "/configuration/security",
	                                   "/swagger-ui.html",
	                                   "/webjars/**");
	    }
	
}
