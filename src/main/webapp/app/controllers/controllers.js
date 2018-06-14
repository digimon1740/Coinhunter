'use strict';

define(function (require) {

	const angular = require('angular'),
		services = require('services/services'),
		controllers = angular.module('app.controllers', ['app.services']);

	controllers.controller('InitCtrl', require('controllers/init.controller'));

	// Trading
	controllers.controller('TradingCtrl', require('controllers/trading/trading.controller'));

	// Account
	controllers.controller('MyAssetsCtrl', require('controllers/account/my.assets.controller'));
	controllers.controller('ProfileCtrl', require('controllers/account/profile.controller'));

	// Widget
	controllers.controller('ChatCtrl', require('controllers/widget/chat.controller'));

	controllers.run(['$rootScope', function ($rootScope) {
		$rootScope.sampleParam = "value";
	}]);

	return controllers;
});