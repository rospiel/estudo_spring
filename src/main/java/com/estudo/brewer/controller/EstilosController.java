package com.estudo.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.estudo.brewer.model.Estilo;
import com.estudo.brewer.service.CadastroEstiloService;

@Controller
public class EstilosController {
	
	@Autowired
	private CadastroEstiloService cadastroEstiloService;
	
	@RequestMapping("/estilos/novo")
	public ModelAndView novoEstilo(Estilo estilo) {
		ModelAndView mv = new ModelAndView("estilo/CadastroEstilo");
		return mv;
	}
	
	@RequestMapping(value = "/estilos/novo", method = RequestMethod.POST )
	public ModelAndView cadastrarEstilo(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			model.addAttribute(estilo);
			return novoEstilo(estilo);
		}
		
		cadastroEstiloService.salvar(estilo);
		
		attributes.addFlashAttribute("mensagem", "Estilo incluído com sucesso.");
		
		return new ModelAndView("redirect:/estilos/novo");
	}
}
