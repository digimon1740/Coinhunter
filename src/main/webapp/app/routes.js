'use strict';

define(['app'], function (app) {

	app.config(['$routeProvider', ($routeProvider) => {
		$routeProvider.when('/sellers', {
			templateUrl: '../partial/cms/cms-list.html',
			controller: 'CmsListCtrl'
		}).when('/members', {
			templateUrl: '../partial/cms/cms-member-list.html',
			controller: 'CmsMemberListCtrl'
		}).otherwise({
			redirectTo: '/sellers'
		});
	}]);
});
