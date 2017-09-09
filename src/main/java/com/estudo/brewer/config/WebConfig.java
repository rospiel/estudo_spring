package com.estudo.brewer.config;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.estudo.brewer.controller.CervejasController;
import com.estudo.brewer.controller.converter.EstiloConverter;
import com.estudo.brewer.thymeleaf.BrewerDialect;

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
@ComponentScan(basePackageClasses = { CervejasController.class }) /* Informando a localização dos controladores */
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
		engine.addDialect(new BrewerDialect());
		
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
	
	/**
	 * Apontando a localização dos conversores
	 * @return
	 */
	
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new EstiloConverter());
		
		/* Repare que passamos o padrão internacional, através do idioma do cliente a conversão será realizada */
		/* Informando conversor de String pra BigDecimal */
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		/* Informando conversor de String pra BigDecimal */
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		return conversionService;
	}
	
	/**
	 * Informando o idioma da aplicação
	 * @return
	 */
	
	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
}
