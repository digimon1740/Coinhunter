'use strict';

define(function (require) {

	const angular = require('angular'),
		services = require('services/services'),
		controllers = angular.module('app.controllers', ['app.services']);

	// init
	controllers.controller('InitCtrl', require('controllers/init.controller'));

	// controllers.controller('CmsListCtrl', require('controllers/cms/cms.list.controller'));

	controllers.run(['$rootScope', function ($rootScope) {
		$rootScope.sampleParam = "value";
	}]);

	return controllers;
});