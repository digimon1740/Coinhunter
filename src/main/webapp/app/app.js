'use strict';

define(['angular', 'i18n!i18n-nls/app/nls/app-i18n', 'controllers/controllers', 'filters/filters', 'services/services', 'directives/directives',
	'angular.resource', 'angular.InfiniteScroll'], function (angular, i18n) {

	const dependencies = ['ngRoute', 'ngResource', 'ui.bootstrap', 'infinite-scroll', 'app.controllers', 'app.filters', 'app.services', 'app.directives'];

	const app = angular.module('app', dependencies);

	app.run(($rootScope, $location) => {
		$rootScope.breadCrumb = {};
		$rootScope.settings = {};
		$rootScope.demandSettings = {};

		$rootScope.currentHashUrl = currentHashUrl;
		$rootScope.activeMenu = activeMenu;
		$rootScope.refresh = refresh;

		function currentHashUrl() {
			let url = location.hash.replace('#/', '');
			if (url.indexOf('?') != -1) {
				url = url.substring(0, url.indexOf('?'));
			}
			return url;
		}

		const activationUrlMap = {
			'cms': ['sellers', 'members'],
			'withdraw': ['withdraw-results', 'withdraw-requests', 'withdraw-pendings', 'withdraw-recalls'],
			'settlement': ['calculates', 'settlement-histories'],
			'settings': ['demand-settings', 'holiday-settings']
		};

		function activeMenu(type) {
			if (!type) {
				return false;
			}
			let urls = activationUrlMap[type];
			let currentHashUrl = $rootScope.currentHashUrl();
			for (let i = 0; i < urls.length; i++) {
				let url = urls[i];
				if (currentHashUrl == url) {
					return true;
				}
			}
			return false;
		}

		function refresh($event) {
			$event && $event.preventDefault();
			let url = $location.$$url;
			if (url) {
				location.hash = url.substring(1);
			}
		}

	});

	return app;
});