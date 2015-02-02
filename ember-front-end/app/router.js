import Ember from 'ember';
import config from './config/environment';

var Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.resource('hotels', {path:'/hotels', queryParams: ['city','checkin','checkout']});
});

export default Router;
