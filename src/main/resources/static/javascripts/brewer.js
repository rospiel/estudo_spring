var Brewer = Brewer || {};

Brewer.MaskMoney = (function() {
	
	/*
	 * Construtor 
	 * 
	 * */
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	/*
	 * Criando a funcao que ficara encarregada de colocar 
	 * separador pra decimal e milhares
	 * 
	 * */
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	
	return MaskMoney;
	
}());

$(function() {
	var maskMoney = new Brewer.MaskMoney();
	maskMoney.enable();
});