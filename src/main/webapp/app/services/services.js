'use strict';

define(function (require) {

	let angular = require('angular'),
		config = require('config'),
		services = angular.module('app.services', ['app.config']);

	// API services
	services.factory('UserService', require('services/api/user.service'));


	// Shared modules
	//services.factory('VerifyService', require('services/shared/verify.service'));

	return services;
});