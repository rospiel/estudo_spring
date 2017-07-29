package com.estudo.brewer.config.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.estudo.brewer.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
	
	/**
	 * 
	 */
	
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}
	
	/***
	 * Recepciona as requisições web fornece um padrão url 
	 */
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
