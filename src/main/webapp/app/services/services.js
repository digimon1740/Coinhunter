'use strict';

define(function (require) {

	let angular = require('angular'),
		config = require('config'),
		services = angular.module('app.services', ['app.config']);

	// user
	services.factory('UserService', require('services/user/user.service'));

	//trading
	services.factory('BithumbService', require('services/bithumb/bithumb.service'));

	// info
	services.factory('CoinInfoService', require('services/info/coin.info.service'));
	// Shared modules
	//services.factory('VerifyService', require('services/shared/verify.service'));

	return services;
});