package com.lbw.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by wangyunfei on 2017/6/12.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	// @Autowired
	// private RedisConnectionFactory connectionFactory;

	// 资源服务器主要通过redis共享token认证信息
	// @Autowired
	// private DataSource jdbcTokenDataSource;
	// @Bean
	// public TokenStore tokenStore() {
	// return new RedisTokenStore(connectionFactory);
	// return new JdbcTokenStore(jdbcTokenDataSource);
	// return new JwtTokenStore(accessTokenConverter());
	// }

	// @Autowired
	// private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config);
		CorsFilter corsFilter = new CorsFilter(source);
		http.authorizeRequests().antMatchers("/noauth/**").permitAll().anyRequest().authenticated().and()
				.addFilterBefore(corsFilter,
						/* FilterSecurityInterceptor.class */SecurityContextPersistenceFilter.class)// 一写要放在SecurityContextPersistenceFilter
																									// 前CORS才生效
				// .authorizeRequests().anyRequest().hasRole("USER").and()
				// 认证不通过后的处理
				.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()).and().csrf().disable()
				.httpBasic();

		// http.addFilterBefore(myFilterSecurityInterceptor,
		// FilterSecurityInterceptor.class);
	}

	// @Override
	// public void configure(ResourceServerSecurityConfigurer resources) {
	// resources.tokenStore(tokenStore());
	// }

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// 定义异常转换类生效
		AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
		((OAuth2AuthenticationEntryPoint) authenticationEntryPoint)
				.setExceptionTranslator(new Auth2ResponseExceptionTranslator());
		resources.authenticationEntryPoint(authenticationEntryPoint);
	}

	/**
	 * 权限不通过的处理
	 */
	public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			// TODO Auto-generated method stub
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}
}
