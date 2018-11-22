package com.lbw.platform.admin.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserDetailsService userDetailsService;
	
	@Autowired
	private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	 
    public WebSecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
    }
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//authorizeRequests() 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/static/**","/css/**","/data/**","/js/**","/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable();
               // .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());
        
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
	
	/**
	 * 权限不通过的处理
	 */
	public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			// TODO Auto-generated method stub
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	                "Authentication Failed: " + authException.getMessage());
		}
	}
}
