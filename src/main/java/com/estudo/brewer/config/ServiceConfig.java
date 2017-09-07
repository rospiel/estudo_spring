package com.estudo.brewer.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.estudo.brewer.service.CadastroCervejaService;

/**
 * Classe de serviços, informamos a localização das classes com esta função
 * @author Rodrigo
 *
 */

@Configuration
@ComponentScan(basePackageClasses = CadastroCervejaService.class)
public class ServiceConfig {

}
