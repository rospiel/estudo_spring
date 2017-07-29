package com.estudo.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.estudo.brewer.controller.CervejasController;

/***
 * 
 * Classe onde será mapeado os controladores
 * 
 * ComponentScan: Melhor usar esta configuração pois caso o pacote ou nome do controlador sofra alteração o spring cuidará de 
 * atualizar o webconfig.
 * 
 * WebMvcConfigurerAdapter: Extendedos por questão de facilidade na sobescrita de métodos úteis do spring. 
 * 
 * @author Rodrigo
 *
 */

@Configuration /* Se trata de uma classe de configuração */
@ComponentScan(basePackageClasses = { CervejasController.class }) /* Informando a localização de um controlador */
@EnableWebMvc /* Informando que se trata de um classe de projeto web */
/* @ComponentScan("com.estudo.brewer.controller") --> Uma segunda maneira de informar o controlador */
public class WebConfig extends WebMvcConfigurerAdapter {
	
}
