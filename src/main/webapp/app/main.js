'use strict';

require.config({
	paths: {
		'i18n': '/js/lib/i18n-2.0.4',
		'underscore': '/js/lib/underscore-1.8.3',
		'store': '/js/lib/store',
		'angular': '/js/lib/angular.min', //AngularJS v1.5.7
		'angular.route': '/js/lib/angular-route',
		'angular.resource': '/js/lib/angular-resource',
		'angular.ui.bootstrap': '/js/lib/angular-ui-bootstrap',
		'angular.InfiniteScroll': '/js/lib/ng-infinite-scroll.min',
		'objectUtils': '/js/assets/objectUtils',
		'dynamicFilter': '/js/assets/dynamicFilter',
		'moment': '/js/lib/moment.min',
	},
	shim: {
		'angular': {
			exports: 'angular'
		},
		'angular.route': {
			deps: ['angular'],
		},
		'angular.resource': {
			deps: ['angular', 'angular.route']
		},
		'angular.ui.bootstrap': {
			deps: ['angular']
		},
		'angular.InfiniteScroll': { //https://sroze.github.io/ngInfiniteScroll/demo_basic.html#
			deps: ['angular']
		},
		'dynamicFilter': {
			deps: ['objectUtils']
		},
		'app': {
			deps: ['angular']
		},
	},
});

const importDependencies = ['angular', 'underscore', 'app', 'routes',
	'angular.route', 'angular.resource',
	'angular.ui.bootstrap', 'angular.InfiniteScroll', 'dynamicFilter', 'moment'];

require(importDependencies, (angular, _,) => {

		angular.bootstrap(document, ['app']);    //  app start

});
