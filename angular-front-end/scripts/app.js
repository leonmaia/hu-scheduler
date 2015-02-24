(function() {
  var app = angular.module('scheduler', ['angularPikaday']);

  app.controller("SchedulerController", ['$scope', '$filter', '$http', function($scope, $filter, $http) {
    this.hotels = hotels;

    $scope.search = function() {
      var checkinDate = $filter('date')($scope.query.dataVolta, "dd/MM/yyyy");
      var checkoutDate = $filter('date')($scope.query.dataVolta, "dd/MM/yyyy");
    };

    var getCityId = function(query) {
      $http.get('http://127.0.0.1,q')

    };
  }]);

  var hotels = [
  {
    name: 'Granada Hotel',
    price: '2.95',
  },
  {
    name: 'Hotel Atlantico Business Centro',
    price: '2.99',
  },
  {
    name: 'Copacabana Rio Hotel',
    price: '2.99',
  },
  {
    name: 'Golden Park Hotel',
    price: '2.99',
  },
  {
    name: 'Miramar Hotel by Windsor',
    price: '2.99',
  },
  {
    name: 'Windsor Barra',
    price: '2.99',
  },
  {
    name: 'Blue Tree Towers',
    price: '2.99',
  }];
})();
