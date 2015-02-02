import Ember from 'ember';
import ajax from 'ic-ajax';

export default Ember.Route.extend({
  model: function(params, transition) {
    var query = transition.queryParams;
    var postParams = '?city='+query.city+'&checkin='+query.checkin+'&checkout='+query.checkout;
    return ajax({
      url: 'http://localhost:9000/hotels'+postParams,
      type: 'get'
    }).then(function(response) {
      return response.hotels;
    });
  }
});
