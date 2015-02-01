/* global require, module */

var EmberApp = require('ember-cli/lib/broccoli/ember-app');

var app = new EmberApp();

app.import('bower_components/bootstrap-sass-official/assets/javascripts/bootstrap.js');

module.exports = app.toTree();
