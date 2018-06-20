'use strict';

define(function () {

	function controller($rootScope, $scope, $stomp, UserService, BithumbService) {

		$scope.exchange = 'BITHUMB'.toLowerCase();
		$scope.cryptoCurrency = 'BTC';

		$scope.user = {};
		$scope.myAssets = {};

		UserService.get({}, (data) => {
			$scope.user = data;

			if ($stomp && $stomp.connect) {
				connectMyAssetsApiByRest();
				connectMyAssetsApiByWebsocket();
			} else {
				// 웹소켓 지원안될경우
				connectMyAssetsApiByRest();
			}
		});

		function connectMyAssetsApiByWebsocket() {
			let subscriptionTopic = `/topic/my-assets/${$scope.exchange}`;
			let sendingTopic = `/app/my-assets/${$scope.exchange}`;
			let params = {id: $scope.user.id};

			let subscription = (payload, headers, res) => {
				if (payload && payload.success) {
					$scope.myAssets = payload;
					$scope.$apply($scope.myAssets);
				}
				$stomp.send(sendingTopic, params);
			};

			let then = (frame) => {
				$stomp.subscribe(subscriptionTopic,
					subscription);

				console.log('then!!!!');
				$stomp.send(sendingTopic, params);
			};

			$stomp.connect(`${location.origin}/ws`, {})
				.then(then);
		}

		function connectMyAssetsApiByRest() {
			BithumbService.myAssets.get({}, (myAssets) => {
				if (myAssets && myAssets.success) {
					$scope.myAssets = myAssets;
				}
			});
		}
	}

	controller.$inject = ['$rootScope', '$scope', '$stomp', 'UserService', 'BithumbService'];
	return controller;

});