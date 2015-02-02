import Ember from 'ember';
import ajax from 'ic-ajax';

export default Ember.Route.extend({
  model: function(params, transition) {
    return ajax({
      url: 'http://localhost:9000/cities?name='+transition.queryParams.city,
      type: 'get'
    }).then(function(response) {
      debugger;
      var query = transition.queryParams;
      var postparams = '?city='+response.cityId+'&checkin='+query.checkin+'&checkout='+query.checkout;
      return ajax({
        url: 'http://localhost:9000/hotels'+postparams,
        type: 'get'
      }).then(function(response) {
        return response.hotels;
      });
    });
  }
});
