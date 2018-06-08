'use strict';

define(function () {

	function controller($rootScope, $scope, $routeParams, UserService) {

		$scope.user = {
			name: '',
			email: ''
		};

		(() => {
			// UserService.get({}, (data) => {
			// 	$scope.user = data;
			// });


			//script.onload = () => this.doSomethingWhenScriptIsLoaded();


		})();
	}

	controller.$inject = ['$rootScope', '$scope', '$routeParams', 'UserService'];
	return controller;

});