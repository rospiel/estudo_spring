package com.estudo.brewer.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.estudo.brewer.service.exception.NomeEstiloJaCadastradoException;

/*
 * Classe responsável por capturar as exceções, anotamos @ControllerAdvice, isso elimina 
 * a necessidade de colocar o try e catch nos trechos que podem lançar erros
 * 
 * */

@ControllerAdvice
public class ControllerAdviceExceptionHandler {
	
	/*
	 * @ExceptionHandler --> Informamos qual a classe da exceção que desejamos capturar e 
	 * retornamos o que desejaríamos na tratativa try e catch do trecho que pode gerar erro 
	 * 
	 * */
	
	@ExceptionHandler(NomeEstiloJaCadastradoException.class)
	public ResponseEntity<String> handleNomeEstiloJaCadastradoException(NomeEstiloJaCadastradoException erro) {
		return ResponseEntity.badRequest().body(erro.getMessage());
	}
	
}
