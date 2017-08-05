package com.estudo.brewer.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.estudo.brewer.controller.CervejasController;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/***
 * 
 * Classe onde será mapeado os controladores
 * 
 * ComponentScan: Melhor usar esta configuração pois caso o pacote ou nome do controlador sofra alteração o spring cuidará de 
 * atualizar o webconfig.
 * 
 * WebMvcConfigurerAdapter: Extendedos por questão de facilidade na sobescrita de métodos úteis do spring. 
 * 
 * ApplicationContextAware: Implementamos pra acessar o context da aplicação
 * 
 * @author Rodrigo
 *
 */

@Configuration /* Se trata de uma classe de configuração */
@ComponentScan(basePackageClasses = { CervejasController.class }) /* Informando a localização de um controlador */
@EnableWebMvc /* Informando que se trata de um classe de projeto web */
/* @ComponentScan("com.estudo.brewer.controller") --> Uma segunda maneira de informar o controlador */
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		
	}
	
	/**
	 * Recepciona TemplateEngine do templateEngine()
	 * Renderiza a resposta bem como o título de codificação
	 * @return
	 */
	
	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	/**
	 * Recepciona ITemplateResolver do templateResolver()
	 * Compila a resposta
	 * @return
	 */
	
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		
		engine.addDialect(new LayoutDialect());
		
		return engine;
	}
	
	/**
	 * Recepciona a resposta do controlador 
	 * Informamos onde ele deve encontrar o arquivo de resposta bem como extensão
	 * @return
	 */
	
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	/**
	 * Responsável por pegar e entregar os recursos estáticos da aplicação
	 * Diga-se estático css, imagens e arquivos js 
	 */
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	
}
