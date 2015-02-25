(function() {
  var app = angular.module('scheduler', ['angularPikaday']);

  app.controller("SchedulerController", ['$scope', '$filter', '$http', function($scope, $filter, $http) {

    $http.get('api/hotels').then(function(result) {
      $scope.hotels = result.data.hotels;
    });

    $scope.$watch('query.dataIda', function() {
      if ($scope.dataVoltaObject != undefined)
        $scope.dataVoltaObject.setMinDate($scope.dataIdaObject.getDate());
    });

    $scope.search = function() {
      var checkinDate = $filter('date')($scope.dataIdaObject.getDate(), "dd/MM/yyyy");
      var checkoutDate = $filter('date')($scope.dataVoltaObject.getDate(), "dd/MM/yyyy");
      getCityId($scope.query.cidade, checkinDate, checkoutDate);
    };

    var getCityId = function(query,checkinDate,checkoutDate) {
      $http.get('api/cities?name='+query).then(function(result) {
        var cityId = result.data.cities[0].cityId
        var getUrl = 'api/hotels?city='+cityId+'&checkin='+checkinDate+'&checkout='+checkoutDate;
        $http.get(getUrl).then(function(result) {
          $scope.hotels = result.data.hotels;
        });
      });
    };
  }]);
})();
