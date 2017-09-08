$(function() {
	
	var modal = $('#modalCadastroRapidoEstilo');
	var botaoSalvar = modal.find('.js-modal-cadastro-estilo-salvar-btn');
	
	var form = modal.find('form');
	form.on('submit', function(evento) { evento.preventDefault() } );
	var url = form.attr('action');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-estilo');
	
	var inputNomeEstilo = $('#nomeEstilo');
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click', onBotaoSalvarClick);
	
	/*
	 * Funcao responsavel por colocar foco no campo de nome ao mostral modal
	 * 
	 * */
	
	function onModalShow() {
		inputNomeEstilo.focus();
	}
	
	/*
	 * Funcao responsavel por limpar o campo nome, sumir com a classe de erro 
	 * dos inputs e deixar escondido o container de erro
	 * 
	 * */
	
	function onModalClose() {
		inputNomeEstilo.val('');
		containerMensagemErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}
	
	/*
	 * Enviando novo estilo no formato json pro servidor
	 * 
	 * */
	
	function onBotaoSalvarClick() {
		var nomeEstilo = inputNomeEstilo.val().trim();
		
		$.ajax( {
			url: url,
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ nome: nomeEstilo }),
			error: onErroSalvandoEstilo,
			success: onEstiloSalvo
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
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>' + mensagemErro + '</span>');
		form.find('.form-group').addClass('has-error');
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
		modal.modal('hide');
	}
})