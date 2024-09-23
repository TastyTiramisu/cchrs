package com.corp.cchrs.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT email, password, enabled FROM PERSON WHERE email = ?")
		.authoritiesByUsernameQuery("SELECT email, role FROM PERSON WHERE email = ?");
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//enable h2 console again
		/*http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
        .and().csrf().ignoringAntMatchers("/h2-console/**")
        .and().headers().frameOptions().sameOrigin();*/
		
		http.authorizeRequests()
        .antMatchers("/asset/add").hasAnyAuthority("ADMIN")
        .antMatchers("/asset/edit/*").hasAnyAuthority("ADMIN")
        .antMatchers("/asset/delete/*").hasAnyAuthority("ADMIN")
        .antMatchers("/deleted/assets").hasAnyAuthority("ADMIN")
        .antMatchers("/people").hasAnyAuthority("ADMIN")
        .antMatchers("/assets/person/*").hasAnyAuthority("ADMIN")
        .antMatchers("/asset/*").hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/my/assets").hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/asset/*/history").hasAnyAuthority("USER", "ADMIN")
        .antMatchers("/assets").hasAnyAuthority("USER", "ADMIN")//.permitAll()
        .anyRequest().authenticated()
        .and().formLogin()
        .defaultSuccessUrl("/assets");
		
		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login")
		.deleteCookies("JSESSIONID")
		.invalidateHttpSession(true);
        
        return http.build();
	}
}
