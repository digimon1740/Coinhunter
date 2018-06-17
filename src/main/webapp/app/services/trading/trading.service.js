'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return {
			bithumbTicker: $resource('/ticker/bithumb/:cryptoCurrency', {}, {
				get: {
					isArray: false,
				},
			}),
			bithumbChart : $resource('/chart/bithumb', {}, {
				list: {
					isArray: false,
				},
			})
		}
	};

	factory.$inject = ['$resource'];
	return factory;
});