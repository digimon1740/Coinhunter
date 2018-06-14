'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return {
			bithumbTicker: $resource('/trading/bithumb/ticker/:cryptoCurrency', {}, {
				get: {
					isArray: false,
				},
			}),
			bithumbChart : $resource('/trading/bithumb/chart', {}, {
				list: {
					isArray: false,
				},
			})
		}
	};

	factory.$inject = ['$resource'];
	return factory;
});