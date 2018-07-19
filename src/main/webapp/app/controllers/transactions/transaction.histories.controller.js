'use strict';

define(function () {

	function controller($rootScope, $scope, $stomp, UserService, BithumbService) {

		$scope.exchange = 'BITHUMB'.toLowerCase();
		$scope.cryptoCurrency = 'BTC';

		$scope.user = {};
		$scope.transactionHistories = [];

		(() => {
			if ($stomp && $stomp.connect) {
				connectTransactionHistoriesApiByWebsocket();
			} else {
				// 웹소켓 지원안될경우
				connectMyAssetsApiByRest();
			}
		})();

		function connectTransactionHistoriesApiByWebsocket() {
			let subscriptionTopic = `/topic/transaction/histories/${$scope.exchange}`;
			let sendingTopic = `/app/transaction/histories/${$scope.exchange}`;
			let params = {cryptoCurrency: $scope.cryptoCurrency};

			let subscription = (payload, headers, res) => {
				if (payload && payload.success) {
					$scope.transactionHistories = payload;
					$scope.$apply($scope.transactionHistories);
				}
				$stomp.send(sendingTopic, params);
			};

			let then = (frame) => {
				$stomp.subscribe(subscriptionTopic,
					subscription);

				console.log('then!!!!transaction/histories');
				$stomp.send(sendingTopic, params);
			};

			$stomp.connect(`${location.origin}/ws`, {})
				.then(then);
		}

		function connectMyAssetsApiByRest() {
			BithumbService.myAssets.get({}, (myAssets) => {
				if (myAssets && myAssets.success) {
					$scope.transactionHistories = myAssets;
				}
			});
		}
	}

	controller.$inject = ['$rootScope', '$scope', '$stomp', 'UserService', 'BithumbService'];
	return controller;

});