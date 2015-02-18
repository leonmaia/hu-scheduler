(function() {
  var app = angular.module('scheduler', []);

  app.controller("SchedulerController", function() {
    this.hotels = hotels;
  });

  var hotels = [
    {
      name: 'Teste',
      price: '2.95',
      canPurchase: true,
    },
    {
      name: 'Teste2',
      price: '2.99',
      canPurchase:false,
    }
  ];
})();
