package com.estudo.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.estudo.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

	@Override
	public Estilo convert(String codigo) {
		if(StringUtils.isEmpty(codigo) != true) {
			Estilo estilo = new Estilo();
			estilo.setCodigo(Long.valueOf(codigo));
			
			return estilo;
		}
		
		return null;
	}
}
