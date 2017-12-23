'use strict';

angular.
module('digitalPlanet').
config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');

        $routeProvider.
        when('/products', {
            template: '<product-list></product-list>'
        }).
        when('/products/:productId', {
            template: '<product-detail></product-detail>'
        }).
        when('/filters/:filterCategory', {
            template: '<product-filter></product-filter>'
        }).
        when('/info/:infoCategory', {
            template: '<company-info></company-info>'
        }).
        when('/search/:searchParam', {
            template: '<product-search></product-search>'
        }).
        when('/basket', {
            template: '<product-basket></product-basket>'
        }).
        when('/user-page', {
            template: '<user-page></user-page>'
        }).
        otherwise('/products');
    }
]);