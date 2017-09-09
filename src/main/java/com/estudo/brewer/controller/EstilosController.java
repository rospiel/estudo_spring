package com.estudo.brewer.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estudo.brewer.model.Estilo;
import com.estudo.brewer.service.CadastroEstiloService;
import com.estudo.brewer.service.exception.NomeEstiloJaCadastradoException;

/*
 * Informamos o @RequestMapping na classe quando a mais de um método com 
 * o mesmo mapeamento, diferenciando apenas o que vem em seguida
 * 
 * */


@Controller
@RequestMapping("/estilos")
public class EstilosController {
	
	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		ModelAndView mv = new ModelAndView("estilo/CadastroEstilo");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST )
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			model.addAttribute(estilo);
			return novo(estilo);
		}
		
		try {
			cadastroEstiloService.salvar(estilo);
		} catch (NomeEstiloJaCadastradoException erro) {
			result.rejectValue("nome", erro.getMessage(), erro.getMessage());
			return novo(estilo);
		}
		
		attributes.addFlashAttribute("mensagem", "Estilo incluído com sucesso.");
		
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	/*
	 * Cadastro rápido via ajax
	 * @RequestBody se faz necessário pra converter o json recepcionado em objeto Estilo por
	 * 				meio do Jackson.
	 * 
	 * */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid  Estilo estilo, BindingResult result) {
		if(result.hasErrors()) {
			/* Retornando o response o erro(400)  identificado */
			return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
		}
		
		/* ***** Retiramos o tratamento abaixo porque deixamos a carga da ControllerAdviceExceptionHandler a captura
			try {
				estilo = cadastroEstiloService.salvar(estilo);
			} catch (NomeEstiloJaCadastradoException erro) {
				return ResponseEntity.badRequest().body(erro.getMessage());
			}
		*/
		
		estilo = cadastroEstiloService.salvar(estilo);
		
		/* Tudo correu bem? Retorna o estilo(200) afim de que seja acrescentado no combo de estilos */
		return ResponseEntity.ok(estilo);
		
	}
}
