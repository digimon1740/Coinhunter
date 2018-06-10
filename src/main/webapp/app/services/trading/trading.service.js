'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return {
			bithumbTicker: $resource('/trading/bithumb/ticker/:cryptoCurrency', {}, {
				get: {
					isArray: false,
				},
			}),
		}
	};

	factory.$inject = ['$resource'];
	return factory;
});