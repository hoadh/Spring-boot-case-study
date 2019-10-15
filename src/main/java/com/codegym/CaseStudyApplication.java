package com.codegym;

import cg.wbd.grandemonstration.formatter.LocationFormatter;
import com.codegym.service.FootballPlayerService;
import com.codegym.service.LocationService;
import com.codegym.service.impl.FootballPlayerServiceImpl;
import com.codegym.service.impl.LocationServiceImpl;
import org.springframework.beans.BeansException;
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
}
