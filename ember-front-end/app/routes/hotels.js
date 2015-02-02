import Ember from 'ember';
import ajax from 'ic-ajax';

export default Ember.Route.extend({
  model: function(params) {
    return ajax({
      url: 'http://localhost:9000/hotels/cities/' + params.cityId,
      type: 'get'
    }).then(function(response) {
      return response.hotels;
    });
  }
});
