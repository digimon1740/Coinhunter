'use strict';

define(function (require) {

	let angular = require('angular'),
		config = require('config'),
		services = angular.module('app.services', ['app.config']);

	services.factory('UserService', require('services/user/user.service'));


	// Shared modules
	//services.factory('VerifyService', require('services/shared/verify.service'));

	return services;
});