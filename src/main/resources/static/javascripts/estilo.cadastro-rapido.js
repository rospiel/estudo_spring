var Brewer = Brewer || {};
	
Brewer.EstiloCadastroRapido = (function() {
		
	/* Construtor */
	function EstiloCadastroRapido() {
		this.modal = $('#modalCadastroRapidoEstilo');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-estilo-salvar-btn');
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
		this.inputNomeEstilo = $('#nomeEstilo');
	}
	
	EstiloCadastroRapido.prototype.iniciar = function() {
		this.form.on('submit', function(evento) { evento.preventDefault() } );
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalClose.bind(this));
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
	}
	
	/*
	 * Funcao responsavel por colocar foco no campo de nome ao mostral modal
	 * 
	 * */
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	/*
	 * Funcao responsavel por limpar o campo nome, sumir com a classe de erro 
	 * dos inputs e deixar escondido o container de erro
	 * 
	 * */
	
	function onModalClose() {
		this.inputNomeEstilo.val('');
		this.containerMensagemErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	/*
	 * Enviando novo estilo no formato json pro servidor
	 * 
	 * */
	
	function onBotaoSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();
		
		$.ajax( {
			url: this.url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onErroSalvandoEstilo.bind(this),
			success: onEstiloSalvo.bind(this)
		} );
	}
	
	/*
	 * Na recepcao de erros, recupera o erro, mostra o container de erros 
	 * e concatena dentro do container o erro obtido
	 * Adiciona ao input a classe de erro
	 * 
	 * */
	function onErroSalvandoEstilo(objeto) {
		var mensagemErro = objeto.responseText;
		this.containerMensagemErro.removeClass('hidden');
		this.containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}
	
	/*
	 * Obtendo sucesso na inclusão, obtem o combo de estilos e acrescenta o mais 
	 * novo estilo na relação, esconde o modal de inclusão de estilos rapidos
	 * 
	 * */
	
	function onEstiloSalvo(estilo) {
		var comboEstilo = $('#estilo');
		comboEstilo.append('<option value=' + estilo.codigo + '>' + estilo.nome + '</option>');
		comboEstilo.val(estilo.codigo);
		this.modal.modal('hide');
	}
	
	
	return EstiloCadastroRapido;
		
}());
	
$(function() {
	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();
});
	
