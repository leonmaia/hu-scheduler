import Ember from 'ember';
import config from './config/environment';

var Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.route('search');
  this.resource('hotels', {path:'/hotels/:cityId'});
});

export default Router;
