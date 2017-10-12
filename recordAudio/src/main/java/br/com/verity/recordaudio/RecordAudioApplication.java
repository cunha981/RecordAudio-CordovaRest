package br.com.verity.recordaudio;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication
public class RecordAudioApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(RecordAudioApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		new MultipartFilter();
		return application.sources(RecordAudioApplication.class);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement(){
	    MultipartConfigElement config = new MultipartConfigElement("");
	    return config;
	}
	
	@Bean
	public FilterRegistrationBean multipartFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setFilter(new MultipartFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}
}
