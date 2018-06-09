'use strict';

define(function () {

	function controller($rootScope, $scope, $routeParams, UserService, $stomp) {

		//$scope.myres = [];

		$scope.cryptoCurrency = 'BTC';

		UserService.get({}, (data) => {
			$scope.user = data;

			// TODO stomp 가 사용가능일경우에 웹소켓 연결
			// TODO 아닌경우라면 settimeout 으로 새로고침 해야할듯
			$stomp.connect('http://localhost:8080/ws', {})
				.then(function (frame) {
					var param = {id: $scope.user.id, cryptoCurrency: $scope.cryptoCurrency};
					let subscription = $stomp.subscribe('/topic/trading/ticker/' + $scope.user.id,
						function (payload, headers, res) {
							console.log(payload);
							$stomp.send('/app/trading.ticker/' + $scope.user.id, param);
						});
					$stomp.send('/app/trading.ticker/' + $scope.user.id, param);
				});

		});

	}

	controller.$inject = ['$rootScope', '$scope', '$routeParams', 'UserService', '$stomp'];
	return controller;

});