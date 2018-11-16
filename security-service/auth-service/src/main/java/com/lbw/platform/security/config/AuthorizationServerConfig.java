package com.lbw.platform.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.lbw.platform.security.security.DomainUserDetailsService;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisConnectionFactory connectionFactory;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	DataSource jdbcTokenDataSource;

	/*
	 * @Bean public JwtAccessTokenConverter accessTokenConverter() {
	 * JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	 * converter.setSigningKey("123"); return converter; }
	 */

	@Bean
	public TokenStore tokenStore() {
		//return new RedisTokenStore(connectionFactory);
		 return new JdbcTokenStore(jdbcTokenDataSource);
		// return new JwtTokenStore(accessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailService)// 若无，refresh_token会有UserDetailsService
																									// is
																									// required错误
				.tokenStore(tokenStore());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("hxw").scopes("all").secret("hxwSecret")
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.accessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
				.refreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(25));
	}
	
	@Override 
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception { 
		security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()"); //允许接口/oauth/check_token 被调用 }
	}
	/*
	 * @Bean
	 * 
	 * @Primary public DefaultTokenServices tokenServices() {
	 * DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	 * defaultTokenServices.setTokenStore(tokenStore());
	 * defaultTokenServices.setSupportRefreshToken(true); return
	 * defaultTokenServices; }
	 */
}
