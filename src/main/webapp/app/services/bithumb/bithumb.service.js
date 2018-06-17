'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return {
			ticker: $resource('/ticker/bithumb/:cryptoCurrency', {}, {
				get: {
					isArray: false,
				},
			}),
			chart : $resource('/chart/bithumb', {}, {
				list: {
					isArray: false,
				},
			}),
			myAssets : $resource('/my-assets/bithumb', {}, {
				list: {
					isArray: false,
				},
			})
		}
	};

	factory.$inject = ['$resource'];
	return factory;
});