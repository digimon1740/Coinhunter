'use strict';

define(['angular'], function (angular) {

	let factory = function ($resource) {
		return $resource('/users/user', {}, {
			get: {
				isArray: false,
			},
		});
	};

	factory.$inject = ['$resource'];
	return factory;
});