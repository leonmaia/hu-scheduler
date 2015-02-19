(function() {
  var app = angular.module('scheduler', []);

  app.controller("SchedulerController", function() {
    this.hotels = hotels;
  });

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
