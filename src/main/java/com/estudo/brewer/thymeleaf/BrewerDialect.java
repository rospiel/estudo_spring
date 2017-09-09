package com.estudo.brewer.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import com.estudo.brewer.thymeleaf.processor.ClassForErrorAttributeTagProcessor;

public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("Algaworks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		
		return processadores;
	}
	
	

}
