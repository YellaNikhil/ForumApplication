package com.ForumApplication.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.ForumApplication.ServiceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false, securedEnabled = false, jsr250Enabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public BCryptPasswordEncoder passwordEncorder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	// tells use inMenory aur databse configuration authonication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncorder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().cors().disable()
		.authorizeRequests()
		.antMatchers("/generate-token", "/user/").permitAll()
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.antMatchers("/question/addquestion").hasRole("Learner") 
		.antMatchers("/question/getAllQuestionByAnswerStatus/**").hasAnyRole("SME","Reviewer")
		.antMatchers("/question/getQuestionBydomain/**").hasRole("SME")
		.antMatchers("/question/getAllQuestions").hasAnyRole("SME","Reviewer","Learner")
		.antMatchers("/question/getquestionbyid/**").hasAnyRole("SME","Reviewer")
		.antMatchers("/question/getAllQuestionsAnswered").hasAnyRole("Learner","Reviewer")
		.antMatchers("/answer/addanswer/**").hasRole("SME")
		.antMatchers("/answer/editAnswerandStatus/**").hasRole("Reviewer")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		//Reviewer Pruthvi
		//SME RAM
		//Learner Sharvan
		
	}
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		http
//
//				.csrf() // Cross-Site Request Forgery (CSRF) is an attack that forces authenticated
//						// users to submit a request to a Web application against which they are
//						// currently authenticated. CSRF attacks exploit the trust a Web application has
//						// in an authenticated user.
//				.disable().cors().disable().authorizeRequests().antMatchers("/generate-token", "/user/").permitAll() // allow
//																														// users
//																														// to
//																														// access
//																														// these
//																														// urls
//																														// without
//																														// token
//																														// authebtication
////		.antMatchers("/generate-token","/**").permitAll()
//				.antMatchers(HttpMethod.OPTIONS).permitAll()
////		.antMatchers("**").permitAll()
//				.anyRequest().authenticated()
////		.anyRequest().fullyAuthenticated()
//				.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		// token validation
//		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//	}

}
