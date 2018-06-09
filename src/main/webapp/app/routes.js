// 'use strict';

define(['app'], function (app) {

	app.config(['$routeProvider', ($routeProvider) => {
		$routeProvider.when('/trading', {
			templateUrl: '../partial/trading/trading.html',
			controller: 'TradingCtrl'
		}).when('/profiles', {
			templateUrl: '../partial/account/profile.html',
			controller: 'ProfileCtrl'
		}).otherwise({
			redirectTo: '/trading'
		});
	}]);
});
