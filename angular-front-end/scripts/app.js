(function() {
  var app = angular.module('scheduler', ['angularPikaday']);

  app.controller("SchedulerController", ['$scope', '$filter', '$http', function($scope, $filter, $http) {
    $http.get('api/hotels').then(function(result) {
      $scope.hotels = result.data.hotels;
    });

    $scope.search = function() {
      var checkin = new Date($scope.query.dataIda);
      var checkout = new Date($scope.query.dataVolta);

      if (checkin < checkout) {
        var checkinDate = $filter('date')(checkin, "dd/MM/yyyy");
        var checkoutDate = $filter('date')(checkout, "dd/MM/yyyy");
        getCityId($scope.query.cidade, checkinDate, checkoutDate);
      } else {
        alert("Voce nao pode ir embora antes de chegar.");
      }
    };

    var getCityId = function(query,checkinDate,checkoutDate) {
      $http.get('api/cities?name='+query).then(function(result) {
        debugger;
        var cityId = result.data.cities[0].cityId
        var getUrl = 'api/hotels?city='+cityId+'&checkin='+checkinDate+'&checkout='+checkoutDate;
        $http.get(getUrl).then(function(result) {
          $scope.hotels = result.data.hotels;
        });
      });
    };
  }]);
})();
