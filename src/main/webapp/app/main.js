'use strict';

require.config({

	paths: {
		'i18n': '/js/lib/i18n-2.0.4',
		'underscore': '/js/lib/underscore-1.8.3',
		'jquery': '/js/lib/jquery-2.1.1',
		'jquery.ui': '/js/lib/jquery-ui-1.10.4.min',
		'bootstrap': '/js/lib/bootstrap.min',
		'jquery.metisMenu': '/js/plugins/metisMenu/jquery.metisMenu',
		'jquery.slimscroll': '/js/plugins/slimscroll/jquery.slimscroll.min',
		'jquery.datepick': '/js/lib/jquery.datepick.min',
		'jquery.datepick-kr': '/js/lib/jquery.datepick-ko',
		'jquery.migrate': '/js/lib/jquery-migrate-1.4.1.min',
		'store': '/js/lib/store',
		'jquery.resizableColumns': '/js/lib/jquery.resizableColumns.min',
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
		'jquery.ui': {
			deps: ['jquery']
		},
		'bootstrap': {
			deps: ['jquery', 'jquery.ui']
		},
		'jquery.metisMenu': {
			deps: ['jquery', 'jquery.ui', 'bootstrap']
		},
		'jquery.slimscroll': {
			deps: ['jquery', 'jquery.ui', 'bootstrap']
		},
		'jquery.migrate': {
			deps: ['jquery']
		},
		'jquery.datepick': {
			deps: ['jquery', 'jquery.migrate', 'jquery.ui', 'bootstrap']
		},
		'jquery.datepick-kr': {
			deps: ['jquery', 'jquery.migrate', 'jquery.ui', 'bootstrap', 'jquery.datepick']
		},
		'jquery.resizableColumns': {
			deps: ['jquery', 'jquery.migrate', 'jquery.ui', 'bootstrap', 'store']
		},
		'angular': {
			deps: ['jquery'],
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

const importDependencies = ['jquery', 'angular', 'underscore', 'app', 'routes', 'jquery.ui', 'bootstrap', 'jquery.metisMenu',
	'jquery.slimscroll', 'jquery.datepick-kr', 'jquery.resizableColumns', 'angular.route', 'angular.resource',
	'angular.ui.bootstrap', 'angular.InfiniteScroll', 'dynamicFilter', 'moment'];

require(importDependencies, ($, angular, _, app,) => {

	$(() => {
		/**
		 * @type {angular.Module}
		 */
		angular.bootstrap(document, ['app']);    //  app start
	});

});
