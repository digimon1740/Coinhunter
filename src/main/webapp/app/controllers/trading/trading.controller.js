'use strict';

define(function () {

	function controller($scope, $routeParams, $stomp, UserService, TradingService) {

		$scope.exchange = 'BITHUMB'.toLowerCase();
		$scope.cryptoCurrency = 'BTC';

		$scope.ticker = {};

		(() => {
			connectTickerApiByRest();

			if ($stomp && $stomp.connect) {
				connectTickerApiByWebsocket();
			} else {
				// 웹소켓 지원안될경우
				connectTickerApiByRest();
			}
		})();

		function connectTickerApiByWebsocket() {
			$stomp.connect('http://localhost:8080/ws', {})
				.then(function (frame) {
					let subscriptionTopic = `/topic/trading/ticker/${$scope.exchange}`;
					let sendingTopic = `/app/trading.ticker/${$scope.exchange}`;
					let param = {cryptoCurrency: $scope.cryptoCurrency};

					$stomp.subscribe(subscriptionTopic,
						(payload, headers, res) => {
							if (payload && payload.status === "0000") {
								$scope.ticker = payload;
								$scope.$apply($scope.ticker);
							}
							$stomp.send(sendingTopic, param);
						});
					$stomp.send(sendingTopic, param);
				});
		}

		function connectTickerApiByRest() {
			TradingService.bithumbTicker.get({
				cryptoCurrency: $scope.cryptoCurrency,
			}, (ticker) => {
				if (ticker && ticker.status === "0000") {
					$scope.ticker = ticker;
				}
			});
		}
	}

	controller.$inject = ['$scope', '$routeParams', '$stomp', 'UserService', 'TradingService'];
	return controller;

});