'use strict';

define(function () {

	function controller($scope, $stomp, UserService, BithumbService) {
		$scope.myAssets = {};

		(() => {
			connectMyAssetsApiByRest();

			if ($stomp && $stomp.connect) {
				connectMyAssetsApiByWebsocket();
			} else {
				// 웹소켓 지원안될경우
				connectMyAssetsApiByRest();
			}
		})();

		function connectMyAssetsApiByWebsocket() {
			let subscriptionTopic = '/topic/my-assets';
			let sendingTopic = '/app/my-assets';

			let subscription = (payload, headers, res) => {
				if (payload && payload.success) {
					$scope.myAssets = payload;
					$scope.$apply($scope.myAssets);
				}
				$stomp.send(sendingTopic, {});
			};

			let then = (frame) => {
				$stomp.subscribe(subscriptionTopic,
					subscription);

				$stomp.send(sendingTopic, {});
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

	controller.$inject = ['$scope', '$stomp', 'UserService', 'BithumbService'];
	return controller;

});