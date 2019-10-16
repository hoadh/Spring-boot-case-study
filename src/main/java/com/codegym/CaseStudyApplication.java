package com.codegym;


import com.codegym.formatter.LocationFormatter;
import com.codegym.service.FootballPlayerService;
import com.codegym.service.FootballPlayerServiceInterface;
import com.codegym.service.LocationService;
import com.codegym.service.LocationServiceInterface;
import com.codegym.service.impl.FootballPlayerServiceImpl;
import com.codegym.service.impl.FootballPlayerServiceImplInterface;
import com.codegym.service.impl.LocationServiceImpl;
import com.codegym.service.impl.LocationServiceImplInterface;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class CaseStudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseStudyApplication.class, args);
	}

	@Bean
	public FootballPlayerService footballPlayerService(){
		return new FootballPlayerServiceImpl();
	}

	@Bean
	public LocationService locationService(){
		return new LocationServiceImpl();
	}

	@Bean
	public FootballPlayerServiceInterface footballPlayerServiceInterface(){
		return new FootballPlayerServiceImplInterface();
	}

	@Bean
	public LocationServiceInterface locationServiceInterface(){
		return new LocationServiceImplInterface();
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.ENGLISH);
		return localeResolver;
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}


	@Configuration
	class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

		private ApplicationContext appContext;

		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			appContext = applicationContext;
		}

		@Override
		public void addFormatters(FormatterRegistry registry) {
			LocationService locationService = appContext.getBean(LocationService.class);
			Formatter locationFormatter = new LocationFormatter(locationService);
			registry.addFormatter(locationFormatter);
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
			interceptor.setParamName("lang");
			registry.addInterceptor(interceptor);
		}

		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {


			// Image resource.
			registry.addResourceHandler("/image/**").addResourceLocations("file:/home/min2208/Documents/pictures/");

		}

	}

//	@EnableWebSecurity
//	public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		@Autowired
//		private UserDetailsService userDetailsService;
//
//		@Bean
//		public PasswordEncoder passwordEncoder() {
//			return new BCryptPasswordEncoder();
//		}
//
//		@Autowired
//		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		}
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http
//					.authorizeRequests()
//					.antMatchers("/register").permitAll()
//					.antMatchers("/").hasRole("MEMBER")
//					.antMatchers("/admin").hasRole("ADMIN")
//					.and()
//					.formLogin()
//					.loginPage("/login")
//					.usernameParameter("email")
//					.passwordParameter("password")
//					.defaultSuccessUrl("/")
//					.failureUrl("/login?error")
//					.and()
//					.exceptionHandling()
//					.accessDeniedPage("/403");
//		}
//
//	}

}
