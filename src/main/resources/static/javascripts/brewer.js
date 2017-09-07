/*
 * Informamos a separador pra decimal e milhares
 * 
 */

$(function() {
	var decimal = $('.js-decimal');
	decimal.maskMoney({ decimal: ',', thousands: '.' });
	
	var plain = $('.js-plain');
	plain.maskMoney({ precision: 0, thousands: '.' });
	
});