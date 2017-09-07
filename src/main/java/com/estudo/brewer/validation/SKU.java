package com.estudo.brewer.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "([a-zA-Z]{2}\\d{4})?")
public @interface SKU {
	
	/* Sobescrevendo a mensagem da validação */
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "SKU deve seguir o padrão XX9999";
	
	/* Necessário pra agrupar erros */
	Class<?>[] groups() default {};
	/* Necessário pra informar quando necessário o nível de erro quando a validação falhar */
	Class<? extends Payload>[] payload() default {};
	
}
