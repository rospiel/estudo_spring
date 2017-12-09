var Brewer = Brewer || {};

Brewer.UploadFoto = ( function () {
	
	/**
	 * Inicializacao das variaveis bem como a compilacao da pagina de apresentacao
	 * da foto apos upload
	 * 
	 */
	
	function UploadFoto () {
		this.inputNomeFoto = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		
		this.htmlFotoCervejaTemplate = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);
		
		this.containerFotoCerveja = $('.js-container-foto-cerveja');
		
		this.uploadDrop = $('#upload-drop');
	}
	
	UploadFoto.prototype.iniciar = function () {
		var configuracao = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action: this.containerFotoCerveja.data('url-fotos'),
				complete: onUploadCompleto.bind(this)
		}
		
		/* Importação por clique */
		UIkit.uploadSelect($('#upload-select'), configuracao);
		/* Importação por clique, segure e arraste */
		UIkit.uploadDrop(this.uploadDrop, configuracao);
		
		if(this.inputNomeFoto.val()) {
			onUploadCompleto.call( this, { nome: this.inputNomeFoto.val(), contentType: this.inputContentType.val() });
		}
		
	}
	
	/**
	 * Preenche os inputs com as informacoes da foto salva
	 * esconde a div de upload e disponibiliza a div de apresentacao da foto
	 * 
	 */
	function onUploadCompleto (resposta) {
		this.inputNomeFoto.val(resposta.nome);
		this.inputContentType.val(resposta.contentType);
		
		this.uploadDrop.addClass('hidden');
		var htmlFotoCerveja = this.template({nomeFoto: resposta.nome});
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}
	
	/**
	 * Esconde a div de apresentacao da foto e volta a mostrar a div
	 * de upload, reseta os inputs
	 * 
	 */
	function onRemoverFoto () {
		$('.js-foto-cerveja').remove();
		this.uploadDrop.removeClass('hidden');
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}

	return UploadFoto;
	
}) ();

$(function () {
	
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
	
})
