'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return $resource('/coins', {}, {
			list: {
				isArray: true,
			},
		});
	};

	factory.$inject = ['$resource'];
	return factory;
});