package com.estudo.brewer.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.estudo.brewer.config.JPAConfig;
import com.estudo.brewer.config.ServiceConfig;
import com.estudo.brewer.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/**
	 * Informando arquivo de configuração pra acessar as bases de dados
	 */
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class };
	}
	
	/**
	 * Informando arquivo de configuração pra recepção dos controladores
	 */
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}
	
	/**
	 * Recepciona as requisições web e fornece um padrão url 
	 */
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	/**
	 * Forçando a codificação especificada
	 */
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		return new Filter[] { characterEncodingFilter };
	}
	
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
