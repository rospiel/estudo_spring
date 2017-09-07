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

import com.estudo.brewer.model.Cerveja;
import com.estudo.brewer.model.Origem;
import com.estudo.brewer.model.Sabor;
import com.estudo.brewer.repository.Estilos;
import com.estudo.brewer.service.CadastroCervejaService;

@Controller
public class CervejasController {
	
	@Autowired
	private Estilos estilos;
	
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	/**
	 * Ao devolver a resposta disponibiliza um objeto cerveja
	 * @param cerveja
	 * @return
	 */
	@RequestMapping("/cervejas/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", estilos.findAll());
		mv.addObject("origens", Origem.values());
		return mv;
	}
	/**
	 * Model e RedirectAttributes fundamentais pra devolver algo juntamente com a resposta
	 * Valid diz que desejamos validar o objeto conforme especificado no model do objeto 
	 * @param cerveja
	 * @param result
	 * @param model
	 * @param attributes
	 * @return
	 */
	@RequestMapping(value = "/cervejas/novo", method = RequestMethod.POST )
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			/* Informando atributo no forward, o atributo deve conter na página com o mesmo nome */
			/* model.addAttribute("mensagem", "Erro no formulário"); */
			
			/* Disponibilizando a cerveja no form, para que os atributos 
			   previamente informados permaneçam no form */
			model.addAttribute(cerveja);
			/* Fazendo forward */
			return novo(cerveja);
		}
		
		cadastroCervejaService.salvar(cerveja);
		
		/* Informando atributo no redirect */
		attributes.addFlashAttribute("mensagem", "Cerveja incluída com sucesso.");
		
		/* Fazendo redirect */
		return new ModelAndView("redirect:/cervejas/novo");
	}
	
	@RequestMapping("/usuarios/novo")
	public String novoUsuario(Cerveja cerveja) {
		return "usuario/CadastroUsuario";
	}
	
	@RequestMapping("/cidades/nova")
	public String novaCidade(Cerveja cerveja) {
		return "cidade/CadastroCidade";
	}
	
	
	
	
	
}
