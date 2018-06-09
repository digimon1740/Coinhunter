'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return $resource('/users/details', {}, {
			get: {
				isArray: false,
			},
		});
	};

	factory.$inject = ['$resource'];
	return factory;
});