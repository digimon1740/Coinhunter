'use strict';

define(function () {

	function controller($scope, $routeParams, $stomp, UserService, TradingService, CoinInfoService) {

		$scope.exchange = 'BITHUMB'.toLowerCase();
		$scope.cryptoCurrency = 'BTC';
		$scope.ticker = {};

		$scope.chartRedrawInterval = 10 * 60 * 1000; // default 10 min

		$scope.toFixed = toFixed;
		$scope.changeCryptoCurrency = changeCryptoCurrency;

		(() => {
			fetchCoins();
			showChart($scope.cryptoCurrency);
			connectTickerApiByRest();

			if ($stomp && $stomp.connect) {
				connectTickerApiByWebsocket();
			} else {
				// 웹소켓 지원안될경우
				connectTickerApiByRest();
			}
		})();

		function fetchCoins() {
			CoinInfoService.list({}, (cryptoCurrencies) => {
				$scope.cryptoCurrencies = cryptoCurrencies;
			});
		}

		function connectTickerApiByWebsocket() {
			let subscriptionTopic = `/topic/trading/ticker/${$scope.exchange}`;
			let sendingTopic = `/app/trading.ticker/${$scope.exchange}`;

			let subscription = (payload, headers, res) => {
				if (payload && payload.status === "0000" && $scope.cryptoCurrency === payload.cryptoCurrency) {
					$scope.ticker = payload;
					$scope.$apply($scope.ticker);
				}

				let param = {cryptoCurrency: $scope.cryptoCurrency};
				$stomp.send(sendingTopic, param);
			};

			let then = (frame) => {
				$stomp.subscribe(subscriptionTopic,
					subscription);

				let param = {cryptoCurrency: $scope.cryptoCurrency};
				$stomp.send(sendingTopic, param);
			};

			$stomp.connect(`${location.origin}/ws`, {})
				.then(then);
		}

		function connectTickerApiByRest() {
			TradingService.bithumbTicker.get({
				cryptoCurrency: $scope.cryptoCurrency,
			}, (ticker) => {
				if (ticker && ticker.status === "0000" && $scope.cryptoCurrency === ticker.cryptoCurrency) {
					$scope.ticker = ticker;
				}
			});
		}

		function showChart(cryptoCurrency) {
			TradingService.bithumbChart.list({
				cryptoCurrency: cryptoCurrency,
				period: '10M'
			}, drawChart);
		}

		function drawChart(bithumbChart) {
			if (!bithumbChart.success) {
				showChart(bithumbChart.cryptoCurrency);
				return;
			}
			let data = [];
			try {
				data = JSON.parse(bithumbChart.data);
			} catch (e) {
				showChart(bithumbChart.cryptoCurrency);
				return;
			}

			let minPrice = 1,
				maxPrice = 0,
				date,
				price,
				index;

			let chartData = [];
			for (index = data.length - 1; index >= 0; index = index - 1) {
				date = data[index][0]; // data[i][0] is date
				price = data[index][2]; // data[i][1] is current price

				if (price > maxPrice) {
					maxPrice = price;
				}
				if (price < minPrice) {
					minPrice = price;
				}
				chartData.unshift([date, price]);
			}

			Highcharts.stockChart('chart-container', {
				chart: {
					events: {
						load: function () {
							let series = this.series[0];
							setTimeout(() => {
								fetchChartDataAndSettingToSeries(series, bithumbChart.cryptoCurrency);
							}, $scope.chartRedrawInterval);
						}
					}
				},
				rangeSelector: {
					buttons: [
						{
							type: 'min',
							count: 1,
							text: '10m',
						}, {
							type: 'hour',
							count: 1,
							text: '1h'
						}, {
							type: 'day',
							count: 1,
							text: '1D'
						}, {
							type: 'all',
							count: 1,
							text: 'All'
						},
					],
					selected: 0,
					inputEnabled: true,
					selectedAfter: 1
				},

				title: {
					text: ''
				},

				yAxis: {
					title: {
						text: ''
					},
					plotLines: [{
						value: minPrice,
						color: '#2D99DC',
						dashStyle: 'shortdash',
						width: 1,
						label: {
							text: '',
						}
					}, {
						value: maxPrice,
						color: '#CB3E4B',
						dashStyle: 'shortdash',
						width: 1,
						label: {
							text: ''
						}
					}]
				},

				credits: {
					enabled: false
				},
				exporting: {
					enabled: false
				},

				series: [{
					name: '원',
					type: 'area',
					color: '#2D99DC',
					fillColor: {
						linearGradient: {
							x1: 0,
							y1: 0,
							x2: 0,
							y2: 1
						},
						stops: [
							[0, 'rgba(45, 153, 220, .1)'],
							[1, 'rgba(45, 153, 220, 0)']
						]
					},
					threshold: null,
					data: chartData,
					tooltip: {
						valueDecimals: 4
					}
				}]
			});
		}

		function fetchChartDataAndSettingToSeries(series, cryptoCurrency) {
			$.ajax({
				url: `/trading/bithumb/chart?cryptoCurrency=${cryptoCurrency}&period=10M`,
				dataType: 'json',
			}).then((newBithumbChart) => {
				if (!newBithumbChart.success) {
					fetchChartDataAndSettingToSeries(newBithumbChart.cryptoCurrency);
					return;
				}
				let data = [];
				try {
					data = JSON.parse(newBithumbChart.data);
				} catch (e) {
					fetchChartDataAndSettingToSeries(newBithumbChart.cryptoCurrency);
					return;
				}
				let dataToSet = [];
				data.forEach((chartData) => {
					dataToSet.push([chartData[0], chartData[2]]);
				});
				series.setData(dataToSet, true);
			});
		}

		function showProChart() {
			let chart = new cryptowatch.Embed('bitfinex', 'btcusd', {
				timePeriod: '1d',
				presetColorScheme: 'delek'
			});
			chart.mount('#chart-container');
		}

		function toFixed(number, count) {
			if (!number) {
				return "";
			}
			number = parseFloat(number);
			return number.toFixed(count);
		}

		function changeCryptoCurrency(cryptoCurrency) {
			$scope.cryptoCurrency = cryptoCurrency;
			showChart(cryptoCurrency);
			connectTickerApiByRest();
		}
	}


	controller.$inject = ['$scope', '$routeParams', '$stomp', 'UserService', 'TradingService', 'CoinInfoService'];
	return controller;

});