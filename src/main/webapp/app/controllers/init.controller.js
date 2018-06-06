'use strict';

define(function () {

	function controller($rootScope, $scope, $routeParams, UserService) {

		$scope.user = {
			email: '',
			name: ''
		};

		(() => {
			UserService.get({}, (data) => {
				$scope.user = data;
			});

		})();
	}

	controller.$inject = ['$rootScope', '$scope', '$routeParams', 'UserService'];
	return controller;

});