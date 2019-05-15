angular.module('app', [ 'ui-leaflet' ]).controller(
		'trackController',
		function($scope, $location, $http) {
			console.log("dfdgfdyfg");
			console.log(($location.search()).Authorization);
			$scope.center = {};
			$scope.markers={};
			$scope.getData = function() {
				$http.get('http://192.168.10.118:9001/track').success(
						function(response) {
							$scope.location = response.result;
							console.log($scope.location)
							$scope.center = {
								"lat" : $scope.location.latitude,
								"lng" : $scope.location.longitude,
								"zoom" : 4
							}
							 $scope.markers ={
						          "osloMarker": {
						                "lat":  response.result.latitude,
						                "lng": $scope.location.longitude,
						                "message":  $scope.location.currentLocation,
						                "focus": true,
						                "draggable": false,
						            }
							}
							$scope.defaults = {
						            "scrollWheelZoom": false
						        }
							/*
							 * angular.extend($scope, { center: { lat:
							 * $scope.location.latitude, lng:
							 * $scope.location.longitude, zoom: 8 }, defaults: {
							 * tileLayer:
							 * "http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png",
							 * zoomControlPosition: 'topright',
							 * tileLayerOptions: { opacity: 0.9, detectRetina:
							 * true, reuseTiles: true, }, scrollWheelZoom: false }
							 * });
							 */
						});
			}

			$scope.getData();

		});